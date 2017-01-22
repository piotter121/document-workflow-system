<%--@elvariable id="addParticipantErrorMessage" type="java.lang.String"--%>
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

<%@ include file="navbarTaskActive.jsp" %>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-8">
            <ul class="nav nav-pills">
                <li role="presentation" ${empty addParticipantErrorMessage ? 'class="active"' : ''}>
                    <a data-toggle="pill" href="#files">Pliki</a>
                </li>
                <li role="presentation" ${empty addParticipantErrorMessage ? '' : 'class="active"'}>
                    <a data-toggle="pill" href="#participants">Uczestnicy</a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="files" class="tab-pane fade ${empty addParticipantErrorMessage ? 'in active' : ''}">
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
                            <div class="row">
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
                                                    <c:if test="${file.markedToConfirm}">
                                                        <span class="badge">DO ZATWIERDZENIA</span>
                                                    </c:if>
                                                    <c:if test="${file.confirmed}">
                                                        <span class="badge">ZATWIERDZONY</span>
                                                    </c:if>
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
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>

                <div id="participants" class="tab-pane fade ${empty addParticipantErrorMessage ? '' : 'in active'}">
                    <div class="toolbar" role="toolbar">
                        <c:if test="${currentUser eq task.administrator}">
                            <div class="btn-group">
                                <button class="btn btn-primary" type="button" data-toggle="collapse"
                                        data-target="#addParticipant" aria-expanded="false"
                                        aria-controls="addParticipant">
                                    <span class="glyphicon glyphicon-plus"></span>
                                    Dodaj uczestnika do zadania
                                </button>
                            </div>
                        </c:if>
                    </div>

                    <c:if test="${currentUser eq task.administrator}">
                        <div class="collapse" id="addParticipant">
                            <div class="well">
                                <div class="row">
                                    <form method="post"
                                          action="<spring:url value="/projects/${project.id}/tasks/${task.id}/addParticipant"/>">
                                        <div class="form-group">
                                            <label class="control-label col-md-2" for="participantEmail">
                                                Adres e-mail użytkownika
                                            </label>
                                            <div class="col-md-5">
                                                <input id="participantEmail" name="participantEmail"
                                                       class="form-control col-md-5"
                                                       type="email"/>
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                       value="${_csrf.token}"/>
                                            </div>
                                        </div>
                                        <button class="btn btn-primary" type="submit">
                                            Dodaj
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <c:if test="${not empty addParticipantErrorMessage}">
                            <div class="alert alert-danger alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <span class="glyphicon glyphicon-exclamation-sign"></span> ${addParticipantErrorMessage}
                            </div>
                        </c:if>
                    </c:if>

                    <c:choose>
                        <c:when test="${empty task.participants}">
                            <div class="alert alert-info">
                                <strong>Brak uczestników zadania!</strong>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="text-center">
                                <h3>Uczestnicy biorący udział w zadaniu</h3>
                            </div>
                            <div class="list-group">
                                <c:forEach items="${task.participants}" var="participant">
                                    <c:if test="${currentUser != participant}">
                                        <div class="list-group-item">
                                            <h3 class="list-group-item-heading">
                                                    ${participant.fullName}
                                            </h3>
                                            <div class="list-group-item-text">
                                                <div class="row">
                                                    <div class="col-md-8">
                                                        <strong>Adres e-mail:</strong> ${participant.email}
                                                    </div>
                                                    <div class="col-md-4">
                                                        <a class="btn btn-info pull-right"
                                                           href="mailto:${participant.email}">
                                                            <span class="glyphicon glyphicon-envelope"></span>
                                                            Wyślij wiadomość e-mail
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="panel panel-info">
                <div class="panel-heading">Szczegóły zadania</div>
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
                            <fmt:formatDate value="${task.creationDate}" type="both" dateStyle="long"
                                            timeStyle="short"/>
                        </h4>
                    </div>
                    <div class="list-group-item">
                        <p class="list-group-item-heading">Data modyfikacji</p>
                        <h4 class="list-group-item-text">
                            <fmt:formatDate value="${task.modificationDate}" type="both" dateStyle="long"
                                            timeStyle="short"/>
                        </h4>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>