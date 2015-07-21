<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 17.7.15
  Time: 17.25
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/users"/>
<%@ page isELIgnored="false" %>


<html>
<head>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>

  <title></title>
</head>
<body>
<jsp:include page="../hello.jsp"/>
<table class="user">
  <c:forEach items="${users}" var="user">
    <form action="/user" method="get">
    <tr>
      <td><input hidden="hidden" name="login" value="${user.login}"/> <c:out value="${user.login}" /></td>
      <td><input hidden="hidden" name="password" value="${user.password}"><c:out value="${user.password}" /></td>
      <td><input hidden="hidden" name="email" value="${user.email}"><c:out value="${user.email}" /></td>
      <td><button type="submit" name="action" value="edit"><fmt:message key="edit" /> </button></td>
      <td><button type="submit" name="action" value="delete"><fmt:message key="delete"/> </button></td>
    </tr>
    </form>
  </c:forEach>
<tr>
  <td>
    <form action="/user" method="get">
      <button type="submit" name="action" value="getUserForm"><fmt:message key="add"/> </button>
    </form>
  </td>
</tr>
</table>

</body>
</html>
