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
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jquery-2.1.4.js"></script>
  <script type="text/javascript" src="${pageContext.servletContext.contextPath}/javascript/jQueryScript.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/styles/tables.css"/>
  <title></title>
</head>
<body>

<jsp:include page="../language.jsp"/>
<jsp:include page="../menu.jsp"/>



    <table class="news">
      <form action="/news" method="post">
        <tr>
          <td>
            <fmt:message key="messageText"/>
          </td>
          <td>
            <textarea name="newsText">${news.newsText}</textarea>
          </td>
        </tr>
        <tr>
          <td>
            <input type="hidden" name="newsId" value="${news.newsId}"/>
            <input type="hidden" name="action" value="edit">
            <input type="submit">
          </td>
        </tr>
      </form>
      <tr>
        <td>
          ${news.tagsString}
        </td>
      </tr>
      <tr>
        <td>
          <form method="post" action="/tags">
            <input type="hidden" name="newsId" value="${news.newsId}"/>
            <input type="hidden" name="action" value="add">
            <button type="submit"><fmt:message key="addTag"/></button>
            <input type="text" name="tagName">
          </form>
        </td>
      </tr>

      <tr>
        <td>
          <form method="post" action="/tags">
            <input type="hidden" name="newsId" value="${news.newsId}"/>
            <input type="hidden" name="action" value="delete">
            <button type="submit"><fmt:message key="deleteTag"/></button>
            <input type="text" name="tagName">
          </form>
        </td>
      </tr>

    </table>



</body>
</html>
