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
<jsp:include page="../hello.jsp"/>
<jsp:include page="../menu.jsp"/>

<table class="news"  id="commentsTable">
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
              <textarea class="comments" name="commentText" readonly="readonly" ><c:out value="${comments.commentText}"/></textarea>
            </td>
          </tr>
          <tr>
            <td align="left"><c:out value="${comments.creationDate}" /></td>
            <td align="right"><c:out value="${comments.commentatorUsername}" /></td>
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
</table>


<table class="news">
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
</table>
</body>
</html>
