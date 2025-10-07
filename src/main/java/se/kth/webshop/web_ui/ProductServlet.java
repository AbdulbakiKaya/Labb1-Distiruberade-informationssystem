package se.kth.webshop.web_ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import se.kth.webshop.service_bo.CartService;
import se.kth.webshop.service_bo.Product;
import se.kth.webshop.service_bo.ProductService;

import java.io.IOException;

@WebServlet(name = "ProductServlet", urlPatterns = {"/products"})
public class ProductServlet extends HttpServlet {

    private final ProductService productService = new ProductService();
    private final CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("products", productService.getAll());
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userName") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?from=" + request.getContextPath() + "/products");
            return;
        }

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String productId = request.getParameter("productId");
            int qty = 1;
            try {
                qty = Integer.parseInt(request.getParameter("qty"));
                if (qty <= 0) qty = 1;
            } catch (Exception ignored) {}

            ProductInfo productInfo = productService.getById(productId);
            ProductInfo product = new ProductInfo(
                    productInfo.getId(),
                    productInfo.getName(),
                    productInfo.getPriceSek()
            );
            cartService.addToCart(session, product, qty);

            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Fallback
        request.setAttribute("products", productService.getAll());
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}