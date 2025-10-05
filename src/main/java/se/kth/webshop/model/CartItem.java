package se.kth.webshop.model;

public class CartItem {
    private final String productId;
    private final String productName;
    private final int unitPriceSek;
    private int quantity;

    public CartItem(String productId, String productName, int unitPriceSek, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.unitPriceSek = unitPriceSek;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getUnitPriceSek() {
        return unitPriceSek;
    }

    public int getQuantity() {
        return quantity;
    }

    public void addQuantity(int delta) {
        this.quantity += delta;
        if (this.quantity < 0) this.quantity = 0;
    }

    public int getLineTotalSek() {
        return unitPriceSek * quantity;
    }
}
