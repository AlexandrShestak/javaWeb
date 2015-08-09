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
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jquery-2.1.4.js"></script>
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jQueryScript.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>
  <link href="bootstrap-3.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.servletContext.contextPath}/styles/main.css" rel="stylesheet">

  <title></title>
</head>
<body>
<jsp:include page="../language.jsp"/>

<form class="form-horizontal col-md-6 col-md-offset-4" action="/registration" method="POST">
  <fieldset>
    <div id="legend">
      <legend class=""><fmt:message key="registration"/> </legend>
    </div>
    <div class="control-group">
      <!-- Username -->
      <label class="control-label"  for="username"><fmt:message key="login"/></label>
      <div class="controls">
        <input type="text" id="username" name="login"  class="input-xlarge">
        <p class="help-block">Username can contain any letters or numbers, without spaces</p>
      </div>
    </div>

    <div class="control-group">
      <!-- E-mail -->
      <label class="control-label" for="email"><fmt:message key="email"/> </label>
      <div class="controls">
        <input type="email" id="email" name="email" class="input-xlarge">
        <p class="help-block">Please provide your E-mail</p>
      </div>
    </div>

    <div class="control-group">
      <!-- Password-->
      <label class="control-label" for="password"><fmt:message key="userPassword"/></label>
      <div class="controls">
        <input type="hidden" name="action" value="registration">
        <input type="password" id="password" name="password" placeholder="" class="input-xlarge">
        <p class="help-block">Password should be at least 4 characters</p>
      </div>
    </div>

    <c:if test="${not empty errorMessage}">
      <div class="control-group">
       <div class="alert alert-warning" role="alert">${errorMessage}</div>
      </div>
    </c:if>

    <div class="control-group">
      <!-- Button -->
      <div class="controls">
        <button class="btn btn-success"><fmt:message key="registration"/> </button>
      </div>
    </div>
  </fieldset>
</form>
</body>
</html>
