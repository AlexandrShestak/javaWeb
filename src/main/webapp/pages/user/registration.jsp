<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 18.7.15
  Time: 13.52
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

<form action="/registration" method="post">

  <table class="user">
    <tr>
      <td>
        Логин:
      </td>
      <td>
        <input type="text" name="login">
      </td>
    </tr>
    <tr>
      <td>
        Пароль:
      </td>
      <td>
        <input type="text" name="password" >
      </td>
    </tr>
    <tr>
      <td>
        Электронная почта:
      </td>
      <td>
        <input type="text" name="email"  >
      </td>
    </tr>
    <tr>
      <td>
        ${errorMessage}
      </td>
    </tr>
    <tr>
      <td>
        <button type="submit" name="action" value="registration">Зарегестрировать</button>
      </td>
    </tr>

  </table>
</form>
</body>
</html>
