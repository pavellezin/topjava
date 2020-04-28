<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <link href="/topjava/css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <form method="get" action="meals">
        <div>
            <input type="hidden" id="filter" name="action" value="filter">
            <table>
                <tr>
                    <td>
                        <label for="sday">Start date:</label>
                        <input type="date" id="sday" name="sday" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}">
                    </td>
                    <td>
                        <label for="eday">End date:</label>
                        <input type="date" id="eday" name="eday" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}">
                    </td>
                    <td>
                        <label for="stime">Start time:</label>
                        <input type="time" id="stime" name="stime" pattern="[0-24]{2}:[0-59]{2}">
                    </td>
                    <td>
                        <label for="etime">End time:</label>
                        <input type="time" id="etime" name="etime" pattern="[0-24]{2}:[0-59]{2}">
                    </td>
                    <span class="validity"></span>
                </tr>
            </table>
            <button type="submit">Filter</button>
        </div>

    </form>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>