<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 28.7.15
  Time: 16.20
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/news"/>

<%@ page isELIgnored="false" %>

<html>
<head>
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jquery-2.1.4.js"></script>
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jQueryScript.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>

  <title></title>
</head>
<body>
<div class="container">
<table class="tags">
  <tr>
    <td>
      <fmt:message key="addTag"/>
    </td>
    <td>
      <input type="hidden" name="newsId" value="${newsId}" id="newsIdToAddTag">
      <input type="text" id="addTagName">
    </td>
    <td>
      <button id="addTag" ><fmt:message key="add"/> </button>
    </td>
  </tr>
</table>
<form action="/news" method="get">
  <button type="submit"><fmt:message key="toNews"/> </button>
</form>
</div>
</body>
</html>
