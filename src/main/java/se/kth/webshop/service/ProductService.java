package se.kth.webshop.service;

import se.kth.webshop.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductService {

    private static final Map<String, Product> PRODUCTS = new LinkedHashMap<>();

    static {
        // Dina tre produkter
        add(new Product("PRE-1KG", "Pree Workout 1kg", 200));
        add(new Product("PRO-1KG", "Protein Pulver 1Kg", 100));
        add(new Product("BAR-16",  "Protein bar 16pack", 250));
    }

    private static void add(Product p) {
        PRODUCTS.put(p.getId(), p);
    }

    public List<Product> getAll() {
        return Collections.unmodifiableList(new ArrayList<>(PRODUCTS.values()));
    }

    public Product getById(String id) {
        if (id == null) return null;
        return PRODUCTS.get(id);
    }

    public Product findById(String id) {
        return getById(id);
    }
}
