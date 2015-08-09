<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 22.7.15
  Time: 19.31
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="helloTag" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<%@page buffer="50kb" autoFlush="false" %>

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
<helloTag:myHelloTag />
<jsp:include page="../menu.jsp"/>
<div class="container">
<c:forEach items="${news}" var="news">
  <div class="row col-md-8 col-md-offset-2">
    <div class="span8">
      <div class="row">
        <div class="span6">
          <p>
            <c:out value="${news.newsText}"/>
          </p>
        </div>
      </div>
      <div class="row">
        <div class="span8">
          <p></p>
          <p>
            <i class="glyphicon glyphicon-user"></i> by <c:out value="${news.creatorUsername}" />
            | <i class="glyphicon glyphicon-calendar"></i> <c:out value="${news.creationDate}" />
            | <i class="glyphicon glyphicon-comment"></i> <a href="/comments?action=getForm&newsId=${news.newsId}"><fmt:message key="comments"/></a>
          <c:if test="${news.creatorUsername eq sessionScope.login}">
            | <i class="glyphicon glyphicon-edit"></i><a href="/news?action=edit&newsId=${news.newsId}"><fmt:message key="edit"/></a>
            | <i class="glyphicon glyphicon-trash"></i><a  href="/news?action=delete&newsId=${news.newsId}"><fmt:message key="delete"/></a>
          </c:if>
            |<i class="glyphicon glyphicon-tag"></i> Tags : <span class="label label-info">${news.tagsString}</span>
          </p>
        </div>
      </div>
      <div class="row">
        <div class="span8">
          <hr>
        </div>
      </div>
    </div>
    </div>


</c:forEach>
  <div class="row col-md-2 col-md-offset-5">
  <div class="span8">
  <div class="row">
    <div class="span8">
      <form action="/news" method="get">
        <input type="hidden" name="action" value="add">
        <button class="btn btn-warning" type="submit"><fmt:message key="addNews"/> </button>
      </form>
    </div>
  </div>
  </div>
    </div>
</div>



</body>
</html>
