<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.taskmanager.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tasks list</title>
</head>
<body>
<h3><a href="?action=add">Добавить задачу</a></h3>
<c:forEach items="<%=SectionType.values()%>" var="section">
    <h2>${section.title}</h2>
    <c:forEach items="${tasks}" var="task">
        <c:choose>
            <c:when test="${task.section == section.title}">
                <jsp:useBean id="task" type="ru.taskmanager.model.Task"/>
                <h3>${task.name}</h3>
                <p>${task.description}</p>
                <p><a href="list?id=${task.id}&action=edit">Изменить</a></p>
                <p><a href="list?id=${task.id}&action=delete">Удалить</a></p>
            </c:when>
        </c:choose>
    </c:forEach>
</c:forEach>
</body>
</html>
