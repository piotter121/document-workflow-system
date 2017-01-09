<%--@elvariable id="project" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.ProjectInfoDTO"--%>
<%--@elvariable id="currentUser" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.UserInfoDTO"--%>
<%--@elvariable id="task" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.TaskInfoDTO"--%>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%@ include file="css.jsp" %>
    <title>System obiegu dokumentów - ${task.name}</title>
</head>
<body>

<div class="page-header">
    <h1>
        <img src="<spring:url value="/images/logo.png"/>" width="40px" height="40px">
        <a href="<spring:url value="/projects/${task.projectId}"/>">
            ${project.name}
        </a>
        <small>${task.name}</small>
    </h1>
</div>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li>
                <a href="<spring:url value="/"/>">
                    <span class="glyphicon glyphicon-home"></span> Strona główna
                </a>
            </li>
            <li>
                <a href="<spring:url value="/projects"/>">
                    <span class="glyphicon glyphicon-folder-close"></span> Projekty
                </a>
            </li>
            <li class="active">
                <a href="<spring:url value="/tasks"/>">
                    <span class="glyphicon glyphicon-tasks"></span> Zadania
                </a>
            </li>
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
            <ul class="nav nav-pills">
                <li role="presentation" class="active">
                    <a data-toggle="pill" href="#files">Pliki</a>
                </li>
                <li role="presentation">
                    <a data-toggle="pill" href="#participants">Uczestnicy</a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="files" class="tab-pane fade in active">
                    <div class="toolbar" role="toolbar">
                        <c:if test="${currentUser != task.administrator}">
                            <div class="btn-group">
                                <a href="mailto:${task.administrator.email}" class="btn btn-info">
                                    <span class="glyphicon glyphicon-envelope"></span>
                                    Wyślij wiadomość do administratora zadania
                                </a>
                            </div>
                        </c:if>
                        <div class="btn-group">
                            <a href="<spring:url value="/projects/${project.id}/tasks/${task.id}/files/add"/>"
                               class="btn btn-success">
                                <span class="glyphicon glyphicon-plus"></span>
                                Dodaj nowy plik
                            </a>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${empty task.filesInfo}">
                            <div class="alert alert-info text-center" role="alert">
                                <strong>Zadanie nie posiada jeszcze żadnych plików!</strong>
                                Dodaj nowy plik, aby kontynuować pracę
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center">
                                <h3>Pliki w zadaniu ${task.name}</h3>
                            </div>
                            <c:forEach items="${task.filesInfo}" var="file">
                                <div class="col-md-6">
                                    <div class="panel panel-${file.markedToConfirm ? 'primary' : file.confirmed ? 'success' : 'default'}">
                                        <div class="panel-heading">
                                            <h3 class="panel-title">
                                                <img src="<spring:url value="/images/${file.extension}.png"/>"
                                                     height="20"/>
                                                <a href="<spring:url value="/projects/${project.id}/tasks/${task.id}/files/${file.id}"/>">
                                                        ${file.name}
                                                </a>
                                            </h3>
                                        </div>

                                        <div class="panel-body">
                                            <p>Utworzono w dniu
                                                <fmt:formatDate value="${file.creationDate}" dateStyle="long"/></p>
                                            <p>Aktualny numer wersji to ${file.latestVersion.versionString}</p>
                                            <c:if test="${file.creationDate != file.latestVersion.saveDate}">
                                                <p>Ostatnio zmodyfikowany w dniu <fmt:formatDate
                                                        value="${file.modificationDate}" dateStyle="long"/>
                                                    przez ${file.latestVersion.author.fullName}</p>
                                            </c:if>
                                        </div>

                                        <div class="panel-footer">
                                            <span class="glyphicon glyphicon-list-alt"></span>
                                                ${file.versions.size()} ${file.versions.size() eq 1 ? 'wersja' : 'wersji'}
                                            pliku
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>

                </div>

                <div id="participants" class="tab-pane fade">

                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="panel panel-info">
                <div class="panel-heading">Szczegóły zadania</div>
                <div class="panel-body">
                    <div class="list-group">
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Nazwa</p>
                            <h4 class="list-group-item-text">${task.name}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Opis</p>
                            <h4 class="list-group-item-text">${task.description}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Administrator</p>
                            <h4 class="list-group-item-text">${task.administrator.fullName}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Data utworzenia</p>
                            <h4 class="list-group-item-text">
                                <fmt:formatDate value="${task.creationDate}" pattern="dd.MM.yyyy K:mm"/>
                            </h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Data modyfikacji</p>
                            <h4 class="list-group-item-text">
                                <fmt:formatDate value="${task.modificationDate}" pattern="dd.MM.yyyy K:mm"/>
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