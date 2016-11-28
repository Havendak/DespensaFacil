package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andre Becklas on 28/11/2016.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Criação da tabela df_user_list
        String createQuery="CREATE TABLE df_user_list (" +
                "  user_list_id LONG UNSIGNED NOT NULL AUTO_INCREMENT," +
                "  user_list_name VARCHAR(50) NULL," +
                "  default_list BOOL NULL," +
                "  PRIMARY KEY(user_list_id)" +
                ");";
        db.execSQL(createQuery);

        //Criação da tabela df_product
        createQuery="CREATE TABLE df_product (" +
                "  barcode VARCHAR(30) NOT NULL AUTO_INCREMENT," +
                "  product_name VARCHAR(100) NULL," +
                "  product_status TINYINT UNSIGNED NULL," +
                "  PRIMARY KEY(barcode)" +
                ");";
        db.execSQL(createQuery);

        //Criação da tabela df_list_product
        createQuery="CREATE TABLE df_list_product (" +
                "  user_list_id INTEGER UNSIGNED NOT NULL," +
                "  barcode VARCHAR(30) NOT NULL," +
                "  quantity INTEGER UNSIGNED NULL," +
                "  due_date DATE NULL," +
                "  product_status TINYINT UNSIGNED NULL," +
                "  PRIMARY KEY(user_list_id, barcode)," +
                "  INDEX Table_03_FKIndex1(user_list_id)," +
                "  INDEX Table_03_FKIndex2(barcode)" +
                ");";
        db.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
