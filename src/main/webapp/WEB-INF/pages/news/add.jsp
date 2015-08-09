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
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jquery-2.1.4.js"></script>
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jQueryScript.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>
  <link href="${pageContext.servletContext.contextPath}/bootstrap-3.3.5/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.servletContext.contextPath}/styles/main.css" rel="stylesheet">
  <title></title>
</head>
<body>

<jsp:include page="../language.jsp"/>
<div class="container col-md-8 col-md-offset-2">
  <form action="/news" method="post">
    <table class="table ">
      <tr>
        <td>
          <fmt:message key="messageText"/>
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
          <button type="submit"><fmt:message key="addNews"/> </button>
        </td>
      </tr>
    </table>
  </form>
  <table class="table">
    <tr>
      <td>
        <fmt:message key="addTag"/>
      </td>
      <td>
        <input type="hidden" name="newsId" value="${newsId}" id="newsIdToAddTag">
        <input type="text" id="addTagName">
      </td>
      <td>
        <button id="addTag" ><fmt:message key="add"/> </button>
      </td>
    </tr>
  </table>
</div>
</body>
</html>
