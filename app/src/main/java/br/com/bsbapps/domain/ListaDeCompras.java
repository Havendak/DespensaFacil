package br.com.bsbapps.domain;

/**
 * Created by proca on 02/12/2016.
 */

public class ListaDeCompras {


    String barcode;
    int quantidade;

    public ListaDeCompras(String barcode, int quantidade) {
        this.barcode = barcode;
        this.quantidade = quantidade;
    }
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }


    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS  LISTA_COMPRAS" +
            "(" +
            "BARCODE              TEXT NOT NULL," +
            "QUANTIDADE           INT NULL" +
            ")";
    }
}
