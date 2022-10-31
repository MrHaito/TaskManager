<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="task" scope="request" type="ru.taskmanager.model.Task"/>
<html>
<head>
    <title>Add new task</title>
</head>
<body>
<h3><a href="list">В список задач</a></h3>
<form method="post">
    <input value="${task.id}" type="hidden" name="id">
    <dl>
        <dt>Name:</dt>
        <dd><input type="text" name="name" value="${task.name}"></dd>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dd><input type="text" name="description" value="${task.description}"><br/></dd>
    </dl>
    <dl>
        <dt>Категория: </dt>
        <dd><select name="section">
            <option value="Общее" ${task.section == 'Общее' ? 'selected' : ''}>Общее</option>
            <option value="Фигурное катание" ${task.section == 'Фигурное катание' ? 'selected' : ''}>Фигурное
                катание</option>
            <option value="Гимнастика" ${task.section == 'Гимнастика' ? 'selected' : ''}>Гимнастика</option>
            <option value="Здоровье" ${task.section == 'Здоровье' ? 'selected' : ''}>Здоровье</option>
            <option value="Баскетбол" ${task.section == 'Баскетбол' ? 'selected' : ''}>Баскетбол</option>
        </select></dd>
    </dl>
    <dl>
        <dt>В план на неделю</dt>
        <dd><input type="checkbox" name="isWeekly" value="true"></dd>
    </dl>
    <dl>
        <dt>В план на месяц</dt>
        <dd><input type="checkbox" name="isMonthly" value="true"></dd>
    </dl>
    <button type="submit">Submit</button>
    <button type="reset" onclick="window.history.back()">Отменить</button>
</form>
</body>
</html>
