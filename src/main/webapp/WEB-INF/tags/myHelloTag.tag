<%--
  Created by IntelliJ IDEA.
  User: alexandr
  Date: 19.7.15
  Time: 15.02
  To change this template use File | Settings | File Templates.
--%>
<%@ tag isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages/hello"/>
<sec:authentication var="user" property="principal" />
<form action="/logout" method="post">
    <table width="100%">
        <tr>
            <td>
                <h4><fmt:message key="welcome"/> ${user.username}</h4>
            </td>
            <td>
                ${SPRING_SECURITY_LAST_USERNAME}
            </td>
           <%-- Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            } else {
            String username = prncipal.toString();
            }
            --%>
            <td>
                <button type="submit" name="action" value="logout"><fmt:message key="out"/> </button>
            </td>
        </tr>
    </table>
</form>
