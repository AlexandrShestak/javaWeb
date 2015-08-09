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
<%@ taglib prefix="helloTag" tagdir="/WEB-INF/tags" %>
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
<jsp:include page="../language.jsp"/>
<helloTag:myHelloTag />
<jsp:include page="../menu.jsp"/>
<table class="user">
  <c:forEach items="${users}" var="user">

    <tr>
      <td><c:out value="${user.login}" /></td>
      <td><c:out value="${user.password}" /></td>
      <td><c:out value="${user.email}" /></td>

      <td>
        <form action="/user" method="get">
          <input type="hidden" name="action" value="edit">
          <input type="hidden" name="username" value="${user.login}"/>
          <button type="submit"<%-- name="action" value="edit"--%>><fmt:message key="edit"/> </button>
       </form>
      </td>

      <td>
        <form action="/user" method="get">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="username" value="${user.login}"/>
          <button type="submit" <%--name="action" value="delete"--%>><fmt:message key="delete"/> </button>
       </form>
      </td>
    </tr>
  </c:forEach>
<tr>
  <td>
    <form action="/user" method="get">
      <input type="hidden" name="action" value="add">
      <button type="submit"><fmt:message key="add"/> </button>
    </form>
  </td>
</tr>
</table>

</body>
</html>
