package br.com.bsbapps.domain;

/**
 * Created by proca on 02/12/2016.
 * Representa um item de uma lista da despensa
 */

public class PantryItem {
    String barcode;
    String dueDate;
    int quantity;

    public PantryItem(String barcode, String dueDate, int quantity) {
        this.barcode = barcode;
        this.dueDate = dueDate;
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
