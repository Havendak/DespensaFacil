package br.com.bsbapps.despensafacil.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.bsbapps.despensafacil.DatabaseOpenHelper;

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

    private int list_id;
    private String barcode;
    private int dueDate;
    private int quantity;
    private int status;

    public PantryItem(Context context) {
        dbHelper = new DatabaseOpenHelper(context);
    }

    public int getListId() {
        return list_id;
    }

    public void setListId(int list_id) {
        this.list_id = list_id;
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
        values.put(DatabaseOpenHelper.COLUMN_LIST_ID, list_id);
        values.put(DatabaseOpenHelper.COLUMN_BARCODE, barcode);
        values.put(DatabaseOpenHelper.COLUMN_DUE_DATE, dueDate);
        values.put(DatabaseOpenHelper.COLUMN_QUANTITY, quantity);
        values.put(DatabaseOpenHelper.COLUMN_STATUS, status);

        return database.insert(DatabaseOpenHelper.TABLE_PANTRY_ITEM, null, values);
    }

    //Método de deleção
    public void delete() {
        database.delete(DatabaseOpenHelper.TABLE_PANTRY_ITEM, DatabaseOpenHelper.COLUMN_LIST_ID
                + " = " + list_id + " AND '" + DatabaseOpenHelper.COLUMN_BARCODE
                + " = " + barcode + "'", null);
    }
}
