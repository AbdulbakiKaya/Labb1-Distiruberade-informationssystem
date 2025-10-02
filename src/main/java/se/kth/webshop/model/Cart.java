package se.kth.webshop.model;

import java.util.*;

public class Cart {
    private final Map<Integer, CartItem> items = new LinkedHashMap<>();

    public void add(Product p) {
        items.compute(p.getId(), (id, existing) -> {
            if (existing == null) return new CartItem(p);
            existing.increment();
            return existing;
        });
    }

    public void removeOne(int productId) {
        CartItem ci = items.get(productId);
        if (ci == null) return;
        ci.decrement();
        if (ci.getQuantity() == 1) { /* behåll 1 kvar, inget mer */ }
    }

    public void removeAll(int productId) {
        items.remove(productId);
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public double getTotal() {
        return items.values().stream().mapToDouble(CartItem::getLineTotal).sum();
    }

    public boolean isEmpty() { return items.isEmpty(); }
}