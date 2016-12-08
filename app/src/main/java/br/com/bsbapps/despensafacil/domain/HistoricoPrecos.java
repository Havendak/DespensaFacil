package br.com.bsbapps.despensafacil.domain;

/**
 * Created by proca on 02/12/2016.
 */

public class HistoricoPrecos {
    String barcode;
    int quantidade;
    int valor;
    int dataCompra;
    int idMercado;

    public HistoricoPrecos(String barcode, int quantidade, int valor, int dataCompra, int idMerdaco) {
        this.barcode = barcode;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataCompra = dataCompra;
        this.idMercado = idMerdaco;
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

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public int getDataCompra() {
        return dataCompra;
    }

    public void setDataCompra(int dataCompra) {
        this.dataCompra = dataCompra;
    }

    public int getIdMercado() {
        return idMercado;
    }

    public void setIdMercado(int idMerdaco) {
        this.idMercado = idMerdaco;
    }

    public static String createTable() {
        return "CREATE TABLE IF NOT EXISTS HISTORICO_PRECOS" +
                "(" +
                "BARCODE              TEXT NOT NULL," +
                "QUANTIDADE           INT NULL," +
                "VALOR                INT NULL," +
                "DATA COMPRA          INT NULL" +
                "ID_MERCADO INT NULL)";
    }
}
