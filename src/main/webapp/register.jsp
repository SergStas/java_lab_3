<%--
  Created by IntelliJ IDEA.
  User: sergs
  Date: 03.10.2021
  Time: 19:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    Have account? <a href="login">Log in</a><br>

    <form method="post">
        <label>
            Login:
            <input type="text" placeholder="Min length - 3" name="login">
        </label><br>
        <label>
            Password:
            <input type="password" placeholder="Min length - 4" name="password">
        </label><br>
        <label>
            E-mail:
            <input type="text" placeholder="mail@example.com" name="email">
        </label><br>
        <input type="submit" value="Register">
    </form> <%

    if (request.getAttribute("error") != null) { %>
        <p style="color: darkred">${error}</p> <%
    } %>
</body>
</html>
