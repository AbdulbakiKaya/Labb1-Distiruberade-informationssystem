<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
  <body>
    <h1>Logga in</h1>

    <form action="login" method="post">
      <label>Användarnamn:</label>
      <input type="text" name="username" required /><br/>
      <label>Lösenord:</label>
      <input type="password" name="password" required /><br/>
      <button type="submit">Logga in</button>
    </form>

    <c:if test="${not empty error}">
      <p style="color:red">${error}</p>
    </c:if>

    <p><a href="index.jsp">Till startsidan</a></p>
  </body>
</html>
