package se.kth.webshop.web;
package se.kth.webshop.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false); // hämta om den finns
        if (session != null) {
            session.invalidate(); // logga ut
        }
        resp.sendRedirect("index.jsp"); // tillbaka till startsidan
    }

}
