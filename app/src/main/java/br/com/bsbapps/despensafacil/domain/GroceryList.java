package br.com.bsbapps.despensafacil.domain;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by proca on 02/12/2016.
 * represneta uma lista de intens de compras GroceryItem
 */

public class GroceryList {
    int listId;
    List<GroceryItem> item = new ArrayList<GroceryItem>();

    public GroceryList(int listId, List<GroceryItem> item) {
        this.listId = listId;
        this.item = item;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public List<GroceryItem> getItem() {
        return item;
    }

    public void setItem(List<GroceryItem> item) {
        this.item = item;
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS  GROCERY_LIST" +
                "(" +
                "LIST_ID              INT NOT NULL," +
                "BARCODE              TEXT NOT NULL," +
                "QUANTITY             INT NULL" +
                ")";
    }

    public void insert(SQLiteDatabase db){

        for (int i=0; i<item.size();i++) {
            ContentValues valores = new ContentValues();
            valores.put("LIST_ID", listId);
            valores.put("BARCODE", item.get(i).getBarcode());
            valores.put("QUANTITY ", item.get(i).getQuantity());

            long resultado = db.insert("GROCERY_LIST", null, valores);
            Log.d("Insert grocery list: ", String.valueOf(resultado));
        }


    }
}
