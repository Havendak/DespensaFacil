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
                "    user_list_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "    user_list_name VARCHAR(50), " +
                "    default_list BOOL NOT NULL DEFAULT false);";
        db.execSQL(createQuery);

        //Criação da tabela df_product
        createQuery="CREATE TABLE df_product (" +
                "    barcode varcHAR(30) PRIMARY KEY ASC NOT NULL, " +
                "    product_name varc(100) NOT NULL, " +
                "    product_status tinYINT NOT NULL DEFAULT 0);";
        db.execSQL(createQuery);

        //Criação da tabela df_list_product
        createQuery="CREATE TABLE df_list_product (" +
                "    _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "    user_list_id INTEGER NOT NULL, " +
                "    barcode varch(30) NOT NULL, " +
                "    quantity INT DEFAULT 0, " +
                "    due_date DATE NOT NULL, " +
                "    product_status tinyint DEFAULT 0);";
        db.execSQL(createQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
