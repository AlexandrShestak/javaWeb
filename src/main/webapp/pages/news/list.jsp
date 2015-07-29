<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 22.7.15
  Time: 19.31
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

<table class="search">
  <tr>
    <td>
      <form action="/news" method="get">
        <input type="hidden" name="action" value="search">
        <input type="text" name="tag">
        <button type="submit"><fmt:message key="search"/></button>
      </form>
    </td>
  </tr>
</table>
<table class="news">
  <c:forEach items="${news}" var="news">

    <tr>
      <td colspan="5">
        <textarea class="news" name="newsText" readonly="readonly" ><c:out value="${news.newsText}"/></textarea>
      </td>
    </tr>
    <tr>
      <td align="left"><c:out value="${news.creationDate}" /></td>
      <td align="right"><c:out value="${news.creatorUsername}" /></td>
      <c:if test="${news.creatorUsername eq sessionScope.login}">
        <td>
          <form action="/news" method="get">
            <input type="hidden" name="action" value="edit">
            <input type="hidden" name="newsId" value="${news.newsId}"/>
            <button type="submit"><fmt:message key="edit"/> </button>
          </form>
        </td>
      </c:if>
      <c:if test="${news.creatorUsername eq sessionScope.login}">
        <td>
          <form action="/news" method="get">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="newsId" value="${news.newsId}"/>
            <button type="submit" ><fmt:message key="delete"/> </button>
          </form>
        </td>

      </c:if>
      <td>
        <form action="/comments" method="get">
          <input type="hidden" name="newsId" value="${news.newsId}"/>
          <input type="hidden" name="action" value="getForm">
          <button type="submit" ><fmt:message key="comments"/> </button>
        </form>
      </td>
    </tr>
    <tr>
      <td colspan="5">
        <hr align="right" width="40%" size="3" color="#0000dd" />
      </td>
    </tr>
  </c:forEach>
  <tr>
    <td>
      <form action="/news" method="get">
        <input type="hidden" name="action" value="add">
        <button type="submit"><fmt:message key="add"/> </button>
      </form>
    </td>
  </tr>
</table>


</body>
</html>
