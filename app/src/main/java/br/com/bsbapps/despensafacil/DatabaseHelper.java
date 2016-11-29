package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by proca on 28/11/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public DatabaseHelper(Context context) {
       super(context, "DESPENSA_FACIL", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        criaTabelas(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void criaTabelas(SQLiteDatabase sqLiteDatabase) {
        String createTableDespensa = "CREATE TABLE IF NOT EXISTS DESPENSA " +
                "(BARCODE TEXT NOT NULL, " +
                "DATA_VALIDADE TEXT NOT NULL, " +
                "QUANTIDADE INT NULL)";
        String createTableHistoricoPrecos = "CREATE TABLE IF NOT EXISTS HISTORICO_PRECOS" +
                "(" +
                "BARCODE              TEXT NOT NULL," +
                "QUANTIDADE           INT NULL," +
                "VALOR                INT NULL," +
                "DATA COMPRA          TEXT NULL" +
                "ID_MERCADO INT NULL)";
        String createTableListaCompras = "CREATE TABLE IF NOT EXISTS  LISTA_COMPRAS" +
                "(" +
                "BARCODE              TEXT NOT NULL," +
                "QUANTIDADE           INT NULL" +
                ")";
        String createTableProduto = "CREATE TABLE IF NOT EXISTS PRODUTOS" +
                "(" +
                "BARCODE              TEXT NOT NULL," +
                "NOME_PRODUTO         TEXT NULL," +
                "ID_MEDIDA            INT NULL," +
                "QUANTIDADE           INT NULL," +
                "DESCRICAO            TEXT NULL," +
                "ID_TIPO              INT NULL," +
                "ID_SUBTIPO           INT NULL," +
                "FAVORITO             NUMERIC NULL" +
                ")";
        sqLiteDatabase.execSQL(createTableDespensa);
        sqLiteDatabase.execSQL(createTableListaCompras);
        sqLiteDatabase.execSQL(createTableHistoricoPrecos);
        sqLiteDatabase.execSQL(createTableProduto);

    }



}
