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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page isELIgnored="false" %>

<%@page buffer="50kb" autoFlush="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/news"/>
<sec:authentication var="user" property="principal" />

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

<helloTag:myHelloTag />
<jsp:include page="../menu.jsp"/>


<br>
<br>
<br>
<div class="col-md 1 col-md-offset-5">
<h2>          <spring:message code="problems"/></h2>
</div>
<br>
<div class="container">
<c:forEach items="${news}" var="news">
  <div class="panel panel-default col-md-8 col-md-offset-2">
    <div class="span8">
      <div class="panel-body">
        <div class="span6">
          <p>
            <a href="/comments?action=getForm&newsId=${news.newsId}"></a>
            <c:out value="${news.newsText}"/>
          </p>
        </div>
      </div>

      <div class="panel-footer">
        <div class="span8">
          <p></p>
          <p>
            <i class="glyphicon glyphicon-user"></i> by <c:out value="${news.creatorUsername}" />
            | <i class="glyphicon glyphicon-calendar"></i> <c:out value="${news.creationDate}" />
            | <i class="glyphicon glyphicon-comment"></i> <a href="/comments?action=getForm&newsId=${news.newsId}"> <spring:message code="comments"/></a>
          <c:if test="${news.creatorUsername eq user.username}">
            | <i class="glyphicon glyphicon-edit"></i><a href="/news?action=edit&newsId=${news.newsId}"> <spring:message code="edit"/></a>
            | <i class="glyphicon glyphicon-trash"></i><a  href="/news?action=delete&newsId=${news.newsId}"> <spring:message code="delete"/></a>
          </c:if>
            |<i class="glyphicon glyphicon-tag"></i> Tags : <span class="label label-info">${news.tagsString}</span>
           <%-- <c:forEach items="${news.tagSet}" var="tag">
              |<i class="glyphicon glyphicon-tag"></i> Tags : <span class="label label-info">${tag.tagName}</span>
            </c:forEach>--%>
          </p>
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
        <button class="btn btn-warning" type="submit"> <spring:message code="addNews"/> </button>
      </form>
    </div>
  </div>
  </div>
    </div>
</div>



</body>
</html>
