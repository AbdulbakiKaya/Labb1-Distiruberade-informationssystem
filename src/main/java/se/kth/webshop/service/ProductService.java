package se.kth.webshop.service;

import se.kth.webshop.model.Product;
import java.util.*;

public class ProductService {
    private List<Product> products = new ArrayList<>();

    public ProductService() {
        // Exempeldata (senare byter vi till JDBC/H2)
        products.add(new Product(1, "Energigelé", 25.0));
        products.add(new Product(2, "Proteinbar", 30.0));
        products.add(new Product(3, "Sportdryck", 20.0));
    }

    public List<Product> getAllProducts() {
        return products;
    }
}