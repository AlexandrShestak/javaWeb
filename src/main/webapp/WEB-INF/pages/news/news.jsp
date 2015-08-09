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
<helloTag:myHelloTag />
<jsp:include page="../menu.jsp"/>
<div class="container col-md-8 col-md-offset-2">

  <div class="row">

    <!-- Blog Post Content Column -->
    <div class="col-lg-8">
      <!-- Blog Post -->
      <!-- Author -->
      <p class="lead">
        by <c:out value="${news.creatorUsername}"/>
      </p>
      <!-- Date/Time -->
      <p><span class="glyphicon glyphicon-time"></span><c:out value="${news.creationDate}"/></p>
      <!-- Post Content -->
       <p><c:out value="${news.newsText}"/> </p>
      <p>
        <i class="glyphicon glyphicon-tag"></i>
        <c:forEach items="${newsTags}" var="tags">
          #${tags.tagName}
         </c:forEach>
        </span>
      </p


      <!-- Blog Comments -->

      <!-- Comments Form -->
      <%--<div class="well">
        <h4>Leave a Comment:</h4>
        <form role="form">
          <div class="form-group">
            <textarea class="form-control" rows="3"></textarea>
            <input type="hidden" name="newsId" id="newsIdToAjax" value="${news.newsId}"/>
            <input type="hidden" name="action" id="actionToAjax" value="add"/>
          </div>
          <button type="submit" class="btn btn-primary">Submit</button>
        </form>
      </div>--%>

      <hr>

      <!-- Posted Comments -->
<div class="media" id="commentsTable">
<c:forEach items="${comments}" var="comments">
      <!-- Comment -->
      <div class="media">

        <div class="media-body">
          <h4 class="media-heading"><c:out value="${comments.commentatorUsername}"/>
            <small><c:out value="${comments.creationDate}"/></small>
            <c:if test="${comments.commentatorUsername eq sessionScope.login}">
              <td>
                  <%--<form action="/comments" method="get">--%>
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="commentId" value="${comments.commentId}"/>
                <button class="editComment"><fmt:message key="edit"/> </button>
                  <%--  </form>--%>
              </td>
              <td>
                  <%--<form action="/comments" method="get">--%>
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="commentId"  value="${comments.commentId}"/>
                <button class="deleteComment"><fmt:message key="delete"/></button>
                  <%--</form>--%>
              </td>
            </c:if>
          </h4>
          <textarea class="comments" hidden="hidden" name="commentText"  ><c:out value="${comments.commentText}"/></textarea>
          <p name="commentText">
             <c:out value="${comments.commentText}"/>
          </p>
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
      <button id="deleteAjax" style="display: none;"><fmt:message key="delete"/></button>
      <button id="editAjax"style="display: none;"><fmt:message key="edit"/></button>
      <button  id="addComment" class="btn btn-primary">Submit</button>
   <%-- </form>--%>
  </div>
</div>
    </div>
  </div>



<%--<table class="news"  id="commentsTable">
  <tr>
    <td colspan="5">
      <textarea class="news" name="newsText" readonly="readonly" ><c:out value="${news.newsText}"/></textarea>
    </td>
  </tr>
  <tr>
    <td colspan="5" align="center" bgcolor="aqua">
      <c:forEach items="${newsTags}" var="tags">
        #${tags.tagName}
      </c:forEach>
    </td>
  </tr>
  <tr>
    <td colspan="3" align="left"><c:out value="${news.creationDate}" /></td>
    <td colspan="2" align="right"><c:out value="${news.creatorUsername}" /></td>
  </tr>

  <c:forEach items="${comments}" var="comments">

    <tr>
      <td colspan="5">
        <table width="80%" >

          <tr>
            <td colspan="5">
              <textarea class="comments"  name="commentText" readonly="readonly" ><c:out value="${comments.commentText}"/></textarea>
            </td>
          </tr>
          <tr>
            <td align="left"><c:out value="${comments.creationDate}" /></td>
            <td align="right"><c:out value="${comments.commentatorUsername}" /></td>
            <c:if test="${comments.commentatorUsername eq sessionScope.login}">
              <td>
                  &lt;%&ndash;<form action="/comments" method="get">&ndash;%&gt;
                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="commentId" value="${comments.commentId}"/>
                <button class="editComment"><fmt:message key="edit"/> </button>
                  &lt;%&ndash;  </form>&ndash;%&gt;
              </td>
              <td>
                  &lt;%&ndash;<form action="/comments" method="get">&ndash;%&gt;
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="commentId"  value="${comments.commentId}"/>
                <button class="deleteComment"><fmt:message key="delete"/></button>
                  &lt;%&ndash;</form>&ndash;%&gt;
              </td>
            </c:if>
          </tr>
          <tr>
            <td colspan="5">
              <hr align="right" width="40%" size="3" color="#0000dd" />
            </td>
          </tr>
        </table>
      </td>
    </tr>

  </c:forEach>
</table>--%>


<%--<table class="news">
  <tr>
    <td>
      <textarea class="comments"  name="commentText" id="commentTextToAjax"></textarea>
    </td>
  </tr>
  <tr>
    <td>
      <input type="hidden" name="newsId" id="newsIdToAjax" value="${news.newsId}"/>
      <input type="hidden" name="action" id="actionToAjax" value="add"/>

      <button id="deleteAjax" style="display: none;"><fmt:message key="delete"/></button>
      <button id="editAjax"style="display: none;"><fmt:message key="edit"/></button>
      <button id="addComment"><fmt:message key="addComment"/> </button>
    </td>
  </tr>
</table>--%>
</body>
</html>
