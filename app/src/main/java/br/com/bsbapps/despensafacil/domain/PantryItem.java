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
    public Cursor getAlertProducts(int list, int alertDays){

        //recupera dia atual e adiciona a quantidade de dias de alerta
        Date dateHandler = new DateHandler().getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stringDateToday = sdf.format(dateHandler);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(stringDateToday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, alertDays);
        String limitDay = sdf.format(c.getTime());

        //consulta usando a data limite
        String sql = "SELECT A._id, A.user_list_id, A.barcode, B.product_name, A.quantity, A.due_date, " +
                "FROM df_list_product A INNER JOIN df_product B ON B.barcode = A.barcode " +
                "WHERE A.user_list_id = ? ORDER BY B.product_name AND" +
                "Datetime(A.due_date) <= Datetime(" + limitDay + ")";

        return database.rawQuery(sql,new String[]{String.valueOf(list)});
    }
}
