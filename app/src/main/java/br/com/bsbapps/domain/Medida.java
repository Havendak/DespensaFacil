package br.com.bsbapps.domain;

/**
 * Created by proca on 02/12/2016.
 */

public class Medida {
    int idMedida;
    String nomeMedida;

    public Medida(int idMedida, String nomeMedida) {
        this.idMedida = idMedida;
        this.nomeMedida = nomeMedida;
    }

    public int getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(int idMedida) {
        this.idMedida = idMedida;
    }

    public String getNomeMedida() {
        return nomeMedida;
    }

    public void setNomeMedida(String nomeMedida) {
        this.nomeMedida = nomeMedida;
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS MEDIDA" +
                "(" +
                "ID_MEDIDA INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "NOME_MEDIDA          TEXT NULL" +
                ")";

    }
}
