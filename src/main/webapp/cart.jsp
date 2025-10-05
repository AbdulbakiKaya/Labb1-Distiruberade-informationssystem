<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="sv">
<head>
  <meta charset="UTF-8">
  <title>Kundvagn</title>
</head>
<body>
<h1>Kundvagn</h1>

<c:choose>
  <c:when test="${empty sessionScope.userName}">
    <p>Du måste vara inloggad för att se kundvagnen.</p>
    <p><a href="${pageContext.request.contextPath}/login.jsp?from=${pageContext.request.contextPath}/cart">Logga in</a></p>
  </c:when>
  <c:otherwise>
    <p>
      Inloggad som <strong>${sessionScope.userName}</strong> |
      <a href="${pageContext.request.contextPath}/products">Produkter</a> |
      <a href="${pageContext.request.contextPath}/logout">Logga ut</a>
    </p>

    <!-- Hämta kundvagn från session -->
    <c:set var="cart" value="${sessionScope.cart}" />

    <!-- Tom kundvagn -->
    <c:if test="${cart == null or empty cart.items}">
      <p>Din kundvagn är tom.</p>
    </c:if>

    <!-- Icke-tom kundvagn -->
    <c:if test="${cart != null and not empty cart.items}">
      <table border="1" cellpadding="6" cellspacing="0">
        <thead>
          <tr>
            <th>Produkt</th>
            <th>Antal</th>
            <th>Pris/st</th>
            <th>Radbelopp</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="item" items="${cart.items}">
            <tr>
              <td>${item.productName}</td>
              <td>${item.quantity}</td>
              <td>${item.unitPriceSek} kr</td>
              <td>${item.lineTotalSek} kr</td>
              <td>
                <form method="post" action="${pageContext.request.contextPath}/cart" style="display:inline;">
                  <input type="hidden" name="action" value="removeAll"/>
                  <input type="hidden" name="productId" value="${item.productId}"/>
                  <button type="submit">Ta bort</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          <tr>
            <td colspan="3" style="text-align:right;"><strong>Totalt:</strong></td>
            <td colspan="2"><strong>${cart.totalSek} kr</strong></td>
          </tr>
        </tbody>
      </table>

      <form method="post" action="${pageContext.request.contextPath}/cart" style="margin-top: 10px;">
        <input type="hidden" name="action" value="clear"/>
        <button type="submit">Töm kundvagn</button>
      </form>
    </c:if>
  </c:otherwise>
</c:choose>

<p><a href="${pageContext.request.contextPath}/">Start</a></p>
</body>
</html>