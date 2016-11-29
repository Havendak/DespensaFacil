package domain;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by proca on 29/11/2016.
 */

public class Produto {

    String barcode;
    String nomeProduto;
    int idMedida;
    int quantidade;
    String descricao;
    int idTipo;
    int idSubtipo;
    int favorito;

    public Produto(String barcode, String nomeProduto, int idMedida, int quantidade, String descricao, int idTipo, int idSubtipo, int favorito) {
        this.barcode = barcode;
        this.nomeProduto = nomeProduto;
        this.idMedida = idMedida;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.idTipo = idTipo;
        this.idSubtipo = idSubtipo;
        this.favorito = favorito;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public int getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(int idMedida) {
        this.idMedida = idMedida;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdSubtipo() {
        return idSubtipo;
    }

    public void setIdSubtipo(int idSubtipo) {
        this.idSubtipo = idSubtipo;
    }

    public int getFavorito() {
        return favorito;
    }

    public void setFavorito(int favorito) {
        this.favorito = favorito;
    }

    public static String createTable(){
        return   "CREATE TABLE IF NOT EXISTS PRODUTO" +
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
    }

    public void insert(SQLiteDatabase db){
        ContentValues valores = new ContentValues();
        valores.put("BARCODE", barcode);
        valores.put("NOME_PRODUTO", nomeProduto);
        valores.put("ID_MEDIDA",idMedida);
        valores.put("QUANTIDADE",quantidade);
        valores.put("DESCRICAO",descricao);
        valores.put("ID_TIPO",idTipo);
        valores.put("ID_SUBTIPO",idSubtipo);
        valores.put("FAVORITO",favorito);
        long resultado = db.insert("PRODUTO",null,valores);
        Log.d("Insert produto: ", String.valueOf(resultado));

    }

}
