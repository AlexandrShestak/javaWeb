<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%--
<%@ page language="java" contentType="text/html; charset=Cp1251" pageEncoding="Cp1251"%>
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<%--
<jsp:include page="pages/language.jsp"/>
--%>


<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/index"/>
<%@ page isELIgnored="false" %>

<html lang="${sessionScope.language}">
<header>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>

</header>
<body>

<jsp:include page="pages/language.jsp"/>
<%-- <jsp:include page="pages/hello.jsp"/>  --%>


<table>

</table>

<form action="/login" method="post">
  <table class="login">
    <tr>
      <td><fmt:message key="enterLogin"/> </td>
      <td><input type="text" name="login"> </td>
    </tr>

    <tr>
      <td><fmt:message key="enterPassword"/> </td>
      <td><input type="text" name="password"> </td>
    </tr>
    <tr>
      <td colspan="2"><c:if test="${not empty errorMessage}">
        <fmt:message key="incorrectData"/></td>
      </c:if>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <button type="submit" name="action" value="login"><fmt:message key="login"/> </button>
      </td>
    </tr>
  </table>

</form>
<form action="pages/authorization/registration.jsp">
  <table class="login">
    <tr>
      <td><fmt:message key="registr"/> </td>
      <td>
        <button type="submit" name="action" value="registration"><fmt:message key="registration"/> </button>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
