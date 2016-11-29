package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import domain.Despensa;
import domain.Produto;

/**
 * Created by proca on 28/11/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context) {
       super(context, "DESPENSA_FACIL", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        criaTabelas(db);
        populaTabelas(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void criaTabelas(SQLiteDatabase db) {

        String createTableHistoricoPrecos = "CREATE TABLE IF NOT EXISTS HISTORICO_PRECOS" +
                "(" +
                "BARCODE              TEXT NOT NULL," +
                "QUANTIDADE           INT NULL," +
                "VALOR                INT NULL," +
                "DATA COMPRA          INT NULL" +
                "ID_MERCADO INT NULL)";
        String createTableListaCompras = "CREATE TABLE IF NOT EXISTS  LISTA_COMPRAS" +
                "(" +
                "BARCODE              TEXT NOT NULL," +
                "QUANTIDADE           INT NULL" +
                ")";


        String createTableMedida = "CREATE TABLE IF NOT EXISTS MEDIDA" +
                "(" +
                "ID_MEDIDA primary key autoincrement           INT NOT NULL," +
                "NOME_MEDIDA          TEXT NULL" +
                ")";

        String createTableTipo = "CREATE TABLE IF NOT EXISTS TIPO" +
                "(" +
                "ID_TIPO primary key autoincrement           INT NOT NULL," +
                "NOME_TIPO          TEXT NOT NULL" +
                ")";

        String createTableSubTipo = "CREATE TABLE SUBTIPO" +
                "(" +
                "ID_SUBTIPO  primary key autoincrement  INT NOT NULL," +
                "ID_TIPO   INT NOT NULL," +
                "NOME_SUBTIPO       TEXT NOT NULL" +
                ")";

        db.execSQL(Despensa.createTable());
        //db.execSQL(createTableListaCompras);
        //db.execSQL(createTableHistoricoPrecos);
        db.execSQL(Produto.createTable());
        //db.execSQL(createTableMedida);
        //db.execSQL(createTableTipo);
        //db.execSQL(createTableSubTipo);
    }

    private void populaTabelas(SQLiteDatabase db) {
        Cursor cur = db.rawQuery("SELECT EXISTS (SELECT 1 FROM DESPENSA)", null);

        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt (0) == 0) {
                Despensa despensa = new Despensa("7894321711263","2016-12-20",1);
                despensa.insert(db);
                despensa = new Despensa("7898024394181","2016-12-15",1);
                despensa.insert(db);
                despensa = new Despensa("7894321711263","2017-08-20",2);
                despensa.insert(db);
            }
        }
        cur = db.rawQuery("SELECT EXISTS (SELECT 1 FROM PRODUTO)", null);
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt (0) == 0) {
                Produto produto = new Produto("7894321711263","Toddy",1,400,"Achocolatado Toddy 400g",1,1,0);
                produto.insert(db);
                produto = new Produto("7898024394181","Nutella",1,350,"Creme de Avelã Nutella 350g",1,1,0);
                produto.insert(db);

            }
        }


    }


}
