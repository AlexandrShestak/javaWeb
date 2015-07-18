<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 17.7.15
  Time: 17.25
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>

  <title></title>
</head>
<body>
<table class="user">
  <c:forEach items="${users}" var="user">
    <form action="/controller" method="get">
    <tr>
      <td><input hidden="hidden" name="login" value="${user.login}"/> <c:out value="${user.login}" /></td>
      <td><input hidden="hidden" name="password" value="${user.password}"><c:out value="${user.password}" /></td>
      <td><input hidden="hidden" name="email" value="${user.email}"><c:out value="${user.email}" /></td>
      <td><button type="submit" name="action" value="edit">Изменить</button></td>
      <td><button type="submit" name="action" value="delete">Удалить</button></td>
    </tr>
    </form>
  </c:forEach>
<tr>
  <td>
    <form action="/controller" method="get">
      <button type="submit" name="action" value="getUserForm">Добавить пользователя</button>
    </form>
  </td>
</tr>
</table>

</body>
</html>
