<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="ru.javawebinar.topjava.util.Dates" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<%
    List<MealTo> meals = (List<MealTo>) request.getAttribute("meals");
%>
<table style="width: 1000px;">
    <thead>
    <tr>
        <th rowspan="1" colspan="1" style="width: 250px;">Дата/Время</th>
        <th rowspan="1" colspan="1" style="width: 250px;">Описание</th>
        <th rowspan="1" colspan="1" style="width: 250px;">Калории</th>
        <th rowspan="1" colspan="2" style="width: 250px;">Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
    <tr data-excess="<c:out value="${meal.excess}"/>">
        <td><c:out value="${Dates.formatLocalDateTime(meal.dateTime, Dates.MEAL_FORMATTER)}"/></td>
        <td><c:out value="${meal.description}"/></td>
        <td><c:out value="${meal.calories}"/></td>
        <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Редактировать</a></td>
        <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Удалить</a></td>
        </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=add">Добавить</a></p>
</body>
</html>
