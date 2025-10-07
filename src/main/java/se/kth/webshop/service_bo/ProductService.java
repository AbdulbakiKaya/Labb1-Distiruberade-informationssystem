package se.kth.webshop.service_bo;

import se.kth.webshop.database_db.ProductDAO;
import se.kth.webshop.web_ui.ProductInfo;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private final ProductDAO dao = new ProductDAO();

    public List<ProductInfo> getAll() {
        List<ProductInfo> products = new ArrayList<>();
        for (Product product : dao.findAll()) {
            products.add(new ProductInfo(product.getId(), product.getName(), product.getPriceSek()));
        }
        return products;
    }

    public ProductInfo getById(String id) {
        Product product = dao.findById(id);
        return new ProductInfo(product.getId(), product.getName() ,product.getPriceSek());
    }

}