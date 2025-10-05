package se.kth.webshop.web;

import se.kth.webshop.model.Cart;
import se.kth.webshop.model.Product;
import se.kth.webshop.service.CartService;
import se.kth.webshop.service.ProductService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final ProductService productService = new ProductService();
    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Visa korgen
        Cart cart = cartService.getCart(req.getSession());
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        String idParam = req.getParameter("id");

        if ("add".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            Product p = productService.getAllProducts().stream()
                    .filter(x -> x.getId() == id)
                    .findFirst().orElse(null);
            if (p != null) {
                Cart cart = cartService.getCart(req.getSession());
                cart.add(p);
            }
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        if ("removeAll".equals(action) && idParam != null) {
            int id = Integer.parseInt(idParam);
            Cart cart = cartService.getCart(req.getSession());
            cart.removeAll(id);
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        // fallback: visa korg
        doGet(req, resp);
    }
}