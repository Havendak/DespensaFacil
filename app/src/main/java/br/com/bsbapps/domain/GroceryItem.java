package br.com.bsbapps.domain;

/**
 * Created by proca on 02/12/2016.
 * representa um item de uma lista de compras
 */

public class GroceryItem {
    String barcode;
    int quantity;

    public GroceryItem(String barcode, int quantity) {
        this.barcode = barcode;
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
