<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>
<html>
<header>

</header>
<body>
<form action="/controller" method="post">
    <table class="login">
        <tr>
            <td>Enter login:</td>
            <td><input type="text" name="login"> </td>
        </tr>

        <tr>
            <td>Enter password:</td>
            <td><input type="text" name="password"> </td>
        </tr>
        <tr>
            <td>
                <input type="submit" name="action" value="login">
            </td>
        </tr>
    </table>


    ${errorMessage}
    <c:out value="${errorMessage}"></c:out>

</form>

</body>
</html>
