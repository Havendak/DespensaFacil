package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import br.com.bsbapps.domain.GroceryList;
import br.com.bsbapps.domain.HistoricoPrecos;
import br.com.bsbapps.domain.Medida;
import br.com.bsbapps.domain.PantryItem;
import br.com.bsbapps.domain.PantryList;
import br.com.bsbapps.domain.Product;
import br.com.bsbapps.domain.SubTipoProduto;
import br.com.bsbapps.domain.TipoProduto;

/**
 * Created by proca on 28/11/2016.
 * esta classe deve ser apagada posi foi substituida pela classe DataBaseOpenHelper
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

        db.execSQL(PantryList.createTable());
        db.execSQL(GroceryList.createTable());
        db.execSQL(HistoricoPrecos.createTable());
        db.execSQL(Product.createTable());
        db.execSQL(Medida.createTable());
        db.execSQL(TipoProduto.createTable());
        db.execSQL(SubTipoProduto.createTable());
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
