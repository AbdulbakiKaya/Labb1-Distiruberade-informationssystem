package se.kth.webshop.web_ui;
public final class ProductInfo {

    private final String id;
    private final String name;
    private final int priceSek;

    public ProductInfo(String id, String name, int priceSek) {
        this.id = id;
        this.name = name;
        this.priceSek = priceSek;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriceSek() {
        return priceSek;
    }

}
