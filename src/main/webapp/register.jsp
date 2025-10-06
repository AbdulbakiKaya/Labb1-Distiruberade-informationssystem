<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="sv">
<head><meta charset="UTF-8"><title>Registrera</title></head>
<body>
<h1>Skapa konto</h1>

<c:if test="${not empty error}">
  <p style="color:red">${error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/register" method="post">
  <label>Användarnamn:</label>
  <input type="text" name="username" required><br/>
  <label>Lösenord:</label>
  <input type="password" name="password" required><br/>
  <button type="submit">Registrera</button>
</form>

<p><a href="${pageContext.request.contextPath}/login.jsp">Tillbaka till login</a></p>
</body>
</html>
