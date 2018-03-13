<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login & Registration Page</title>
</head>
<body>
    <h1>Register</h1>
    <p><form:errors path="user.*"/></p>

    <form:form method="POST" action="/registration" modelAttribute="user">
        <p>
            <form:label path="username">Email:</form:label>
            <form:input path="username"/>
        </p>
        <p>
            <form:label path="first_name">First Name:</form:label>
            <form:input path="first_name"/>
        </p>
        <p>
            <form:label path="last_name">Last Name:</form:label>
            <form:input path="last_name"/>
        </p>
        <p>
            <form:label path="password">Password:</form:label>
            <form:password path="password"/>
        </p>
        <p>
            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
            <form:password path="passwordConfirmation"/>
        </p>
        <input type="submit" value="Register!"/>
    </form:form>


    <c:if test="${logoutMessage != null}">
        <c:out value="${logoutMessage}"/>
    </c:if>
    <h1>Login!</h1>
    <c:if test="${errorMessage != null}">
        <c:out value="${errorMessage}"/>
    </c:if>
    <form method="POST" action="/login">
        <p>
            <label for="username">Label</label>
            <input type="text" id="username" name="username"/>
        </p>
        <p>
            <label for="password">Password</label>
            <input type="password" id="password" name="password"/>
        </p>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="submit" value="Login!"/>
    </form>
</body>

</html>