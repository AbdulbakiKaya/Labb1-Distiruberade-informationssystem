package se.kth.webshop.web;

import jakarta.servlet.ServletException;
import se.kth.webshop.model.Product;
import se.kth.webshop.service.ProductService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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

        request.getRequestDispatcher("/products.jsp").forward(request, response);
    }
}