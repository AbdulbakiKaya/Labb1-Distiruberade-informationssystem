package se.kth.webshop.database_db;


import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.sql.Statement;

@WebListener
public class DatabaseBootstrap implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement()) {

            st.executeUpdate("""
                MERGE INTO PRODUCTS (id, name, price_sek) KEY(id)
                VALUES
                  ('PRE-1KG', 'Pree Workout 1kg', 200),
                  ('PRO-1KG', 'Protein Pulver 1Kg', 100),
                  ('CRE-300', 'Kreatin Monohydrat 300g', 150)
            """);

            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS CART_ITEMS(
                  username   VARCHAR(100) NOT NULL,
                  product_id VARCHAR(50)  NOT NULL,
                  qty        INT          NOT NULL CHECK (qty > 0),
                  PRIMARY KEY(username, product_id),
                  FOREIGN KEY (username)   REFERENCES USERS(username)   ON DELETE CASCADE,
                  FOREIGN KEY (product_id) REFERENCES PRODUCTS(id)      ON DELETE CASCADE
                )
            """);

        } catch (Exception e) {
            throw new RuntimeException("DB bootstrap failed", e);
        }
    }
}