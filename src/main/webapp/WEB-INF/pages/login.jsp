

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%--
<%@ page language="java" contentType="text/html; charset=Cp1251" pageEncoding="Cp1251"%>
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<%--
<jsp:include page="/WEB-INF/pages/navigation.jsp"/>
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
    <link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="../../styles/signin.css" rel="stylesheet">
</header>
<body>
<div class="container">
<jsp:include page="/WEB-INF/pages/navigation.jsp"/>
<%-- <jsp:include page="pages/hello.jsp"/login>  --%>


<form class="form-signin" action="/j_spring_security_check" method="post">
    <table class="table">
        <tr>

            <td> <spring:message code="enterLogin"/></td>
            <td><input  id="inputLogin" type="text" name="username" class="form-control" > </td>
        </tr>

        <tr>
            <td> <spring:message code="enterPassword"/>  </td>
            <td><input type="password" name="password" id="inputPassword" class="form-control"  required> </td>
        </tr>
        <c:if test="${not empty logoutMessage}">
            <tr>
                <td colspan="2">

                    <div class="alert alert-warning" role="alert">${logoutMessage}</div>
                </td>
            </tr>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <tr>
                <td colspan="2">

                    <div class="alert alert-warning" role="alert"> <spring:message code="incorrectData"/></div>
                </td>
            </tr>
        </c:if>
        <tr>
            <td colspan="2" align="center">
                <input type="hidden" name="${_csrf.parameterName}"
                       value="${_csrf.token}" />
                <button type="submit" name="action"  class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="login"/></button>
            </td>
        </tr>
    </table>
</form>

<div class="row">
    <div class="col-md-4 col-md-offset-4">
        <form action="registration">
            <table class="table">
                <tr>
                    <td><spring:message code="registr"/></td>
                    <td>
                        <button type="submit" name="action" value="registration" class="btn btn-warning"><spring:message code="registration"/></button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
</div>
</body>
</html>
