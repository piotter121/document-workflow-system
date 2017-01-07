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

<div class="container-fluid">
    <div class="row">
        <div class="col-md-9">
            <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#pliki">Pliki</a></li>
                <li><a data-toggle="tab" href="#participants">Uczestnicy</a></li>
            </ul>

            <div class="tab-content">
                <div id="pliki" class="tab-pane fade in active">
                    <div class="btn-group">
                        <a href="<spring:url value="/projects/${task.projectId}/tasks/${task.id}/files/add"/>"
                           class="btn btn-primary">
                            Dodaj nowy plik
                        </a>
                    </div>

                    <c:choose>
                        <c:when test="${not empty task.filesInfo}">
                            <table class="table table-striped table-hover">
                                <thead>
                                <tr>
                                    <th>Nazwa</th>
                                    <th>Typ zawartości</th>
                                    <th>Data modyfikacji</th>
                                    <th>Zatwierdzony</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${task.filesInfo}" var="fileInfo">
                                    <tr>
                                        <td>${fileInfo.name}</td>
                                        <td>${fileInfo.contentType}</td>
                                        <td><fmt:formatDate value="${fileInfo.modificationDate}"
                                                            pattern="dd.MM.yyyy KK:mm"/></td>
                                        <td>${fileInfo.confirmed ? 'tak' : 'nie'}</td>
                                        <td>
                                            <a href="<spring:url value="/projects/${task.projectId}/tasks/${task.id}/files/${fileInfo.id}"/>">
                                                <span class="glyphicon glyphicon-info-sign"></span>
                                            </a>
                                            <c:if test="${task.administrator eq currentUser}">
                                                <!-- Czynności dla administratora -->
                                            </c:if>
                                            <c:if test="${task.participants.contains(currentUser)}">
                                                <!-- Czynności dla użytkowników -->
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-info text-center">
                                <strong>Brak plików w zadaniu.</strong> Dodaj nowy plik, aby udostępnić go innym
                                uczestnikom zadania.
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div id="participants" class="tab-pane fade">

                </div>
            </div>
        </div>

        <div class="col-md-3">
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