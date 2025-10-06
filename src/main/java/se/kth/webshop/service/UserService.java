package se.kth.webshop.service;


import se.kth.webshop.model.Cart;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class UserService {
    private static final Map<String, String> USERS = new ConcurrentHashMap<>();
    private static final Map<String, Cart> USER_CARTS = new ConcurrentHashMap<>();

    public static boolean register(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) return false;
        return USERS.putIfAbsent(username, password) == null;
    }

    public static boolean validate(String username, String password) {
        String stored = USERS.get(username);
        return stored != null && stored.equals(password);
    }

    public static Cart getOrCreateCart(String username) {
        return USER_CARTS.computeIfAbsent(username, k -> new Cart());
    }

    public static void saveCart(String username, Cart cart) {
        if (username != null && cart != null) {
            USER_CARTS.put(username, cart);
        }
    }
}
