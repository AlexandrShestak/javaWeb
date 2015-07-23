<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 22.7.15
  Time: 19.29
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/news"/>
<%@ page isELIgnored="false" %>


<html>
<head>
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jquery-2.1.4.js"></script>
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jQueryScript.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>
  <title></title>
</head>
<body>

<jsp:include page="../language.jsp"/>

<form action="/news" method="post">

  <table class="news">
    <tr>
      <td>
        <fmt:message key="messageText"/>
      </td>
      <td>
        <textarea name="newsText"></textarea>
      </td>
    </tr>
    <tr>
      <td>
        <input type="hidden" name="action" value="add">
        <input type="submit">
      </td>
    </tr>

  </table>
</form>

</body>
</html>
