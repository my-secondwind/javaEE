<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<table class="table">
   <thead>
   <tr>
      <th>ID</th>
      <th>model</th>
      <th>price</th>
      <th>manufacturer</th>
   </tr>
   </thead>
   <tbody>
   <c:forEach var="mobile" items="${mobiles}">
      <tr>
         <td scope="row">${mobile.id}</td>
         <td>${mobile.model}</td>
         <td>${mobile.price}</td>
         <td>${mobile.manufacturer}</td>
         <td><a href="${pageContext.request.contextPath}/showmobile?id=${mobile.id}">Details</a></td>
         <td><a href="${pageContext.request.contextPath}/editmobile?id=${mobile.id}&page=allmobiles">Edit</a></td>
         <td><a href="${pageContext.request.contextPath}/deletemobile?id=${mobile.id}">Delete</a></td>
      </tr>
   </c:forEach>
   </tbody>
</table>

<br>
<a href="/mobiles/">Main page</a>