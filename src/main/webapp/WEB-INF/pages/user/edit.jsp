<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/user"/>
<%@ page isELIgnored="false" %>

<html>
<head>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>
  <link href="${pageContext.servletContext.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.servletContext.contextPath}/styles/main.css" rel="stylesheet">
</head>
<body>
<div class="container">
<jsp:include page="../navigation.jsp"/>
<form action="/user" method="post">
    <table class="user">
      <tr>
        <td>
          <spring:message code="login"/>
        </td>
        <td>
          <input type="hidden" name="username" value="${user.username}">
          <input type="text" name="username" value="${user.username}" disabled="disabled">
        </td>
      </tr>
      <tr>
        <td>
          <spring:message code="password"/>
        </td>
        <td>
          <input type="text" name="password" value="${user.password}" >
        </td>
      </tr>
      <tr>
        <td>
          <spring:message code="email"/>
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
</div>
</body>
</html>
