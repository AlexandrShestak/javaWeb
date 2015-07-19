<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>
<html>
<header>

</header>
<body>
<form action="/authorization" method="post">
    <table class="login" align="center" border="2">
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

</body>
</html>
