package se.kth.webshop.service_bo;

import se.kth.webshop.web_ui.ProductInfo;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private final Map<String, CartItem> items = new LinkedHashMap<>();

    public void add(ProductInfo p, int qty) {
        if (p == null || qty <= 0) return;
        CartItem item = items.get(p.getId());
        if (item == null) {
            item = new CartItem(p.getId(), p.getName(), p.getPriceSek(), qty);
            items.put(p.getId(), item);
        } else {
            item.addQuantity(qty);
        }
    }

    public void removeAll(String productId) {
        if (productId != null) {
            items.remove(productId);
        }
    }

    public void clear() {
        items.clear();
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public int getTotalSek() {
        return items.values().stream().mapToInt(CartItem::getLineTotalSek).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
