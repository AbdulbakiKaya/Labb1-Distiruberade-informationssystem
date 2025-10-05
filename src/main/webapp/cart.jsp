<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="se.kth.webshop.model.Cart" %>
<%@ page import="se.kth.webshop.model.CartItem" %>

<!DOCTYPE html>
<html lang="sv">
<head><meta charset="UTF-8"><title>Kundkorg</title></head>
<body>
<%
  String user = (String) session.getAttribute("USER");
  Cart cart = (Cart) request.getAttribute("cart");
%>

<h1>Kundkorg</h1>
<p>Användare: <%= (user != null ? user : "Ej inloggad") %></p>
<p>
  <a href="<%= request.getContextPath() %>/products">Fortsätt handla</a>
  <% if (user == null) { %> | <a href="<%= request.getContextPath() %>/login">Logga in</a> <% } %>
</p>

<% if (cart == null || cart.isEmpty()) { %>
  <p>Din kundkorg är tom.</p>
<% } else { %>
  <table border="1" cellpadding="6">
    <tr><th>Produkt</th><th>Antal</th><th>Rad-summa</th><th></th></tr>
    <% for (CartItem ci : cart.getItems()) { %>
      <tr>
        <td><%= ci.getProduct().getName() %></td>
        <td><%= ci.getQuantity() %></td>
        <td><%= String.format("%.2f", ci.getLineTotal()) %> kr</td>
        <td>
          <form method="post" action="<%= request.getContextPath() %>/cart" style="display:inline;">
            <input type="hidden" name="action" value="removeAll">
            <input type="hidden" name="id" value="<%= ci.getProduct().getId() %>">
            <button type="submit">Ta bort</button>
          </form>
        </td>
      </tr>
    <% } %>
    <tr>
      <td colspan="2" style="text-align:right;"><strong>Totalt:</strong></td>
      <td colspan="2"><strong><%= String.format("%.2f", cart.getTotal()) %> kr</strong></td>
    </tr>
  </table>

  <% if (user != null) { %>
    <p><em>(Senare: “Till kassa” → order/transaction)</em></p>
    <form method="post" action="<%= request.getContextPath() %>/logout">
      <button type="submit">Logga ut</button>
    </form>
  <% } %>
<% } %>
</body>
</html>
