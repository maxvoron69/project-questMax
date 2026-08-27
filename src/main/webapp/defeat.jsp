<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.quest.GameSession" %>
<!DOCTYPE html>
<html>
<head>
    <link href="static/main.css" rel="stylesheet">
    <title><c:out value="${gameSession.currentStep.title}"/></title>
</head>
<body>
<h1 style="color: red;"><c:out value="${gameSession.currentStep.title}"/></h1>
<p><c:out value="${gameSession.currentStep.message}" escapeXml="false"/></p>
<br>
<button onclick="window.location.href='<c:url value="/restart"/>'" class="form-control" style="width: 200px; cursor: pointer;">Начать заново</button>
</body>
</html>
