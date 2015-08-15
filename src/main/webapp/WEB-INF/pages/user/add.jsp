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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

  <form action="/user" method="post">

    <table class="user">
      <tr>
        <td>
          <spring:message code="login"/>
        </td>
        <td>
          <input type="text" name="username">
        </td>
      </tr>
      <tr>
        <td>
          <spring:message code="userPassword"/>
        </td>
        <td>
          <input type="text" name="password" >
        </td>
      </tr>
      <tr>
        <td>
          <spring:message code="email"/>
        </td>
        <td>
          <input type="text" name="email"  >
        </td>
      </tr>
      <tr>
        <td>
          <input type="hidden" name="action" value="add">
          <input type="submit">
        </td>
      </tr>

    </table>
  </form>
</body>
</html>
