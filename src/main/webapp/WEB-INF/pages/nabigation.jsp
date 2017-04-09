<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />
<html>
<head>
    <title></title>
</head>
<body>
<div id="navbar-default">
    <div class="nav navbar-nav navbar-right">
        <ul class="nav nav-tabs">
            <sec:authorize access="isAuthenticated()">
                <li>
                    <a href="">${user.username}</a>
                </li>
                <li>
                    <a href="/logout"> <spring:message code="logout"/> </a>
                </li>
            </sec:authorize>
            <li>
                <a href="/language?language=en">EN</a>
            </li>
            <li>
                <a href="/language?language=ru">RU</a>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
