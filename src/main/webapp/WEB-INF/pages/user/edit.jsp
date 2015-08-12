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
<jsp:include page="../language.jsp"/>
<jsp:include page="../hello.jsp"/>
<form action="/user" method="post">
    <table class="user">
      <tr>
        <td>
          <fmt:message key="login"/>
        </td>
        <td>
          <input type="hidden" name="login" value="${user.username}">
          <input type="text" name="login" value="${user.username}" disabled="disabled">
        </td>
      </tr>
      <tr>
        <td>
          <fmt:message key="userPassword"/>
        </td>
        <td>
          <input type="text" name="password" value="${user.password}" >
        </td>
      </tr>
      <tr>
        <td>
          <fmt:message key="email"/>
        </td>
        <td>
          <input type="text" name="email" value="${user.email}" >
        </td>
      </tr>
      <tr>
        <td>
          <input type="hidden" name="action" value="edit">
          <input type="submit">
        </td>
      </tr>
    </table>
</form>
</body>
</html>
