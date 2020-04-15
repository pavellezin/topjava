<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.topjava.util.Dates" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><c:out value="${not empty meal.id ? 'Редактировать' : 'Добавить'}"/></title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<form action="${var}" method="POST">
    <input type="hidden" name="id" value="<c:out value="${meal.id}"/>">
    <label for="dateTime">Дата/Время</label>
    <input type="text" name="dateTime" id="dateTime" value="<c:out value="${not empty meal.id ? Dates.formatLocalDateTime(meal.dateTime, Dates.MEAL_FORMATTER) : ''}" />">
    <label for="description">Описание</label>
    <input type="text" name="description" id="description"
           value="<c:out value="${not empty meal.id ? meal.description : ''}" />">
    <label for="calories">Калории</label>
    <input type="text" name="calories" id="calories" value="<c:out value="${not empty meal.id ? meal.calories : ''}" />">
    <input type="submit" value="<c:out value="${not empty meal.id ? 'Редактировать' : 'Добавить'}"/>">
</form>
</body>
</html>
