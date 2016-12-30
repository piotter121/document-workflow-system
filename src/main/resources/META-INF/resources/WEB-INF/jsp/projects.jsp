<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="header.html" %>
    <title>System obiegu dokumentów - projekty</title>
</head>
<body>

<header class="jumbotron">
    <div class="container">
        <h1>System obiegu dokumentów</h1>
        <p>
            Projekty
        </p>
        <form action="<c:url value="/logout" />" method="post">
            <input type="submit" value="Wyloguj" class="btn btn-danger btn-mini pull-right"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</header>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-4">

        </div>
        <div class="col-md-8">
            <c:forEach items="${projects}" var="project">
                <div class="caption">
                    <h3>
                        <a href="<spring:url value="/projects/${project.name}"/>">
                                ${project.name}
                        </a>
                    </h3>
                    <p>${project.description}</p>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

</body>
</html>