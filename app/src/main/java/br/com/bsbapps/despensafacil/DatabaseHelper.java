package br.com.bsbapps.despensafacil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



import br.com.bsbapps.domain.Despensa;
import br.com.bsbapps.domain.HistoricoPrecos;
import br.com.bsbapps.domain.ListaDeCompras;
import br.com.bsbapps.domain.Medida;
import br.com.bsbapps.domain.Produto;
import br.com.bsbapps.domain.SubTipoProduto;
import br.com.bsbapps.domain.TipoProduto;

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

        db.execSQL(Despensa.createTable());
        db.execSQL(ListaDeCompras.createTable());
        db.execSQL(HistoricoPrecos.createTable());
        db.execSQL(Produto.createTable());
        db.execSQL(Medida.createTable());
        db.execSQL(TipoProduto.createTable());
        db.execSQL(SubTipoProduto.createTable());
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
                Produto produto = new Produto("7894321711263","Toddy",1,400,"Achocolatado Toddy 400g",1,1,0,0);
                produto.insert(db);
                produto = new Produto("7898024394181","Nutella",1,350,"Creme de Avelã Nutella 350g",1,1,0,0);
                produto.insert(db);

            }
        }


    }


}
