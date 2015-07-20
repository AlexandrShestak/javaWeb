<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 17.7.15
  Time: 23.48
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>
<html>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>

  <title></title>
</head>
<body>
<jsp:include page="../hello.jsp"/>
<form action="/user" method="post">
    <table class="user">
      <tr>
        <td>
          Логин:
        </td>
        <td>
          <input type="hidden" name="login" value="${user.login}">
          <input type="text" name="login" value="${user.login}" disabled="disabled">
        </td>
      </tr>
      <tr>
        <td>
          Пароль:
        </td>
        <td>
          <input type="text" name="password" value="${user.password}" >
        </td>
      </tr>
      <tr>
        <td>
          Электронная почта:
        </td>
        <td>
          <input type="text" name="email" value="${user.email}" >
        </td>
      </tr>
      <tr>
        <td>
          <input type="submit" name="action" value="edit">
        </td>
      </tr>
    </table>
</form>
</body>
</html>
