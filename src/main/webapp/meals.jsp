<%--
  Created by IntelliJ IDEA.
  User: alex_jd
  Date: 10/9/21
  Time: 8:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>
    <title>Title</title>

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=add">Add Meal</a>
<hr>

<c:set var="mealList" value="${requestScope.mealList}" />
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach var="mealTo" items="${mealList}">
        <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <fmt:formatDate value="${parsedDateTime}" var="dateTime" pattern="yyyy-MMM-dd hh:mm" />
        <c:if test="${mealTo.excess eq true}">
            <tr style="color: red">
                <td><c:out value="${dateTime}" /></td>
                <td><c:out value="${mealTo.description}" /></td>
                <td><c:out value="${mealTo.calories}" /></td>
                <td><a href="meals?action=update&mealId=<c:out value="${mealTo.id}"/>">Update</a> </td>
                <td><a href="meals?action=delete&mealId=<c:out value="${mealTo.id}"/>">Delete</a></td>
            </tr>
        </c:if>
        <c:if test="${mealTo.excess eq false}">
            <tr style="color: green;">
                <td><c:out value="${dateTime}" /></td>
                <td><c:out value="${mealTo.description}" /></td>
                <td><c:out value="${mealTo.calories}" /></td>
                <td><a href="meals?action=update&mealId=<c:out value="${mealTo.id}"/>">Update</a> </td>
                <td><a href="meals?action=delete&mealId=<c:out value="${mealTo.id}"/>">Delete</a></td>
            </tr>
        </c:if>

    </c:forEach>
</table>


</body>
</html>
