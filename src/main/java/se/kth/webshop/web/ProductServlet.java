package se.kth.webshop.web;

import se.kth.webshop.model.Product;
import se.kth.webshop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Product> products = productService.getAllProducts();
        request.setAttribute("products", products);

        // Skicka vidare till JSP
        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}