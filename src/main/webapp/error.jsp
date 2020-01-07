<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<h1>Error details</h1>
<c:if test="${statusCode != 500}">
    <strong>Status Code</strong>: ${statusCode}<br/>
    <strong>Requested URI</strong>: ${requestUri}
</c:if>
<c:if test="${statusCode == 500}">
    <ul>
        <li>Servlet Name: ${servletName} </li>
        <li>Exception Name: ${throwableName} </li>
        <li>Requested URI: ${requestUri} </li>
        <li>Exception Message: ${throwableMessage} </li>
    </ul>
    <br><br>
    <a href="/mobiles/">Main page</a>
</c:if>