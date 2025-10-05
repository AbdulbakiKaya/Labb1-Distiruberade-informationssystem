<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="sv">
<head>
  <meta charset="UTF-8">
  <title>Webshop – Start</title>
</head>
<body>
<h1>Välkommen till Webshop</h1>

<c:choose>
  <c:when test="${empty sessionScope.userName}">
    <p>Du är inte inloggad.</p>
    <p><a href="${pageContext.request.contextPath}/login.jsp">Logga in</a></p>
  </c:when>
  <c:otherwise>
    <p>Inloggad som <strong>${sessionScope.userName}</strong></p>
    <p>
      <a href="${pageContext.request.contextPath}/products">Produkter</a> |
      <a href="${pageContext.request.contextPath}/cart">Kundvagn</a> |
      <a href="${pageContext.request.contextPath}/logout">Logga ut</a>
    </p>
  </c:otherwise>
</c:choose>

</body>
</html>