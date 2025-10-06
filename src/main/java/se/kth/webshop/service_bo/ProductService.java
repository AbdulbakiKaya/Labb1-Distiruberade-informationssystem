package se.kth.webshop.service_bo;

import se.kth.webshop.database_db.ProductDAO;
import java.util.List;

public class ProductService {

    private final ProductDAO dao = new ProductDAO();

    public List<Product> getAll() {
        return dao.findAll();
    }

    public Product getById(String id) {
        if (id == null || id.isBlank()) return null;
        return dao.findById(id);
    }

    public Product findById(String id) {
        return getById(id);
    }
}