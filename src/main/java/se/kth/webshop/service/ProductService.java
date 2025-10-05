package se.kth.webshop.service;

import se.kth.webshop.model.Product;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.util.List;
import java.util.ArrayList;

public class ProductService {
    private List<Product> products = new ArrayList<>();

    public ProductService() {
        products.add(new Product(1, "Energigelé", 25.0));
        products.add(new Product(2, "Proteinbar", 30.0));
        products.add(new Product(3, "Sportdryck", 20.0));
    }

    public List<Product> getAllProducts() {
        return products;
    }
}