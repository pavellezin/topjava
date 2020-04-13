<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:if test="${empty meal.id}">
        <title>Добавить</title>
    </c:if>
    <c:if test="${!empty meal.id}">
        <title>Редактировать</title>
    </c:if>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<c:if test="${empty meal.id}">
    <c:url value="/meals?action=add" var="var"/>
</c:if>
<c:if test="${!empty meal.id}">
    <c:url value="/meals?action=edit&id=${meal.id}" var="var"/>
</c:if>
<form action="${var}" method="POST">
    <c:if test="${!empty meal.id}">
        <input type="hidden" name="id" value="${meal.id}">
    </c:if>
    <label for="dateTime">Дата/Время</label>
    <input type="text" name="dateTime" id="dateTime">
    <label for="description">Описание</label>
    <input type="text" name="description" id="description">
    <label for="calories">Калории</label>
    <input type="text" name="calories" id="calories">
    <c:if test="${empty meal.id}">
        <input type="submit" value="Добавить">
    </c:if>
    <c:if test="${!empty meal.id}">
        <input type="submit" value="Редактировать">
    </c:if>
</form>
</body>
</html>
