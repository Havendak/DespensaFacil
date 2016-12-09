package br.com.bsbapps.despensafacil.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.bsbapps.despensafacil.DatabaseOpenHelper;

/**
 * Created by proca on 02/12/2016.
 * Repesenta uma lista co itens da despensa, PantryItem
 */

public class PantryList {
    // Database
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbHelper;

    // Colunas
    private String[] allColumns = {DatabaseOpenHelper.COLUMN_LIST_ID,
            DatabaseOpenHelper.COLUMN_LIST_NAME,
            DatabaseOpenHelper.COLUMN_STATUS};

    // Variáveis privadas
    private int listId;
    private String listName;
    private int status;

    // Construtor
    public PantryList(Context context) {
        dbHelper = new DatabaseOpenHelper(context);
    }

    // Métodos de definição e obtenção das variáveis privadas
    public int getListId() {
        return listId;
    }

    public void setListId(int ListId) {
        this.listId = ListId;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
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
        values.put(DatabaseOpenHelper.COLUMN_LIST_NAME, listName);
        values.put(DatabaseOpenHelper.COLUMN_STATUS, status);

        return database.insert(DatabaseOpenHelper.TABLE_PANTRY_LIST, null, values);
    }

    //Método de deleção
    public void delete() {
        database.delete(DatabaseOpenHelper.TABLE_PANTRY_LIST, DatabaseOpenHelper.COLUMN_LIST_ID
                + " = " + listId, null);
    }


}
