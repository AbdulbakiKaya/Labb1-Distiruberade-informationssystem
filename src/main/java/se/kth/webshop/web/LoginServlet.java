package se.kth.webshop.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password"); // TODO: validering om ni vill

        boolean ok = username != null && !username.isBlank();
        if (!ok) {
            request.setAttribute("error", "Felaktiga uppgifter");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("userName", username);

        String from = request.getParameter("from");
        if (from == null || from.isBlank()) {
            from = request.getContextPath() + "/products";
        } else if (!from.startsWith(request.getContextPath())) {
            from = request.getContextPath() + "/products";
        }

        response.sendRedirect(from);
    }
}