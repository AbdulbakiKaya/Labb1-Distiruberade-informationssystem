package se.kth.webshop.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import se.kth.webshop.service.UserService;
import se.kth.webshop.model.Cart;


@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String user = (String) session.getAttribute("userName");
            Cart cart = (Cart) session.getAttribute("cart");
            if (user != null && cart != null) {
                UserService.saveCart(user, cart);
            }
            session.invalidate(); // logga ut
        }

        response.sendRedirect(request.getContextPath() + "/");
    }

}