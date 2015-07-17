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
<html>
<head>
    <title></title>
</head>
<body>
<table>
  <c:forEach items="${users}" var="user">
    <tr>
      <td><c:out value="${user.login}" /></td>
      <td><c:out value="${user.password}" /></td>
      <td><c:out value="${user.email}" /></td>
      <td><a href="UserController?action=edit&userId=">">Update</a></td>
      <td><a href="UserController?action=delete&userId=">">Delete</a></td>
    </tr>
  </c:forEach>
</table>

</body>
</html>
