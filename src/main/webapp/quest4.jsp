<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="static/main.css" rel="stylesheet">
    <script src="<c:url value='/static/jquery-3.6.0.min.js'/>"></script>
    <title>Квест - ПОБЕДА!</title>
</head>
<body>
<h1 style="color: yellow;">Тебя вернули домой. ПОБЕДА!</h1>
<p>Имя капитана: <b><%= request.getParameter("name") != null ? request.getParameter("name") : "неизвестно" %></b></p>
<br>
<button onclick="window.location.href='<c:url value="/restart"/>'" class="form-control" style="width: 200px; cursor: pointer;">Начать заново</button>
</body>
</html>