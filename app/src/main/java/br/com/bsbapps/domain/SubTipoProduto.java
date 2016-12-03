package br.com.bsbapps.domain;

/**
 * Created by proca on 02/12/2016.
 */

public class SubTipoProduto {
    int idSubTipo;
    int idTipo;
    String nomeSubTipo;



    public static String createTable() {
        return "CREATE TABLE SUBTIPO" +
                "(" +
                "ID_SUBTIPO  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "ID_TIPO   INT NOT NULL," +
                "NOME_SUBTIPO       TEXT NOT NULL" +
                ")";
    }

    public int getIdSubTipo() {
        return idSubTipo;
    }

    public void setIdSubTipo(int idSubTipo) {
        this.idSubTipo = idSubTipo;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public String getNomeSubTipo() {
        return nomeSubTipo;
    }

    public void setNomeSubTipo(String nomeSubTipo) {
        this.nomeSubTipo = nomeSubTipo;
    }

    public SubTipoProduto(int idSubTipo, int idTipo, String nomeSubTipo) {
        this.idSubTipo = idSubTipo;
        this.idTipo = idTipo;
        this.nomeSubTipo = nomeSubTipo;
    }
}
