package se.kth.webshop.service;

import se.kth.webshop.model.Cart;

import javax.servlet.http.HttpSession;

public class CartService {
    public static final String CART_KEY = "CART";

    public Cart getCart(HttpSession session) {
        Cart c = (Cart) session.getAttribute(CART_KEY);
        if (c == null) {
            c = new Cart();
            session.setAttribute(CART_KEY, c);
        }
        return c;
    }
}