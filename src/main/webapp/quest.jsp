<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.quest.GameSession" %>
<!DOCTYPE html>
<html>
<head>
    <link href="static/main.css" rel="stylesheet">
    <title>Квест - <c:out value="${gameSession.currentStep.title}"/></title>
</head>
<body>
<h1><c:out value="${gameSession.currentStep.title}"/></h1>
<p>Имя капитана: <b><c:out value="${gameSession.playerName}"/></b></p>
<p><c:out value="${gameSession.currentStep.message}" escapeXml="false"/></p>

<c:if test="${!gameSession.currentStep.terminal}">
    <p>Выберите действие:</p>
    <form method="POST" action="<c:url value='/quest'/>">
        <input type="hidden" name="choice" value="1">
        <button type="submit" class="form-control" style="width: 200px; cursor: pointer;">
            <c:out value="${gameSession.currentStep.option1Text}"/>
        </button>
    </form>
    <br>
    <form method="POST" action="<c:url value='/quest'/>">
        <input type="hidden" name="choice" value="2">
        <button type="submit" class="form-control" style="width: 200px; cursor: pointer;">
            <c:out value="${gameSession.currentStep.option2Text}"/>
        </button>
    </form>
</c:if>

<c:if test="${gameSession.currentStep.terminal}">
    <br>
    <button onclick="window.location.href='<c:url value="/restart"/>'" class="form-control" style="width: 200px; cursor: pointer;">Начать заново</button>
</c:if>
</body>
</html>
