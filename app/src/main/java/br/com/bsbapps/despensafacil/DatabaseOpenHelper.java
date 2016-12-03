package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.bsbapps.domain.GroceryList;

import br.com.bsbapps.domain.PantryItem;
import br.com.bsbapps.domain.PantryList;
import br.com.bsbapps.domain.Product;


/**
 * Created by Andre Becklas on 28/11/2016.
 * Alterado por Procaci em 03/12/2016
 *      - iseridas as criações de tabelas para os objetos PantryList, GroceryList e Produt
 *      - inserido o metodo de exemplo de insert populaTabelas
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public DatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        db.execSQL(PantryList.createTable());
        db.execSQL(GroceryList.createTable());

        db.execSQL(Product.createTable());



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
    private void populaTabelas(SQLiteDatabase db) {
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
}
