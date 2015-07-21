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

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/user"/>
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
       <fmt:message key="login"/>
      </td>
      <td>
        <input type="text" name="login">
      </td>
    </tr>
    <tr>
      <td>
        <fmt:message key="userPassword"/>
      </td>
      <td>
        <input type="text" name="password" >
      </td>
    </tr>
    <tr>
      <td>
        <fmt:message key="email"/>
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
        <button type="submit" name="action" value="registration"><fmt:message key="registration"/></button>
      </td>
    </tr>

  </table>
</form>
</body>
</html>
