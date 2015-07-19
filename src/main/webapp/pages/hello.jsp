<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 19.7.15
  Time: 15.02
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title></title>
</head>
<body>
<form action="/authorization" method="post">
  <table width="100%">
    <tr>
      <td>
        <h4>Здравствуйте , ${sessionScope.login}</h4>
      </td>
      <td>
        <button type="submit" name="action" value="logout">Выйти</button>
      </td>
    </tr>

  </table>

</form>
</body>
</html>
