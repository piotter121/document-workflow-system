<%--@elvariable id="project" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.ProjectInfoDTO"--%>
<%--@elvariable id="currentUser" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.UserInfoDTO"--%>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%--@elvariable id="delete" type="java.lang.String"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="css.jsp" %>
    <title>System obiegu dokumentów - ${project.name}</title>
</head>
<body>
<div class="page-header">
    <h1>
        <img src="<spring:url value="/images/logo.png"/>" width="40px" height="40px">
        System obiegu dokumentów
        <small>${project.name}</small>
    </h1>
</div>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li><a href="<spring:url value="/"/>"><span class="glyphicon glyphicon-home"></span> Strona główna</a></li>
            <li class="active"><a href="<spring:url value="/projects"/>"><span
                    class="glyphicon glyphicon-folder-close"></span> Projekty</a></li>
            <li><a href="<spring:url value="/tasks"/>"><span class="glyphicon glyphicon-tasks"></span> Zadania</a></li>
        </ul>

        <p class="navbar-text">Zalogowany jako ${currentUser.fullName}</p>

        <form class="navbar-form navbar-right" action="<c:url value="/logout" />" method="post">
            <button id="logout" type="submit" class="btn btn-default">
                <span class="glyphicon glyphicon-log-out"></span> Wyloguj
            </button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-8">
            <div class="toolbar" role="toolbar">
                <c:if test="${currentUser eq project.administrator}">
                    <div class="btn-group">
                        <a href="<spring:url value="/projects/${project.id}/tasks/add"/>" class="btn btn-success">
                            <span class="glyphicon glyphicon-plus"></span>
                            Dodaj nowe zadanie
                        </a>
                        <button class="btn btn-danger" type="submit" form="deleteProject">
                            <span class="glyphicon glyphicon-remove"></span>
                            Usuń projekt
                        </button>
                    </div>
                    <form id="deleteProject" method="post" action="<spring:url value="/projects/${project.id}"/>">
                        <input type="hidden" name="${_csrf.parameterName}"
                               value="${_csrf.token}"/>
                        <input type="hidden" name="_method" value="delete"/>
                    </form>
                </c:if>

                <c:if test="${currentUser != project.administrator}">
                    <div class="btn-group">
                        <a href="mailto:${project.administrator.email}" class="btn btn-info">
                            <span class="glyphicon glyphicon-envelope"></span>
                            Wyślij wiadomość do administratora projektu
                        </a>
                    </div>
                </c:if>
            </div>

            <c:if test="${delete eq 'success'}">
                <div class="alert alert-success alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <strong>Pomyślnie usunięto zadanie</strong>
                </div>
            </c:if>

            <c:choose>
                <c:when test="${empty project.tasks}">
                    <div class="alert alert-info text-center" role="alert">
                        <strong>Projekt nie posiada jeszcze żadnych zadań!</strong>
                        Dodaj nowe zadanie, aby rozpocząć pracę
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="text-center">
                        <h3>Zadania w projekcie ${project.name}</h3>
                    </div>
                    <c:forEach items="${project.tasks}" var="task">
                        <div class="col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">
                                        <a href="<spring:url value="/projects/${project.id}/tasks/${task.id}"/>">
                                                ${task.name}
                                        </a>
                                    </h3>
                                </div>

                                <div class="panel-body">
                                    <p>Utworzono <fmt:formatDate value="${task.creationDate}" dateStyle="long"/></p>
                                    <c:if test="${not empty task.lastModifiedFile}">
                                        <p>Ostatnio zmodyfikowany plik ${task.lastModifiedFile.name}
                                            w dniu <fmt:formatDate
                                                    value="${task.lastModifiedFile.latestVersion.saveDate}"
                                                    dateStyle="long"/>
                                            przez ${task.lastModifiedFile.latestVersion.author.fullName}</p>
                                    </c:if>
                                </div>

                                <div class="panel-footer">
                                    <span class="glyphicon glyphicon-file"></span>
                                        ${task.numberOfFiles} ${task.numberOfFiles eq 1 ? 'plik ' : 'plików '}
                                    <span class="glyphicon glyphicon-user"></span>
                                        ${task.numberOfParticipants} ${task.numberOfParticipants eq 1 ? 'uczestnik ' : 'uczestrników '}
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-4">
            <div class="panel panel-info">
                <div class="panel-heading">Szczegóły projektu</div>
                <div class="panel-body">
                    <div class="list-group">
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Nazwa</p>
                            <h4 class="list-group-item-text">${project.name}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Opis</p>
                            <h4 class="list-group-item-text">${project.description}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Administrator</p>
                            <h4 class="list-group-item-text">${project.administrator.fullName}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Data utworzenia</p>
                            <h4 class="list-group-item-text">
                                <fmt:formatDate pattern="dd.MM.yyyy K:mm" value="${project.creationDate}"/>
                            </h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Data ostatniej modyfikacji</p>
                            <h4 class="list-group-item-text">
                                <fmt:formatDate pattern="dd.MM.yyyy K:mm"
                                                value="${project.lastModifiedTask.lastModifiedFile.latestVersion.saveDate}"/>
                            </h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>