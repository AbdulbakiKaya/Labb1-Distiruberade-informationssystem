package se.kth.webshop.service;

import jakarta.servlet.http.HttpSession;
import se.kth.webshop.model.Cart;
import se.kth.webshop.model.Product;

public class CartService {

    public Cart getOrCreateCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, Product p, int qty) {
        if (session == null || p == null || qty <= 0) return;
        Cart cart = getOrCreateCart(session);
        cart.add(p, qty);
    }

    public void removeAll(HttpSession session, String productId) {
        if (session == null) return;
        Cart cart = getOrCreateCart(session);
        cart.removeAll(productId);
    }

    public void clear(HttpSession session) {
        if (session == null) return;
        Cart cart = getOrCreateCart(session);
        cart.clear();
    }
}