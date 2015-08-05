<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 31.7.15
  Time: 12.42
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/news"/>

<%@ page isELIgnored="false" %>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>
  <title></title>
</head>
<body>
<table class="search">
  <tr>
    <c:if test="${sessionScope.login eq 'admin'}">
      <td>
        <form  method="get" action="/user">
          <input type="submit" value="Users">
        </form>
      </td>
    </c:if>
    <td>
      <form action="/news" method="get">
        <button type="submit"><fmt:message key="toNews"/> </button>
      </form>
    </td>
    <form action="/news" method="get">
      <input type="hidden" name="action" value="search">
      <td>
        <fmt:message key="tag"/>
      </td>
      <td>
      <input type="text" name="tag">
      </td>
      <td>
        <fmt:message key="user"/>
      </td>
      <td>
       <input type="text" name="username">
      </td>
      <td>
        <button type="submit"><fmt:message key="search"/></button>
      </td>
    </form>
  </tr>
</table>

</body>
</html>
