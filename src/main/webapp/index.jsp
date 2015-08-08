

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%--
<%@ page language="java" contentType="text/html; charset=Cp1251" pageEncoding="Cp1251"%>
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<%--
<jsp:include page="/WEB-INF/pages/language.jsp"/>
--%>


<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/index"/>
<%@ page isELIgnored="false" %>

<html lang="${sessionScope.language}">
<header>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>
  <link href="bootstrap-3.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="styles/signin.css" rel="stylesheet">
</header>
<body>

<jsp:include page="/WEB-INF/pages/language.jsp"/>
<%-- <jsp:include page="pages/hello.jsp"/>  --%>


<form class="form-signin" action="/login" method="post">
  <table class="table">
    <tr>

      <td> <fmt:message key="enterLogin"/></td>
      <td><input  id="inputLogin" type="text" name="login" class="form-control" > </td>
    </tr>

    <tr>
      <td><fmt:message key="enterPassword"/> </td>
      <td><input type="password" name="password" id="inputPassword" class="form-control"  required> </td>
    </tr>
    <tr>
      <td colspan="2"><c:if test="${not empty errorMessage}">
        <div class="alert alert-warning" role="alert"><fmt:message key="incorrectData"/></div>
      </td>
      </c:if>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <button type="submit" name="action"  class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="login"/> </button>
      </td>
    </tr>
  </table>

</form>
<div class="row">
  <div class="col-md-4 col-md-offset-4">
    <form action="registration">
      <table class="table">
        <tr>
          <td><fmt:message key="registr"/> </td>
          <td>
            <button type="submit" name="action" value="registration" class="btn btn-warning"><fmt:message key="registration"/> </button>
          </td>
        </tr>
      </table>
    </form>
  </div>
</div>
</body>
</html>
