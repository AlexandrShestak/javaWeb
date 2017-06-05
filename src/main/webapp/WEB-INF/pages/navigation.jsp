<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />
<html>
<head>
    <title></title>
</head>
<body>
<div class="container">
    <div class="navbar">
        <sec:authorize access="isAuthenticated()">
            <div class="nav navbar-nav navbar-left">
                <ul class="nav navbar-nav">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li>
                            <form  method="get" action="/user"  class="navbar-form" role="menu">
                                <input type="submit" value="Users"  class="form-control" >
                            </form>
                        </li>
                    </sec:authorize>

                    <li>
                        <form action="/news" method="get" class="navbar-form" role="menu">
                            <button type="submit" class="btn btn-default">  <spring:message code="toNews"/> </button>
                        </form>
                    </li>
                    <li>
                        <form action="/newsSearch" method="get" class="navbar-form" role="search">
                            <spring:message code="tag"/>
                            <div class="form-group">
                                <input type="text" class="form-control" name="tag">
                            </div>
                            <spring:message code="user"/>
                            <div class="form-group">
                                <input type="text" class="form-control" name="username">
                            </div>
                            <button type="submit" class="btn btn-default"><spring:message code="search"/></button>
                        </form>
                    </li>
                </ul>
            </div>
        </sec:authorize>
        <div class="navbar-collapse collapse navbar-right">
            <ul class="nav navbar-nav">
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
</div>
</body>
</html>
