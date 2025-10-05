package se.kth.webshop.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import se.kth.webshop.model.Cart;
import se.kth.webshop.service.CartService;

import java.io.IOException;

@WebServlet(name = "CartServlet", urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {

    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?from=" + request.getContextPath() + "/cart");
            return;
        }

        String action = request.getParameter("action");
        if ("removeAll".equals(action)) {
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