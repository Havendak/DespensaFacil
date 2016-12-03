package br.com.bsbapps.domain;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by proca on 02/12/2016.
 */

public class Product {
    String barcode;
    String productName;
    int metricUnitId;
    int quantity;
    String description;
    int typeid;
    int subTypeid;
    int favorite;
    int status;

    public Product(String barcode, String productName, int metricUnitId, int quantity, String description, int typeid, int subTypeid, int favorite, int status) {
        this.barcode = barcode;
        this.productName = productName;
        this.metricUnitId = metricUnitId;
        this.quantity = quantity;
        this.description = description;
        this.typeid = typeid;
        this.subTypeid = subTypeid;
        this.favorite = favorite;
        this.status = status;
    }

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

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getSubTypeid() {
        return subTypeid;
    }

    public void setSubTypeid(int subTypeid) {
        this.subTypeid = subTypeid;
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

    public static String createTable(){
        return   "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(" +
                "BARCODE              TEXT PRIMARY KEY ASC NOT NULL," +
                "PRODUCT_NAME         TEXT NULL," +
                "METRIC_UNITY_ID            INT NULL," +
                "QUANTITY           INT NULL DEFAULT 0," +
                "DESCRIPTION            TEXT NULL," +
                "TYPE_ID              INT NULL," +
                "SUBTYPE_ID          INT NULL," +
                "FAVORITE             NUMERIC NOT NULL DEFAULT 0," +
                "STATUS               NUMERIC NOT NULL DEFAULT 0" +
                ")";


    }

    public void insert(SQLiteDatabase db){
        ContentValues valores = new ContentValues();
        valores.put("BARCODE", barcode);
        valores.put("PRODUCT_NAME", productName);
        valores.put("METRIC_UNITY_ID ", metricUnitId);
        valores.put("QUANTITY ",quantity);
        valores.put("DESCRIPTION",description);
        valores.put("TYPE_ID",typeid);
        valores.put("SUBTYPE_ID",subTypeid);
        valores.put("FAVORITE",favorite);
        valores.put("STATUS", status);
        long resultado = db.insert("PRODUCT",null,valores);
        Log.d("Insert produto: ", String.valueOf(resultado));

    }
}
