package domain;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.com.bsbapps.util.DT;

/**
 * Created by proca on 28/11/2016.
 */

public class Despensa {
    String barcode;
    String dataValidade;
    int quantidade;

    public Despensa(String barcode, String dataValidade, int quantidade) {
        this.barcode = barcode;
        this.dataValidade = dataValidade;
        this.quantidade = quantidade;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(String dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    public static String createTable(){
           return "CREATE TABLE IF NOT EXISTS DESPENSA " +
                    "(BARCODE TEXT NOT NULL, " +
                    "DATA_VALIDADE TEXT NOT NULL, " +
                    "QUANTIDADE INT NULL)";
    }

    public void insert(SQLiteDatabase db){
            ContentValues valores = new ContentValues();
            valores.put("BARCODE", barcode);
            valores.put("DATA_VALIDADE", DT.stringToUTC(dataValidade));
            valores.put("QUANTIDADE", quantidade);
            long resultado = db.insert("DESPENSA",null,valores);
        Log.d("Insert despensa: ", String.valueOf(resultado));

    }
}
