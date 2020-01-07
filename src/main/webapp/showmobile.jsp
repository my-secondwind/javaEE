<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<ul class="list-group">
   <li class="list-group-item">${mobile.id}</li>
   <li class="list-group-item">${mobile.model}</li>
   <li class="list-group-item">${mobile.price}</li>
   <li class="list-group-item">${mobile.manufacturer}</li>
   <li><a href="${pageContext.request.contextPath}/editmobile?id=${mobile.id}&page=showmobile">Edit</a></li>
   <li><a href="${pageContext.request.contextPath}/deletemobile?id=${mobile.id}">Delete</a></li>
</ul>

<br>
<a href="/mobiles/">Main page</a>
