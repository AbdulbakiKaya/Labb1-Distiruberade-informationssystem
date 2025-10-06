package se.kth.webshop.web_ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import se.kth.webshop.service_bo.CartService;
import se.kth.webshop.service_bo.UserService;

/**
 * Loggar in användaren, sätter "userName" i session och laddar kundvagn från DB.
 * Förutsätter att UserService.validate(...) finns och fungerar.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!UserService.validate(username, password)) {
            request.setAttribute("error", "Fel användarnamn eller lösenord");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("userName", username);

        new CartService().loadFromDbIntoSession(session, username);

        String from = request.getParameter("from");
        if (from == null || from.isBlank() || !from.startsWith(request.getContextPath())) {
            from = request.getContextPath() + "/products";
        }
        response.sendRedirect(from);
    }
}