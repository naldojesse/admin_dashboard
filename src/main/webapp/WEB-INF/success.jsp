<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <h1>Welcome <c:out value="${currentUser.first_name}"/></h1>

    <div>
        <p>First Name: <c:out value="${currentUser.first_name}"/></p>
        <p>Last Name: <c:out value="${currentUser.last_name}"/></p>
        <p>Email: <c:out value="${currentUser.username}"/></p>
        <p>Sign up date: <c:out value="${currentUser.createdAt}"/></p>
        <p>Last Sign in: <c:out value="${sessionScope.signInDate}"/> </p>
    </div>


    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Logout"/>
    </form>
</body>
</html>