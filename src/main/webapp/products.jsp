<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="se.kth.webshop.model.Product" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="sv">
<head><meta charset="UTF-8"><title>Produkter</title></head>
<body>
<%
  String user = (String) session.getAttribute("USER");
%>
<p>
  <a href="<%= request.getContextPath() %>/">Start</a> |
  <a href="<%= request.getContextPath() %>/cart">Kundkorg</a> |
  <% if (user == null) { %>
     <a href="<%= request.getContextPath() %>/login">Logga in</a>
  <% } else { %>
     Inloggad som <strong><%= user %></strong>
  <% } %>
</p>

<h1>Produktlista</h1>
<ul>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    for(Product p : products) {
%>
    <li>
      <%= p.getName() %> - <%= p.getPrice() %> kr
      <form method="post" action="<%= request.getContextPath() %>/cart" style="display:inline;">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="id" value="<%= p.getId() %>">
        <button type="submit">Lägg i korg</button>
      </form>
    </li>
<%
    }
%>
</ul>
</body>
</html>