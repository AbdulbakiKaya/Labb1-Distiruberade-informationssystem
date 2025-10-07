package se.kth.webshop.database_db;


import se.kth.webshop.service_bo.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public Product[] getAll;

    public List<Product> findAll() {
        String sql = "SELECT id, name, price_sek FROM PRODUCTS ORDER BY name";
        List<Product> out = new ArrayList<>();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Product(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("price_sek")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    public Product findById(String id) {
        String sql = "SELECT id, name, price_sek FROM PRODUCTS WHERE id = ?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getInt("price_sek")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}