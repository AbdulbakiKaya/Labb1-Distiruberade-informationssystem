<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="sv">
<head>
  <meta charset="UTF-8">
  <title>Logga in</title>
</head>
<body>
<h1>Logga in</h1>

<c:if test="${not empty requestScope.error}">
  <p style="color:red">${requestScope.error}</p>
</c:if>

<form method="post" action="${pageContext.request.contextPath}/login">
  <input type="hidden" name="from" value="${param.from}" />
  <div>
    <label>Användarnamn:
      <input type="text" name="username" required>
    </label>
  </div>
  <div>
    <label>Lösenord:
      <input type="password" name="password" required>
    </label>
  </div>
  <button type="submit">Logga in</button>
</form>

<p><a href="${pageContext.request.contextPath}/">Tillbaka</a></p>
</body>
</html>
<p><a href="${pageContext.request.contextPath}/register">Registrera nytt konto</a></p>