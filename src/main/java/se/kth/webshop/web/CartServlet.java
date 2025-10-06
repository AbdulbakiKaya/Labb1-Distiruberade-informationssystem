package se.kth.webshop.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import se.kth.webshop.model.Cart;
import se.kth.webshop.model.Product;
import se.kth.webshop.service.CartService;
import se.kth.webshop.service.ProductService;
import se.kth.webshop.service.UserService;

import java.io.IOException;

    @WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private final CartService cartService = new CartService();
    private final ProductService productService = new ProductService();

        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userName") != null) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                String user = (String) session.getAttribute("userName");
                cart = UserService.getOrCreateCart(user);
                session.setAttribute("cart", cart);
            }
        }

        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            String back = java.net.URLEncoder.encode(request.getContextPath() + "/cart", java.nio.charset.StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/login.jsp?from=" + back);

            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            String user = (String) session.getAttribute("userName");
            cart = UserService.getOrCreateCart(user);
            session.setAttribute("cart", cart);
        }


        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String productId = request.getParameter("productId");
            String qtyStr    = request.getParameter("qty");

            int qty;
            try {
                qty = Integer.parseInt(qtyStr);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            if (qty <= 0 || productId == null || productId.isBlank()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            Product product = productService.findById(productId);   // eller getById(...) – se punkt 3
            if (product == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            cart.add(product, qty);

            response.sendRedirect(request.getContextPath() + "/cart");
            return;

        }

        else if ("removeAll".equals(action)) {
            String productId = request.getParameter("productId");
            cartService.removeAll(session, productId);
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        } else if ("clear".equals(action)) {
            cartService.clear(session);
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }
}