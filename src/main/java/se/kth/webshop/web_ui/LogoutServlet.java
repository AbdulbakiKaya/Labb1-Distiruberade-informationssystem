package se.kth.webshop.web_ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import se.kth.webshop.database_db.CartDAO;
import se.kth.webshop.service_bo.Cart;

import java.io.IOException;

/** Sparar vagnen till DB (om userName finns) och invalidar sessionen. */
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String userName = (String) session.getAttribute("userName");
            Cart cart = (Cart) session.getAttribute("cart");
            if (userName != null && !userName.isBlank() && cart != null) {
                new CartDAO().saveCart(userName, cart);
            }
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}