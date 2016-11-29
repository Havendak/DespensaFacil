package br.com.bsbapps.despensafacil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.name;

/**
 * Created by Andre Becklas on 28/11/2016.
 */

public class DatabaseConnector {
    private static final String DATABASE_NAME = "dfdb";
    private static final int DB_CURRENT_VERSION = 1;
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbOpenHelper;

    public DatabaseConnector(Context context){
        context.deleteDatabase(DATABASE_NAME);
        dbOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME, null, DB_CURRENT_VERSION);
    }

    public void open() throws SQLException{
        database = dbOpenHelper.getWritableDatabase();
    }

    public void close() {
        if(database != null) {
            database.close();
        }
    }

    public Long insertList(String name, Boolean defList) {
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

    public Cursor getDefaultList() {
        return database.query("df_user_list", null, "default_list=1", null, null, null, null);
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

    public Cursor getAllProducts() {
        return database.query("df_product", new String[] {"barcode", "product_name", "product_status"}, null, null, null, null, "product_name");
    }

    public Cursor getProduct(String barcode){
        return database.query("df_product", null, "barcode='" + barcode + "'", null, null, null, null);
    }

    public void insertProductOnList(Long list, String barcode, int quantity, Date duedate) {
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

    public Cursor getAllListProducts(Long list) {
        return database.query("df_list_product", new String[] {"user_list_id", "barcode", "quantity", "due_Date"}, "user_list_id=" + list.toString(), null, null, null, "product_name");
    }

    public Cursor getListProduct(Long list, String barcode){
        return database.query("df_list_product", null, "user_list_id=" + list.toString() + " AND barcode='" + barcode + "'", null, null, null, null);
    }
}
