package se.kth.webshop.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        if (username != null && !username.isBlank()) {
            req.getSession().setAttribute("USER", username.trim());
            resp.sendRedirect(req.getContextPath() + "/products");
        } else {
            resp.sendRedirect(req.getContextPath() + "/login?error=1");
        }
    }
}
