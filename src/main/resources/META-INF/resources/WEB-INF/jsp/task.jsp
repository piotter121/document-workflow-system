<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%@ include file="head.html" %>
    <title>System obiegu dokumentów - ${task.name}</title>
</head>
<body>

<div class="jumbotron">
    <div class="container">
        <h1>System obiegu dokumentów</h1>
        <p>Szczegółowe informacje związane z zadaniem</p>
        <form action="<c:url value="/logout" />" method="post">
            <input type="submit" value="Wyloguj" class="btn btn-danger btn-mini pull-right"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>

<div class="container">

</div>

</body>
</html>