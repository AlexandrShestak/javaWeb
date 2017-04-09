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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/news"/>
<%@ page isELIgnored="false" %>


<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jquery-2.1.4.js"></script>
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jQueryScript.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>
  <link href="${pageContext.servletContext.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.servletContext.contextPath}/styles/main.css" rel="stylesheet">
  <title></title>
</head>
<body>
<jsp:include page="../nabigation.jsp"/>
<jsp:include page="../menu.jsp"/>
<div class="container">
  <form action="/news" method="post">
    <table class="table ">
      <tr>
        <td>
          <spring:message code="messageText"/>
        </td>
        </tr>
      <tr>
      <tr>
        <td>
          <textarea class="form-control" name="newsText"></textarea>
        </td>
      </tr>
      <tr>
        <td>
          <input type="hidden" name="action" value="add">
          <input type="hidden" name="tags" id="tagsToAddWithNews">
          <button type="submit"> <spring:message code="addNews"/></button>
        </td>
      </tr>
    </table>
  </form>
  <table class="table">
    <tr>
      <td>
        <spring:message code="addTag"/>
      </td>
      <td>
        <input type="hidden" name="newsId" value="${newsId}" id="newsIdToAddTag">
        <input type="text" id="addTagName">
      </td>
      <td>
        <button id="addTag" > <spring:message code="add"/> </button>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
