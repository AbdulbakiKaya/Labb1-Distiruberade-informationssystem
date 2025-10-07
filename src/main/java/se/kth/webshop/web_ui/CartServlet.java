package se.kth.webshop.web_ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import se.kth.webshop.service_bo.Cart;
import se.kth.webshop.service_bo.CartService;
import se.kth.webshop.service_bo.ProductService;

import java.io.IOException;

/**
 * Hanterar visning och ändringar av kundvagn.
 * Stödjer actions: add, remove (ta bort EN rad), removeAll (alias), clear.
 */
@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private final CartService cartService = new CartService();
    private final ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        Cart cart = cartService.getOrCreateCart(session);
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        String action = param(request);

        switch (action) {
            case "add" -> {
                String productId = param(request, "productId", "id");
                int qty = parseIntOrDefault(request.getParameter("qty"));
                ProductInfo p = productService.getById(productId);
                if (p != null && qty > 0) {
                    cartService.addToCart(session, p, qty);
                }
                redirectBack(request, response);
            }
            case "remove", "removeAll" -> {
                String productId = param(request, "productId", "id");
                cartService.removeAll(session, productId); // tar bort hela raden
                redirectBack(request, response);
            }
            case "clear" -> {
                cartService.clear(session);
                redirectBack(request, response);
            }
            default -> doGet(request, response);
        }
    }

    private String param(HttpServletRequest req, String name, String altName) {
        String v = req.getParameter(name);
        if (v == null || v.isBlank()) v = req.getParameter(altName);
        return v;
    }

    private String param(HttpServletRequest req) {
        return param(req, "action", null);
    }

    private int parseIntOrDefault(String s) {
        try {
            return (s == null || s.isBlank()) ? 1 : Integer.parseInt(s);
        } catch (Exception e) {
            return 1;
        }
    }

    private void redirectBack(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String ref = req.getHeader("Referer");
        if (ref != null && !ref.isBlank()) {
            resp.sendRedirect(ref);
        } else {
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}