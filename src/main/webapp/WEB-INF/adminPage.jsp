<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<title>Admin Page</title>
<body>
    <h1>Welcome to the Admin Page <c:out value="${currentUser.username}"/></h1>

    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}">
        <input type="submit" value="Logout!">
    </form>
</body>
</html>