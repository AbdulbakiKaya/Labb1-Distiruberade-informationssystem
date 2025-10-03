<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="sv">
<head>
  <meta charset="UTF-8">
  <title>Webbshop – Start</title>
</head>
<body>
<h1>Välkommen till Webbshoppen</h1>
<p>Detta är startsidan. Snart lägger vi till produkter och kundkorg.</p>
<p><a href="<%= request.getContextPath() %>/products">Visa produkter</a></p>
<c:choose>
    <c:when test="${not empty sessionScope.user}">
      <p>Inloggad som: <strong>${sessionScope.user}</strong></p>
      <p><a href="logout">Logga ut</a></p>
    </c:when>
    <c:otherwise>
      <p><a href="login.jsp">Logga in</a></p>
    </c:otherwise>
  </c:choose>
</body>
</html>