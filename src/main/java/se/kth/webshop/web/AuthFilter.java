package se.kth.webshop.web;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebFilter(urlPatterns = {"/products", "/cart"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("userName") != null);
        if (!loggedIn) {
            String from = request.getRequestURI();
            String redirect = request.getContextPath() + "/login.jsp?from=" +
                    URLEncoder.encode(from, StandardCharsets.UTF_8);
            response.sendRedirect(redirect);
            return;
        }

        chain.doFilter(req, res);
    }
}