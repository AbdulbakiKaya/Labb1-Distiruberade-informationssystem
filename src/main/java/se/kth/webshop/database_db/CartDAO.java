package se.kth.webshop.database_db;

import se.kth.webshop.service_bo.Cart;
import se.kth.webshop.service_bo.Product;
import se.kth.webshop.web_ui.ProductInfo;

import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public Cart loadCart(String username) {
        if (username == null || username.isBlank()) return null;

        String sql = """
            SELECT p.id, p.name, p.price_sek, ci.qty
            FROM CART_ITEMS ci
            JOIN PRODUCTS p ON p.id = ci.product_id
            WHERE ci.username = ?
            ORDER BY p.name
        """;

        Cart cart = new Cart();
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductInfo p = new ProductInfo(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getInt("price_sek")
                    );
                    cart.add(p, rs.getInt("qty"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load cart for " + username, e);
        }
        return cart;
    }

    /** Sparar hela vagnen atomärt: rensar först, sedan skriver in alla rader. */
    public void saveCart(String username, Cart cart) {
        if (username == null || username.isBlank() || cart == null) return;

        String deleteSql = "DELETE FROM CART_ITEMS WHERE username = ?";
        String insertSql = "MERGE INTO CART_ITEMS(username, product_id, qty) KEY(username, product_id) VALUES(?,?,?)";

        try (Connection con = ConnectionFactory.getConnection()) {
            con.setAutoCommit(false);

            try (PreparedStatement del = con.prepareStatement(deleteSql)) {
                del.setString(1, username);
                del.executeUpdate();
            }

            try (PreparedStatement ins = con.prepareStatement(insertSql)) {
                // Vi antar att cart.getItems() finns; vi använder reflection på varje CartItem för att hämta productId + qty.
                List<?> items = new ArrayList<>(cart.getItems());
                for (Object oi : items) {
                    String productId = resolveProductId(oi);
                    int qty = resolveQty(oi);
                    if (productId == null || productId.isBlank() || qty <= 0) continue;

                    ins.setString(1, username);
                    ins.setString(2, productId);
                    ins.setInt(3, qty);
                    ins.addBatch();
                }
                ins.executeBatch();
            }

            con.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save cart for " + username, e);
        }
    }


    /** Försöker hämta productId från ett CartItem via flera vanliga getter-namn. */
    private String resolveProductId(Object cartItem) {
        if (cartItem == null) return null;

        // 1) Finns getProduct(): Product ? Ta getId() från Product.
        Object productObj = callNoArg(cartItem, "getProduct");
        if (productObj == null) productObj = callNoArg(cartItem, "getP");
        if (productObj instanceof Product) {
            String id = (String) callNoArg(productObj, "getId");
            if (id != null) return id;
        }

        // 2) Finns getProductId(): String ?
        Object pid = callNoArg(cartItem, "getProductId");
        if (pid instanceof String) return (String) pid;

        // 3) Finns getId(): String ? (om CartItem själv bär id)
        Object maybeId = callNoArg(cartItem, "getId");
        if (maybeId instanceof String) return (String) maybeId;

        // 4) Som sista chans: om getProduct() fanns men inte var Product-klass, försök getId() på den.
        if (productObj != null) {
            Object pid2 = callNoArg(productObj, "getId");
            if (pid2 instanceof String) return (String) pid2;
        }

        return null;
    }

    /** Försöker hämta qty från ett CartItem via flera vanliga getter-namn. */
    private int resolveQty(Object cartItem) {
        if (cartItem == null) return 0;

        Integer q = tryIntGetter(cartItem, "getQuantity");
        if (q != null) return q;
        q = tryIntGetter(cartItem, "getQty");
        if (q != null) return q;
        q = tryIntGetter(cartItem, "getQ");
        if (q != null) return q;

        return 0;
    }

    private Object callNoArg(Object target, String methodName) {
        try {
            Method m = target.getClass().getMethod(methodName);
            m.setAccessible(true);
            return m.invoke(target);
        } catch (Exception ignored) {
            return null;
        }
    }

    private Integer tryIntGetter(Object target, String methodName) {
        try {
            Method m = target.getClass().getMethod(methodName);
            m.setAccessible(true);
            Object v = m.invoke(target);
            if (v instanceof Integer) return (Integer) v;
            if (v instanceof Number) return ((Number) v).intValue();
        } catch (Exception ignored) {}
        return null;
    }
}