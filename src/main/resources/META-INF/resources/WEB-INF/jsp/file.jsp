<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%--@elvariable id="currentUser" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.UserInfoDTO"--%>
<%--@elvariable id="task" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.TaskInfoDTO"--%>
<%--@elvariable id="file" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.FileMetadataDTO"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="css.jsp" %>
    <title>System obiegu dokumentów - ${file.name}</title>
</head>

<body>

<div class="page-header">
    <h1>
        <img src="<spring:url value="/images/logo.png"/>" width="40px" height="40px">
        <a href="<spring:url value="/projects/${task.projectId}/tasks/${task.id}"/>">
            ${task.name}
        </a>
        <small>${file.name}</small>
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
            <div class="toolbar" role="toolbar">
                <div class="btn-group">

                </div>
                <form id="markToConfirm" method="post" action="<spring:url value="/projects/${task.projectId}/tasks/${task.id}/files/"/>">

                </form>
            </div>

            <div class="text-center">
                <h3>Zapisane wersje pliku ${file.name}</h3>
            </div>
            <div id="versions" class="list-group">
                <c:forEach items="${file.versionSortedBySaveDateDESC}" var="version">
                <div class="list-group-item">
                    <span class="badge">Wersja ${version.versionString}</span>
                    <c:if test="${version eq file.latestVersion}">
                        <span class="badge">Najnowsza wersja</span>
                    </c:if>
                    <div class="list-group-item-text">
                        <div class="row">
                            <div class="col-md-5">
                                <dl class="dl-horizontal">
                                    <dt>Ilość zmian:</dt>
                                    <dd>${version.numberOfDifferences}</dd>
                                    <dt>Ilość zmienionych linii:</dt>
                                    <dd>${version.numberOfChangedLines}</dd>
                                </dl>
                            </div>
                            <blockquote class="col-md-7">
                                <p>${version.message}</p>
                                <footer>
                                        ${version.author.fullName} w dniu <fmt:formatDate value="${version.saveDate}"
                                                                                          type="both" dateStyle="long"
                                                                                          timeStyle="long"/>
                                </footer>
                            </blockquote>
                            <div class="toolbar col-md-12" role="toolbar">
                                <div class="btn-group">
                                    <a class="btn btn-primary" target="_blank"
                                       href="<spring:url value="/projects/${task.projectId}/tasks/${task.id}/files/${file.id}/versions/${version.id}/content"/>">
                                        <span class="glyphicon glyphicon-download"></span> Pobierz plik
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="col-md-4">
            <div class="panel panel-info">
                <div class="panel-heading">
                    Szczegóły pliku
                </div>
                <div class="list-group">
                    <div class="list-group-item">
                        <h6 class="list-group-item-heading">Nazwa</h6>
                        <p class="list-group-item-text">${file.name}</p>
                    </div>
                    <div class="list-group-item">
                        <h6 class="list-group-item-heading">Opis</h6>
                        <p class="list-group-item-text">${file.description}</p>
                    </div>
                    <div class="list-group-item">
                        <h6 class="list-group-item-heading">Typ zawartości</h6>
                        <p class="list-group-item-text">${file.contentType}</p>
                    </div>
                    <div class="list-group-item">
                        <h6 class="list-group-item-heading">Data utworzenia</h6>
                        <p class="list-group-item-text"><fmt:formatDate value="${file.creationDate}" type="both"
                                                                        dateStyle="long" timeStyle="long"/></p>
                    </div>
                    <div class="list-group-item">
                        <h6 class="list-group-item-heading">Data modyfikacji</h6>
                        <p class="list-group-item-text"><fmt:formatDate value="${file.modificationDate}" type="both"
                                                                        dateStyle="long" timeStyle="long"/></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>