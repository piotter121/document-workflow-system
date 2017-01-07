<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%--@elvariable id="projects" type="java.util.List"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="css.jsp" %>
    <title>System obiegu dokumentów - projekty</title>
</head>
<body>

<div class="jumbotron">
    <div class="container">
        <h1>System obiegu dokumentów</h1>
        <p>Projekty</p>
        <form action="<c:url value="/logout" />" method="post">
            <input type="submit" value="Wyloguj" class="btn btn-danger btn-mini pull-right"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>

<div class="container">
    <div class="row">
        <div class="toolbar" role="toolbar">
            <div class="btn-group" role="group">
                <button data-toggle="collapse" class="btn btn-default" data-target="#filter">
                    Opcje filtrowania
                </button>
            </div>
            <div class="btn-group" role="group">
                <a href="<spring:url value="/projects/add"/>" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus"></span> Stwórz nowy
                </a>
            </div>
        </div>
        <div id="filter" class="collapse">
            <form action="<spring:url value="/projects"/>" method="get">
                <label>
                    <input type="checkbox" name="onlyOwned"/>Pokaż tylko administrowane projekty
                </label>
                <input type="submit" class="btn btn-success" value="Filtruj"/>
            </form>
        </div>
    </div>

    <div class="row">
        <c:choose>
            <c:when test="${not empty projects}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Projekty w których bierzesz udział
                    </div>
                    <div class="list-group">
                        <c:forEach items="${projects}" var="project">
                            <a href="<spring:url value="/projects/${project.id}"/>" class="list-group-item">
                                <span class="badge">Liczba zadań: ${project.numberOfTasks}</span>
                                <h4 class="list-group-item-heading">${project.name}</h4>
                                <p class="list-group-item-text">${project.description}</p>
                                <p class="list-group-item-text">
                                    <strong>Data utworzenia:</strong>
                                    <fmt:formatDate value="${project.creationDate}" pattern="dd.MM.yyyy KK:mm"/>
                                </p>
                                <p class="list-group-item-text">
                                    <strong>Data modyfikacji:</strong>
                                    <fmt:formatDate value="${project.lastModified}" pattern="dd.MM.yyyy KK:mm"/>
                                </p>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-info text-center">
                    <strong>Brak projektów do wyświetlnia.</strong> Utwórz nowy projekt, lub poproś o dodanie do
                    istniejącego, aby rozpocząć pracę.
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

</body>
</html>