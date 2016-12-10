package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Andre Becklas on 28/11/2016.
 *
 * Cria conexão com o banco de dados e gerencia estrutura das tabelas
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    String createQuery;

    // Tabelas
    public static final String TABLE_PRODUCT = "product";
    public static final String TABLE_PANTRY_ITEM = "pantry_item";
    public static final String TABLE_PANTRY_LIST = "pantry_list";

    // Colunas
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BARCODE = "barcode";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_METRIC_UNIT_ID = "metric_unit_id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_TYPE_ID = "type_id";
    public static final String COLUMN_SUBTYPE_ID = "subtype_id";
    public static final String COLUMN_FAVORITE = "favorite";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_DUE_DATE = "due_date";
    public static final String COLUMN_BUY_DATE = "buy_date";
    public static final String COLUMN_LIST_ID = "list_id";
    public static final String COLUMN_LIST_NAME = "list_name";
    public static final String COLUMN_DEFAULT = "default";

    // Database
    private static final String DATABASE_NAME = "despensafacil.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // Código utilizado para testes. Comentar ao finalizar os testes
        try {
                context.deleteDatabase("dfdb");
        } catch(Exception e) {

        }
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Criação da tabela product
        createQuery="CREATE TABLE " + TABLE_PRODUCT + "(" +
                COLUMN_BARCODE + " TEXT PRIMARY KEY ASC NOT NULL, " +
                COLUMN_PRODUCT_NAME + " TEXT NULL, " +
                COLUMN_METRIC_UNIT_ID + " INT NULL," +
                COLUMN_QUANTITY + " INT NULL DEFAULT 0," +
                COLUMN_DESCRIPTION + " TEXT NULL," +
                COLUMN_TYPE_ID + " INT NULL," +
                COLUMN_SUBTYPE_ID + " INT NULL," +
                COLUMN_FAVORITE + " NUMERIC NOT NULL DEFAULT 0," +
                COLUMN_STATUS + " NUMERIC NOT NULL DEFAULT 0" +
                ")";
        db.execSQL(createQuery);

        //Criação da tabela pantry_item
        createQuery="CREATE TABLE " + TABLE_PANTRY_ITEM + "(" +
                COLUMN_ID + " INT PRIMARY KEY ASC AUTOINCREMENT, " +
                COLUMN_LIST_ID + " INT NOT NULL, " +
                COLUMN_BARCODE + " TEXT NOT NULL, " +
                COLUMN_BUY_DATE + " INT NULL, " +
                COLUMN_DUE_DATE + " INT NULL, " +
                COLUMN_QUANTITY + " INT NULL DEFAULT 0," +
                COLUMN_STATUS + " NUMERIC NOT NULL DEFAULT 0" +
                ")";
        db.execSQL(createQuery);

        //Criação da tabela pantry_list
        createQuery="CREATE TABLE " + TABLE_PANTRY_LIST + "(" +
                COLUMN_LIST_ID + " INT PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT NULL, " +
                COLUMN_STATUS + " NUMERIC NOT NULL DEFAULT 0" +
                ")";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY_LIST);
        onCreate(db);
    }

   /* private void populaTabelas(SQLiteDatabase db) {
        Cursor cur = db.rawQuery("SELECT EXISTS (SELECT 1 FROM PANTRY_LIST)", null);

        if (cur != null) {
            cur.moveToFirst();

            if (cur.getInt (0) == 0) {

                List<PantryItem> pantryItemList = new ArrayList<PantryItem>();
                pantryItemList.add(new PantryItem("7894321711263","2016-12-20",1));
                pantryItemList.add(new PantryItem("7898024394181","2016-12-15",1));
                pantryItemList.add(new PantryItem("7894321711263","2017-08-20",2));
                PantryList pantryList = new PantryList(1,pantryItemList );
                pantryList.insert(db);

            }
        }
        cur = db.rawQuery("SELECT EXISTS (SELECT 1 FROM PRODUCT)", null);
        if (cur != null) {
            cur.moveToFirst();
            if (cur.getInt (0) == 0) {
                Product produto = new Product("7894321711263","Toddy",1,400,"Achocolatado Toddy 400g",1,1,0,0);
                produto.insert(db);
                produto = new Product("7898024394181","Nutella",1,350,"Creme de Avelã Nutella 350g",1,1,0,0);
                produto.insert(db);
                Log.d("Caminho", "inseriu produto");

            }
        }
    }
    */
}
