<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="task" scope="request" type="main.java.taskmanager.model.Task"/>
<html>
<head>
    <title>Add new task</title>
</head>
<body>
<form method="post">
    <dl>
        <dt>Name:</dt>
        <dd><input type="text" name="name" value="${task.name}"></dd>
    </dl>
    <dl>
        <dt>Description:</dt>
        <dd><input type="text" name="description" value="${task.description}"><br/></dd>
    </dl>
    <button type="submit">Submit</button>
</form>
</body>
</html>
