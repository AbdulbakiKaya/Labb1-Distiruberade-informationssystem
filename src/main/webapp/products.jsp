<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="sv">
<head>
  <meta charset="UTF-8">
  <title>Produkter</title>
</head>
<body>
<h1>Produkter</h1>

<c:choose>
  <c:when test="${empty sessionScope.userName}">
    <p>Du måste vara inloggad för att se produkter.</p>
    <p><a href="${pageContext.request.contextPath}/login.jsp?from=${pageContext.request.contextPath}/products">Logga in</a></p>
  </c:when>
  <c:otherwise>
    <p>
      Inloggad som <strong>${sessionScope.userName}</strong> |
      <a href="${pageContext.request.contextPath}/cart">Kundvagn</a> |
      <a href="${pageContext.request.contextPath}/logout">Logga ut</a>
    </p>

    <c:if test="${empty requestScope.products}">
      <p>Inga produkter.</p>
    </c:if>

    <c:if test="${not empty requestScope.products}">
      <table border="1" cellpadding="6" cellspacing="0">
        <thead>
          <tr>
            <th>Produkt</th>
            <th>Pris</th>
            <th>Antal</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="p" items="${requestScope.products}">
            <tr>
              <td>${p.name}</td>
              <td>${p.priceSek} kr</td>
              <td>
                <form method="post" action="${pageContext.request.contextPath}/products" style="display:flex; gap:8px; align-items:center;">
                  <input type="hidden" name="action" value="add" />
                  <input type="hidden" name="productId" value="${p.id}" />
                  <input type="number" name="qty" value="1" min="1" style="width:70px;" />
                  <button type="submit">Lägg i kundvagn</button>
                </form>
              </td>
              <td></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </c:if>
  </c:otherwise>
</c:choose>

<p><a href="${pageContext.request.contextPath}/">Start</a></p>
</body>
</html>