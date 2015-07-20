<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>
<html>
<header>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>

</header>
<body>
<form action="/login" method="post">
    <table class="login">
        <tr>
            <td>Введите логин:</td>
            <td><input type="text" name="login"> </td>
        </tr>

        <tr>
            <td>Введите пароль:</td>
            <td><input type="text" name="password"> </td>
        </tr>
        <tr>
            <td colspan="2"><c:out value="${errorMessage}"></c:out></td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button type="submit" name="action" value="login">Войти</button>
            </td>
        </tr>
    </table>

</form>
<form action="pages/user/registration.jsp">
    <table class="login">
        <tr>
            <td>Или зарегистрируйтесь:</td>
            <td>
                <button type="submit" name="action" value="registration">Регистрация</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
