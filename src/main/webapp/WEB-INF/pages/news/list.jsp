<%--<%@page contentType="text/html" pageEncoding="UTF-8"%>--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
  <meta charset="utf-8">
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
<div class="container">
  <div class="page-header text-center">
    <h2>
      <spring:message code="problems"/>
    </h2>
  </div>
  <br>
  <form action="/fullTextSearch" method="post">
    <div class="panel panel-default col-md-10 col-md-offset-1">
      <div class="row">
        <div id="custom-search-input">
          <div class="input-group col-md-12">
            <spring:message code="search" var="searchPlaceholder"/>
            <input name="query" type="text" class="form-control input-lg" placeholder="${searchPlaceholder}.."/>
            <span class="input-group-btn">
                        <button class="btn btn-info btn-lg" type="submit">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </span>
          </div>
        </div>
      </div>
  </form>
</div>

<c:forEach items="${news}" var="news">
  <div class="panel panel-default col-md-10 col-md-offset-1">
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
              | <i class="glyphicon glyphicon-edit"></i><a href="/newsEdit?newsId=${news.newsId}"> <spring:message code="edit"/></a>
              | <i class="glyphicon glyphicon-trash"></i><a  href="/newsDelete?&newsId=${news.newsId}"> <spring:message code="delete"/></a>
            </c:if>
              <%-- |<i class="glyphicon glyphicon-tag"></i> Tags : <span class="label label-info">${news.tagsString}</span>
            --%>
            |<i class="glyphicon glyphicon-tag"></i> Tags : <c:forEach items="${news.tagSet}" var="tag"><span class="label label-info">#${tag.tagName}</span>  </c:forEach>
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
        <form action="/newsForm" method="get">
          <button class="btn btn-warning" type="submit"> <spring:message code="addNews"/> </button>
        </form>
      </div>
    </div>
  </div>
</div>
</div>
</body>
</html>
