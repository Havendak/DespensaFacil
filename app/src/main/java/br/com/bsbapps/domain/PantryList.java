package br.com.bsbapps.domain;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by proca on 02/12/2016.
 * Repesenta uma lista co itens da despensa, PantryItem
 */

public class PantryList {
    int listId;
    List<PantryItem> item;

    public PantryList(int listId, List<PantryItem> item) {
        this.listId = listId;
        this.item = item;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int pantryListId) {
        this.listId = pantryListId;
    }

    public List<PantryItem> getItem() {
        return item;
    }

    public void setItem(List<PantryItem> item) {
        this.item = item;
    }

    public static String createTable(){
        return "CREATE TABLE IF NOT EXISTS PANTRY_LIST " +
                "(LIST_ID              INT NOT NULL," +
                "BARCODE TEXT NOT NULL, " +
                "DUE_DATE TEXT NOT NULL, " +
                "QUANTITY INT NULL)";
    }
    public void insert(SQLiteDatabase db){

        for (int i=0; i<item.size();i++) {
            ContentValues valores = new ContentValues();
            valores.put("LIST_ID", listId);
            valores.put("BARCODE", item.get(i).getBarcode());
            valores.put("DUE_DATE", item.get(i).getDueDate());
            valores.put("QUANTITY ", item.get(i).getQuantity());

            long resultado = db.insert("PANTRY_LIST", null, valores);
            Log.d("Insert grocery list: ", String.valueOf(resultado));
        }


    }


}
