<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.quest.StatisticsManager" %>
<%@ page import="java.util.Map" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <link href="static/main.css" rel="stylesheet">
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <script src="<c:url value="/static/jquery-3.6.0.min.js"/>"></script>
    <title>The prologue</title>
    <style>
        .stats-table {
            margin-top: 50px;
            border-collapse: collapse;
            width: 400px;
        }
        .stats-table th, .stats-table td {
            border: 1px solid #00ff41;
            padding: 10px;
            text-align: center;
        }
        .stats-table th {
            background-color: #141e30;
        }
    </style>
</head>
<body>
<h1>ПРОЛОГ</h1>
Ты стоишь в космическом порту и готов подняться на борт своего корабля.<br>
Разве ты не об этом мечтал?<br>
Стать капитаном галактического судна с экипажем, который будет совершать подвиги под твоим командованием.<br>
<b>
Так что вперед!
</b>
<h2>ЗНАКОМСТВО С ЭКИПАЖЕМ</h2>
Когда ты поднялся на борт корабля, тебя поприветствовала девушка с чёрной папкой в руках:<br>
- Здравствуйте, командир! Я Звездаида - ваша помощница. Видите? Там в углу пьёт кофе<br>
наш штурман - сержант Перегарный Шлейф, под штурвалом спит наш бортмеханик - Чёрный Штуцер,<br>
а фотографирует его Полярный Меридиан - наш навигатор.<br>
А как обращаться к вам?
<br>
<form method="POST" action="start">
    <div class="col-md-6">
        <label for="createName" class="form-label">Введите своё имя<span class="text-danger"> </span></label>
        <input type="text" id="createName" name="name" class="form-control" maxlength="20" required>
    </div>
    <br>
    <button type="submit" class="form-control" style="width: 200px; cursor: pointer;">Начать путешествие</button>
</form>

<!-- Таблица со статистикой из сессии -->
<%
    Map<String, Object> stats = StatisticsManager.getSessionStats(session);
    if (stats != null) {
%>
<table class="stats-table">
    <thead>
    <tr>
        <th>Имя игрока</th>
        <th>IP адрес</th>
        <th>Количество игр</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><%= stats.get("playerName") != null ? stats.get("playerName") : "неизвестно" %></td>
        <td><%= request.getRemoteAddr() %></td>
        <td><%= stats.get("totalGames") %></td>
    </tr>
    </tbody>
</table>
<%
    } else {
%>
<table class="stats-table">
    <thead>
    <tr>
        <th>Имя игрока</th>
        <th>IP адрес</th>
        <th>Количество игр</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>неизвестно</td>
        <td>неизвестно</td>
        <td>0</td>
    </tr>
    </tbody>
</table>
<%
    }
%>
</body>
</html>
