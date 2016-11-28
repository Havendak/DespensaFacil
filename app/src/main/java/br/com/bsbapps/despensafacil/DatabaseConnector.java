package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Andre Becklas on 28/11/2016.
 */

public class DatabaseConnector {
    private static final String DATABASE_NAME = "dfdb";
    private static final int DB_CURRENT_VERSION = 1;
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbOpenHelper;

    public DatabaseConnector(Context context){
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
}
