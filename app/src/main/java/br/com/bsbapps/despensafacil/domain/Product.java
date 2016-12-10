package br.com.bsbapps.despensafacil.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.bsbapps.despensafacil.DatabaseOpenHelper;

/**
 * Created by proca on 02/12/2016.
 * Classe Product - Representa um produto na base
 */

public class Product {
    // Database
    private SQLiteDatabase database;
    private DatabaseOpenHelper dbHelper;

    // Colunas
    private String[] allColumns = {DatabaseOpenHelper.COLUMN_BARCODE,
            DatabaseOpenHelper.COLUMN_PRODUCT_NAME,
            DatabaseOpenHelper.COLUMN_METRIC_UNIT_ID,
            DatabaseOpenHelper.COLUMN_QUANTITY,
            DatabaseOpenHelper.COLUMN_DESCRIPTION,
            DatabaseOpenHelper.COLUMN_TYPE_ID,
            DatabaseOpenHelper.COLUMN_SUBTYPE_ID,
            DatabaseOpenHelper.COLUMN_FAVORITE,
            DatabaseOpenHelper.COLUMN_STATUS};

    // Variáveis privadas
    private String barcode;
    private String productName;
    private int metricUnitId;
    private int quantity;
    private String description;
    private int typeId;
    private int subTypeId;
    private int favorite;
    private int status;

    // Construtor
    public Product(Context context) {
        dbHelper = new DatabaseOpenHelper(context);
    }

    // Classes de definição e captura das variáveis privadas
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getMetricUnitId() {
        return metricUnitId;
    }

    public void setMetricUnitId(int metricUnitId) {
        this.metricUnitId = metricUnitId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(int subTypeId) {
        this.subTypeId = subTypeId;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Método de abertura do database
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Metodo de fechamento do database
    public void close() {
        dbHelper.close();
    }

    // Método de inserção
    public long insert(){
        ContentValues valores = new ContentValues();
        valores.put("BARCODE", barcode);
        valores.put("PRODUCT_NAME", productName);
        valores.put("METRIC_UNITY_ID ", metricUnitId);
        valores.put("QUANTITY ",quantity);
        valores.put("DESCRIPTION",description);
        valores.put("TYPE_ID",typeId);
        valores.put("SUBTYPE_ID",subTypeId);
        valores.put("FAVORITE",favorite);
        valores.put("STATUS", status);
        open();
        long result = database.insert(DatabaseOpenHelper.TABLE_PRODUCT,null,valores);
        close();
        return result;
    }

    //Método de deleção
    public void delete() {
        open();
        database.delete(DatabaseOpenHelper.TABLE_PRODUCT, DatabaseOpenHelper.COLUMN_BARCODE
                + " = 1" + barcode + "'", null);
        close();
    }

    public Cursor getProduct(String barcode){
        return database.query(DatabaseOpenHelper.TABLE_PRODUCT, null, "barcode='" + barcode + "'"
                , null, null, null, null);
    }

}
