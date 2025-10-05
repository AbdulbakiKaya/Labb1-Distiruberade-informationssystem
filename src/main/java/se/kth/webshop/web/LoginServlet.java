package se.kth.webshop.web;

import jakarta.servlet.ServletException;                 // ✅
import jakarta.servlet.annotation.WebServlet;            // ✅
import jakarta.servlet.http.HttpServlet;                 // ✅
import jakarta.servlet.http.HttpServletRequest;          // ✅
import jakarta.servlet.http.HttpServletResponse;         // ✅
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {          // ✅
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {          // ✅
        String username = req.getParameter("username");
        // enkel "auth"
        if (username != null && !username.isBlank()) {
            req.getSession().setAttribute("user", username); // 🔁 matcha JSP: ${sessionScope.user}
            resp.sendRedirect(req.getContextPath() + "/products");
        } else {
            req.setAttribute("error", "Ange användarnamn");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}