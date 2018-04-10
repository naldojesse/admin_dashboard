<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<title>Admin Page</title>
<body>
    <h1>Welcome to the Admin Page <c:out value="${currentUser.username}"/></h1>

    <form id="logoutForm" method="POST" action="/login">
        <input type="hidden" name="logout">
        <input type="hidden" name="${_csrf.parameterName}">
        <input type="submit" value="Logout!">
    </form>

    <table>
        <th>Name</th>
        <th>Email</th>
        <th>Action</th>
        <c:forEach items="${users}" var="user">
            <c:if test = "${user.username != currentUser.username}">
            <tr>
                <td><c:out value="${user.first_name}"/> <c:out value="${user.last_name}"/></td>
                <td><c:out value="${user.username}"/></td>

                <c:choose>
                    <c:when test="${user.getRoles().get(0).name == 'ROLE_ADMIN' }">
                        <td>admin</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="<c:url value="/users/delete/${user.id}"/>">Delete</a>
                            <a href="<c:url value="/roles/addAdmin/"/>">Make Admin</a>
                        </td>
                    </c:otherwise>
                </c:choose>
            </tr>
            </c:if>
        </c:forEach>
        <td></td>
    </table>
</body>
</html>

<%--<th>Actions</th>--%>
<%--<c:forEach items="${songs}" var="song">--%>
    <%--<tr>--%>
        <%--<td><a href="<c:url value="/songs/${song.id}"/>"><c:out value="${song.title}"/></a></td>--%>
        <%--<td><c:out value="${song.rating}"/></td>--%>
        <%--<td><a href="<c:url value="/songs/delete/${song.id}"/>">Delete</a></td>--%>
    <%--</tr>--%>
<%--</c:forEach>--%>

<%--</table>--%>
