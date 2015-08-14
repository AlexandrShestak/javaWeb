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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/news"/>

<%@ page isELIgnored="false" %>
<html>
<head>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>
  <link href="${pageContext.servletContext.contextPath}/bootstrap-3.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.servletContext.contextPath}/styles/main.css" rel="stylesheet">
  <title></title>
</head>
<body>

<nav class="navbar navbar-default col-md-10 col-md-offset-1">
  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <c:if test="${sessionScope.login eq 'admin'}">
        <form  method="get" action="/user"  class="navbar-form navbar-left" role="menu">
          <input type="submit" value="Users"  class="form-control" >
        </form>
      </c:if>

      <form action="/news" method="get" class="navbar-form navbar-left" role="menu">
        <button type="submit" class="btn btn-default"><fmt:message key="toNews"/> </button>
      </form>

      <form action="/news" method="get" class="navbar-form navbar-left" role="search">
        <input type="hidden" name="action" value="search">
        <fmt:message key="tag"/>
        <div class="form-group">
          <input type="text" class="form-control" name="tag">
        </div>
        <fmt:message key="user"/>
        <div class="form-group">
          <input type="text" class="form-control" name="username">
        </div>
        <button type="submit" class="btn btn-default"><fmt:message key="search"/></button>
      </form>
    </div>
  </div>
</nav>

</body>
</html>
