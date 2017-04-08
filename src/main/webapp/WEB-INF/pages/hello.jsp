<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 19.7.15
  Time: 15.02
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages/hello"/>
<%@ page isELIgnored="false" %>
<html>
<head>
  <link href="${pageContext.servletContext.contextPath}/bootstrap-3.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
  <title></title>
</head>
<body>
<div class="container">
<form action="/logout" method="post">
  <table width="100%">
    <tr>
      <td>
        <h4><fmt:message key="welcome"/> ${sessionScope.login}</h4>
      </td>
      <td>
        <button type="submit" class="btn" name="action" value="logout"><fmt:message key="out"/> </button>
      </td>
    </tr>
  </table>
</form>
</div>
</body>
</html>
