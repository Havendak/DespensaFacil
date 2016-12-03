package br.com.bsbapps.domain;

/**
 * Created by proca on 02/12/2016.
 */

public class TipoProduto {
    int idTipo;
    String nomeTipo;

    public TipoProduto(int idTipo, String nomeTipo) {
        this.idTipo = idTipo;
        this.nomeTipo = nomeTipo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNomeTipo() {
        return nomeTipo;
    }

    public void setNomeTipo(String nomeTipo) {
        this.nomeTipo = nomeTipo;
    }

    public static String createTable()  {
        return "CREATE TABLE IF NOT EXISTS TIPO" +
                "(" +
                "ID_TIPO INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "NOME_TIPO          TEXT NOT NULL" +
                ")";
    }
}
