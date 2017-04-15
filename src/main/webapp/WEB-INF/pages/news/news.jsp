<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 24.7.15
  Time: 14.27
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="helloTag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />


<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle   basename="messages/news"/>

<%@ page isELIgnored="false" %>

<html>
<head>
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
  <div class="panel panel-default">
    <div class="panel-body">
      <p><c:out value="${news.newsText}"/> </p>
    </div>
    <div class="panel-footer">
      <p>
        <i class="glyphicon glyphicon-user"></i> by <c:out value="${news.creatorUsername}" />
        | <i class="glyphicon glyphicon-calendar"></i> <c:out value="${news.creationDate}" />
        | <i class="glyphicon glyphicon-tag"></i> Tags : <span class="label label-info"><c:forEach items="${newsTags}" var="tags">
        #${tags.tagName}
      </c:forEach></span>
        <%-- <c:forEach items="${news.tagSet}" var="tag">
           |<i class="glyphicon glyphicon-tag"></i> Tags : <span class="label label-info">${tag.tagName}</span>
         </c:forEach>--%>
      </p>
    </div>
  </div>
  <hr>
  <!-- Posted Comments -->
  <div class="panel panel-body">
    <div class="media " id="commentsTable">
      <c:forEach items="${comments}" var="comments">
        <!-- Comment -->
        <div class="panel panel-default">
          <div class="media">
            <div class="panel-body">
              <textarea class="comments" hidden="hidden" name="commentText"  ><c:out value="${comments.commentText}"/></textarea>
              <p name="commentText">
                <c:out value="${comments.commentText}"/>
              </p>
            </div>
            <div class="panel-footer">
              <div class="container">
                <div class="col-md-2">
                  <h4 class="media-heading"><c:out value="${comments.commentatorUsername}"/></h4>
                </div>
                <div class="col-md-2">
                  <small><c:out value="${comments.creationDate}"/></small>
                </div>
                <c:if test="${comments.commentatorUsername eq user.username}">
                  <div class="col-md-2">
                    <input type="hidden" name="action" value="edit">
                    <input type="hidden" name="commentId" value="${comments.commentId}"/>
                    <button class="editComment"> <spring:message code="edit"/></button>
                  </div>
                  <div class="col-md-2">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="commentId"  value="${comments.commentId}"/>
                    <button class="deleteComment"> <spring:message code="delete"/></button>
                  </div>
                </c:if>
              </div>
            </div>
          </div>
        </div>
      </c:forEach>
    </div>

    <div class="well">
      <h4>Leave a Comment:</h4>
      <%--<form role="form">--%>
      <div class="form-group">
        <textarea class="form-control" name="commentText" id="commentTextToAjax" rows="3"></textarea>
        <input type="hidden" name="newsId" id="newsIdToAjax" value="${news.newsId}"/>
        <input type="hidden" name="action" id="actionToAjax" value="add"/>
      </div>
      <button id="deleteAjax" style="display: none;"> <spring:message code="delete"/></button>
      <button id="editAjax"style="display: none;"> <spring:message code="edit"/></button>
      <button  id="addComment" class="btn btn-primary">Submit</button>
      <%-- </form>--%>
    </div>
  </div>
</div>


</body>
</html>
