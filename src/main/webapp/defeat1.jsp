<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="static/main.css" rel="stylesheet">
    <title>Поражение</title>
</head>
<body>
<h1 style="color: red;">ТЫ НЕ ПОШЁЛ НА ПЕРЕГОВОРЫ. ПОРАЖЕНИЕ!</h1>
<p>Ты упустил свой шанс на великое приключение.</p>
<br>
<button onclick="window.location.href='<c:url value="/restart"/>'" class="form-control" style="width: 200px; cursor: pointer;">Начать заново</button>
</body>
</html>
