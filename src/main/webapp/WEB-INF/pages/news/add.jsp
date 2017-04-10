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
<jsp:include page="../navigation.jsp"/>
<div class="page-header text-center">
  <h2>
    <spring:message code="messageText"/>
  </h2>
</div>
<div class="container">
  <form action="/news" method="post">
    <table class="table ">
      <tr>
        <td>
          <div class="form-group">
            <textarea class="form-control" rows="7" name="newsText"></textarea>
          </div>
        </td>
      </tr>
      <tr>
        <td>
          <div class="form-group">
            <div class="col-xs-2">
              <label><spring:message code="addTag"/></label>
            </div>
            <div class="col-xs-2">
              <input type="hidden" name="newsId" value="${newsId}" id="newsIdToAddTag">
              <input type="text" id="addTagName">
            </div>
            <div class="col-xs-1">
              <button id="addTag" type="button"> <spring:message code="add"/> </button>
            </div>
            <div class="col-md-offset-5 col-xs-2">
              <input type="hidden" name="action" value="add">
              <input type="hidden" name="tags" id="tagsToAddWithNews">
              <button type="submit"> <spring:message code="addNews"/></button>
            </div>
          </div>
        </td>
      </tr>
    </table>
  </form>
</div>
</body>
</html>
