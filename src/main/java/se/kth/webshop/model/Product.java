package se.kth.webshop.model;

public class Product {
    private final String id;
    private final String name;
    private final int priceSek; // pris i kronor

    public Product(String id, String name, int priceSek) {
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
