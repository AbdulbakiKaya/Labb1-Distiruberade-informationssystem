<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="se.kth.webshop.model.Product" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="sv">
<head>
    <meta charset="UTF-8">
    <title>Produkter</title>
</head>
<body>
<h1>Produktlista</h1>
<ul>
<%
    List<Product> products = (List<Product>) request.getAttribute("products");
    for(Product p : products) {
%>
    <li><%= p.getName() %> - <%= p.getPrice() %> kr</li>
<%
    }
%>
</ul>
</body>
</html>