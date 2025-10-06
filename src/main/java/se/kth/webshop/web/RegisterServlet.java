package se.kth.webshop.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import se.kth.webshop.service.UserService;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String u = req.getParameter("username");
        String p = req.getParameter("password");
        if (UserService.register(u, p)) {
            req.setAttribute("msg", "Konto skapat. Logga in.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Användarnamn upptaget eller ogiltigt.");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
