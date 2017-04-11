<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="helloTag" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/users"/>
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
  <table class="table table-bordered">
    <thead>
    <tr>
      <th class="text-center">
        <spring:message code="login"/>
      </th>
      <th class="text-center">
        <spring:message code="password"/>
      </th>
      <th class="text-center">
        <spring:message code="email"/>
      </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
      <tr>
        <td class="text-center"><c:out value="${user.username}" /></td>
        <td class="text-center"><c:out value="${user.password}" /></td>
        <td class="text-center"><c:out value="${user.email}" /></td>

        <td class="text-center">
          <form action="/user" method="get">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="username" value="${user.username}"/>
            <button type="submit"<%-- name="action" value="edit"--%>><spring:message code="edit"/> </button>
          </form>
        </td>

        <td class="text-center">
          <form action="/user" method="get">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="username" value="${user.username}"/>
            <button type="submit"><spring:message code="delete"/> </button>
          </form>
        </td>
      </tr>

    </c:forEach>
    </tbody>
  </table>
  <div class="col-md-offset-5 col-xs-2">
    <form action="/user" method="get">
      <input type="hidden" name="action" value="add">
      <button class="btn btn-warning" type="submit"><spring:message code="add"/> </button>
    </form>
  </div>

</div>
</body>
</html>
