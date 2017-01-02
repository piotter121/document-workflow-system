<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="head.html" %>
    <title>System obiegu dokumentów - projekty</title>
</head>
<body>

<header class="jumbotron">
    <div class="container">
        <h1>System obiegu dokumentów</h1>
        <p>Projekty</p>
        <form action="<c:url value="/logout" />" method="post">
            <input type="submit" value="Wyloguj" class="btn btn-danger btn-mini pull-right"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</header>

<div class="container">
    <div class="row">
        <button data-toggle="collapse" class="btn btn-default" data-target="#filter">Opcje filtrowania</button>
        <a href="<spring:url value="/projects/add"/>" class="btn btn-primary">Stwórz nowy</a>
    </div>

    <div id="filter" class="collapse">
        <form action="<c:url value="/projects" />" method="get">
            <div class="row">
                <input type="checkbox" name="onlyOwned" value="">
                Pokaż tylko administrowane projekty
                </input>
            </div>
            <div class="row">
                <input type="submit" class="btn btn-success" value="Filtruj"/>
            </div>
        </form>
    </div>

    <div class="row">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>Nazwa projektu</th>
                <th>Opis projektu</th>
                <th>Liczba zadań w projekcie</th>
                <th>Imię i nazwisko właściciela</th>
                <th>Data utworzenia</th>
                <th>Data ostatniej zmiany</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${projects}" var="project">
                <tr>
                    <td>${project.name}</td>
                    <td>${project.description}</td>
                    <td>${project.numberOfTasks}</td>
                    <td>${project.administrator.fullName}</td>
                    <td>${project.creationDate}</td>
                    <td>${project.lastModified}</td>
                    <td><a href="<spring:url value="/projects/${project.id}"/>" class="btn btn-info" role="button">
                        Szczegóły
                    </a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>