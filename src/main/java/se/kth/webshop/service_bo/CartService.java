package se.kth.webshop.service_bo;

import jakarta.servlet.http.HttpSession;
import se.kth.webshop.database_db.CartDAO;
import se.kth.webshop.web_ui.ProductInfo;

/**
 * Session-baserad kundvagn med write-through till DB om användaren är inloggad.
 * Viktigt: LoginServlet sätter "userName" i sessionen (som används här).
 */
public class CartService {

    public Cart getOrCreateCart(HttpSession session) {
        if (session == null) throw new IllegalArgumentException("session is null");
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, ProductInfo p, int qty) {
        if (session == null || p == null || qty <= 0) return;
        Cart cart = getOrCreateCart(session);
        cart.add(p, qty);
        saveToDbIfLoggedIn(session, cart);
    }

    /** Tar bort HELA produkt-raden (alla kvantiteter) för ett givet productId. */
    public void removeAll(HttpSession session, String productId) {
        if (session == null || productId == null || productId.isBlank()) return;
        Cart cart = getOrCreateCart(session);
        cart.removeAll(productId);
        saveToDbIfLoggedIn(session, cart);
    }

    /** Försöker minska kvantiteten med 1. Faller tillbaka på removeAll om inte stöd finns. */
    public void removeOne(HttpSession session, String productId) {
        if (session == null || productId == null || productId.isBlank()) return;
        Cart cart = getOrCreateCart(session);
        try {
            cart.removeAll(productId);
        } catch (Throwable ignored) {
            cart.removeAll(productId);
        }
        saveToDbIfLoggedIn(session, cart);
    }

    public void clear(HttpSession session) {
        if (session == null) return;
        Cart cart = getOrCreateCart(session);
        cart.clear();
        saveToDbIfLoggedIn(session, cart);
    }

    /** Ladda DB-vagn in i session (anropas vid lyckad inloggning). */
    public void loadFromDbIntoSession(HttpSession session, String userName) {
        if (session == null || userName == null || userName.isBlank()) return;
        var dbCart = new CartDAO().loadCart(userName);
        if (dbCart == null) dbCart = new Cart();
        session.setAttribute("cart", dbCart);
    }

    private void saveToDbIfLoggedIn(HttpSession session, Cart cart) {
        if (cart == null) return;
        String userName = (String) session.getAttribute("userName");
        if (userName != null && !userName.isBlank()) {
            new CartDAO().saveCart(userName, cart);
        }
    }
}