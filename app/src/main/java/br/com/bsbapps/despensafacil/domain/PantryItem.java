package br.com.bsbapps.despensafacil.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.bsbapps.despensafacil.DatabaseOpenHelper;
import br.com.bsbapps.util.DateHandler;

/**
 * Created by proca on 02/12/2016.
 * Representa um item de uma lista da despensa
 */

public class PantryItem {
    // Database
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbHelper;

    // Colunas
    private String[] allColumns = {DatabaseOpenHelper.COLUMN_LIST_ID,
            DatabaseOpenHelper.COLUMN_BARCODE,
            DatabaseOpenHelper.COLUMN_DUE_DATE,
            DatabaseOpenHelper.COLUMN_QUANTITY,
            DatabaseOpenHelper.COLUMN_STATUS};

    // Variáveis privadas
    private int listId;
    private String barcode;
    private int dueDate;
    private int quantity;
    private int status;

    //Construtor
    public PantryItem(Context context) {
        dbHelper = new DatabaseOpenHelper(context);
    }

    // Métodos de definição e obtenção das variáveis privadas
    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getDueDate() {
        return dueDate;
    }

    public void setDueDate(int dueDate) {
        this.dueDate = dueDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private void clearFields() {
        listId = 0;
        barcode = "";
        dueDate = 0;
        quantity = 0;
        status = 0;
    }

    // Método de abertura do database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Metodo de fechamento do database
    public void close() {
        dbHelper.close();
    }

    //Método de inserção
    public long insert(){
        ContentValues values = new ContentValues();
        values.put(DatabaseOpenHelper.COLUMN_LIST_ID, listId);
        values.put(DatabaseOpenHelper.COLUMN_BARCODE, barcode);
        values.put(DatabaseOpenHelper.COLUMN_DUE_DATE, dueDate);
        values.put(DatabaseOpenHelper.COLUMN_QUANTITY, quantity);
        values.put(DatabaseOpenHelper.COLUMN_STATUS, status);
        open();
        long result = database.insert(DatabaseOpenHelper.TABLE_PANTRY_ITEM, null, values);
        close();
        return result;
    }

    //Método de deleção
    public void delete() {
        open();
        database.delete(DatabaseOpenHelper.TABLE_PANTRY_ITEM, DatabaseOpenHelper.COLUMN_LIST_ID
                + " = " + listId + " AND '" + DatabaseOpenHelper.COLUMN_BARCODE
                + " = " + barcode + "'", null);
        close();
    }

    //Método de retorno dos alertas
    public Cursor getAlertProducts(int alertDays){

        //recupera dia atual e adiciona a quantidade de dias de alerta
        DateHandler dt = new DateHandler();
        long targetDt = dt.getTimestamp(dt.addDate(alertDays));

        //consulta usando a data limite
        String sql = "SELECT A." + DatabaseOpenHelper.COLUMN_ID +
                ", A." + DatabaseOpenHelper.COLUMN_LIST_ID +
                ", A." + DatabaseOpenHelper.COLUMN_BARCODE +
                ", B." + DatabaseOpenHelper.COLUMN_PRODUCT_NAME +
                ", A." +DatabaseOpenHelper.COLUMN_QUANTITY +
                ", A." + DatabaseOpenHelper.COLUMN_DUE_DATE +
                " FROM " + DatabaseOpenHelper.TABLE_PANTRY_ITEM +
                " A INNER JOIN " + DatabaseOpenHelper.TABLE_PRODUCT +
                " B ON B." + DatabaseOpenHelper.COLUMN_BARCODE +
                " = A." + DatabaseOpenHelper.COLUMN_BARCODE +
                " WHERE A." + DatabaseOpenHelper.COLUMN_LIST_ID +
                " = ? AND A." + DatabaseOpenHelper.COLUMN_DUE_DATE +
                " <= ? ORDER BY B." + DatabaseOpenHelper.COLUMN_DUE_DATE;
        return database.rawQuery(sql,new String[]{String.valueOf(listId), String.valueOf(targetDt)});
    }

    public Cursor getExistentPantryItem(int list, String barcode, Date duedate){
        long dt = new DateHandler().getTimestamp(duedate);
        return database.query(DatabaseOpenHelper.TABLE_PANTRY_ITEM, null,
                DatabaseOpenHelper.COLUMN_LIST_ID + "=" + list + " AND " +
                DatabaseOpenHelper.COLUMN_BARCODE + "='" + barcode + "' AND " +
                DatabaseOpenHelper.COLUMN_DUE_DATE + "='" + dt + "'", null, null, null, null);
    }
}
