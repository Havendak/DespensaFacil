package br.com.bsbapps.despensafacil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andre Becklas on 28/11/2016.
 */

public class DatabaseConnector {
    private static final String DATABASE_NAME = "dfdb";
    private static final int DB_CURRENT_VERSION = 1;
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbOpenHelper;

    public DatabaseConnector(Context context){
        try{
            context.deleteDatabase(DATABASE_NAME);
        } catch (NullPointerException e){

        }
        //dbOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, DB_CURRENT_VERSION);
    }

    public SQLiteDatabase open() throws SQLException{
        database = dbOpenHelper.getWritableDatabase();
        return database;
    }

    public void close() {
        if(database != null) {
            database.close();
        }
    }

    public void insert(String table, ContentValues values) {
        open();
        database.insert(table, null, values);
        close();
    }

    public Long insertList(String name, int defList) {
        ContentValues newList = new ContentValues();
        newList.put("user_list_name", name);
        newList.put("default_list", defList);
        open();
        Long id = database.insert("df_user_list", null, newList);
        close();
        return id;
    }

    public Cursor getAllLists() {
        return database.query("df_user_list", new String[] {"user_list_id", "user_list_name"}, null, null, null, null, "user_list_name");
    }

    public Cursor getList(int id){
        return database.query("df_user_list", null, "user_list_id=" + id, null, null, null, null);
    }



    public void insertProduct(String barcode, String product) {
        ContentValues newProduct = new ContentValues();
        newProduct.put("barcode", barcode);
        newProduct.put("product_name", product);
        newProduct.put("product_status", 0);
        open();
        database.insert("df_product", null, newProduct);
        close();
    }

    public void updateProduct(String barcode, String product) {
        ContentValues newProduct = new ContentValues();
        newProduct.put("product_name", product);
        open();
        database.update("df_product", newProduct, "barcode='" + barcode + "'", null);
        close();
    }




    public void insertProductOnList(int list, String barcode, int quantity, Date duedate) {
        ContentValues newListProduct = new ContentValues();
        newListProduct.put("user_list_id", list);
        newListProduct.put("barcode", barcode);
        newListProduct.put("quantity", quantity);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(duedate);
        newListProduct.put("due_date", date);
        open();
        database.insert("df_list_product", null, newListProduct);
        close();

    }

    public void deleteProductFromList(int list, String barcode) {
        String sql = "DELETE FROM df_list_product WHERE list=? AND barcode='?'";
        database.rawQuery(sql,new String[]{String.valueOf(list),String.valueOf(barcode)});
    }

    public void incrementQuantity(int list, String barcode, Date duedate, int quantity) {
        String sql = "UPDATE df_list_product SET quantity=quantity + ? WHERE user_list_id=? and barcode = '?' and due_date='?'";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(duedate);
        database.rawQuery(sql,new String[]{String.valueOf(quantity), String.valueOf(list), String.valueOf(barcode), String.valueOf(date)});
    }



    public Cursor getListProduct(int list, String barcode){
        return database.query("df_list_product", null, "user_list_id=" + list + " AND barcode='" + barcode + "'", null, null, null, null);
    }


}
