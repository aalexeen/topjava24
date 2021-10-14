<%--
  Created by IntelliJ IDEA.
  User: alex_jd
  Date: 10/11/21
  Time: 11:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Edit/Update meal</title>
</head>
<body>
<c:set var="meal" value="${requestScope.meal}" />
    <form method="POST">
        <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
        <fmt:formatDate value="${parsedDateTime}" var="dateTime" pattern="MM/dd/yyyy hh:mm" />
        DateTime <input type="text" name="DateTime" value="${dateTime}"/><br />
        Description <input type="text" name="description" value="<c:out value="${meal.description}"/>"/><br />
        Calories <input type="text" name="calories" value="<c:out value="${meal.calories}"/>"/><br />
        <input type="submit" value="Submit">
    </form>

</body>
</html>
