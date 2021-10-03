<%--
  Created by IntelliJ IDEA.
  User: sergs
  Date: 03.10.2021
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Log in</title>
</head>
<body>
    <a href="register">Register</a><br>

    <form method="post">
        <label>
            Login or e-mail:
            <input type="text" name="username">
        </label><br>
        <label>
            Password:
            <input type="password" name="password">
        </label><br>
        <input type="submit" value="Log in">
    </form><br> <%

    if (request.getAttribute("error") != null) { %>
        <p style="color: darkred">${error}</p> <%
    } %>
</body>
</html>
