<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <link href="static/main.css" rel="stylesheet">
    <script src="<c:url value='/static/jquery-3.6.0.min.js'/>"></script>
    <title>Квест - Следующая глава</title>
</head>
<body>
<h1>Ты принял вызов. Поднимаешься на мостик к капитану?</h1>
<p>Имя капитана: <b><%= request.getParameter("name") != null ? request.getParameter("name") : "неизвестно" %></b></p>
<p>Выберите действие:</p>
<br>
<button id="acceptCallBtn" class="form-control" style="width: 200px; cursor: pointer;">Подняться на мостик</button>
<br>
<button id="rejectCallBtn" class="form-control" style="width: 200px; cursor: pointer;">Отказаться подниматься на мостик</button>

<script>
    $(document).ready(function() {
        // Получаем имя из URL-параметра
        const urlParams = new URLSearchParams(window.location.search);
        const name = urlParams.get('name');

        // Обработка кнопки "Подняться на мостик"
        $('#acceptCallBtn').click(function() {
            // Переход на следующую страницу квеста с передачей имени
            window.location.href = 'quest3.jsp?name=' + encodeURIComponent(name);
        });

        // Обработка кнопки "Отказаться подниматься на мостик"
        $('#rejectCallBtn').click(function() {
            // Переход на страницу поражения
            window.location.href = 'defeat1.jsp';
        });
    });
</script>
</body>
</html>
