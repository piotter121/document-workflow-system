<%--@elvariable id="currentUser" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.UserInfoDTO"--%>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%--@elvariable id="projects" type="java.util.List"--%>
<%--@elvariable id="project" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.ProjectInfoDTO"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%@ include file="css.jsp" %>
    <title>System obiegu dokumentów - Projekty</title>
</head>
<body>

<div class="page-header">
    <h1>
        <img src="<spring:url value="/images/logo.png"/>" width="40px" height="40px">
        System obiegu dokumentów
        <small>Projekty</small>
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

    <div class="row col-md-12">
        <div class="toolbar" role="toolbar">
            <div class="btn-group" role="group">
                <a href="<spring:url value="/projects/add"/>" class="btn btn-primary">
                    <span class="glyphicon glyphicon-plus"></span> Stwórz nowy projekt
                </a>
            </div>
        </div>
    </div>


    <c:choose>
        <c:when test="${not empty projects}">
            <div class="text-center">
                <h3>Projekty w których uczestniczysz</h3>
            </div>
            <c:forEach items="${projects}" var="project">
                <div class="col-md-4 col-sm-6">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                <a href="<spring:url value="/projects/${project.id}"/>">${project.name}</a>
                            </h3>
                        </div>
                        <div class="panel-body">
                            <p>Utworzono <fmt:formatDate value="${project.creationDate}" dateStyle="long"/></p>
                            <c:if test="${not empty project.lastModifiedTask}">
                                <p>Ostatnio zmodyfikowany plik ${project.lastModifiedTask.lastModifiedFile.name}
                                    w dniu <fmt:formatDate
                                            value="${project.lastModifiedTask.lastModifiedFile.latestVersion.saveDate}"
                                            dateStyle="long"/>
                                    przez ${project.lastModifiedTask.lastModifiedFile.latestVersion.author.fullName}
                                    w zadaniu ${project.lastModifiedTask.name}</p>
                            </c:if>
                        </div>
                        <div class="panel-footer">
                            <span class="glyphicon glyphicon-user"></span> ${project.numberOfParticipants} ${project.numberOfParticipants eq 1 ? 'uczestnik ' : 'uczestników '}
                            <span class="glyphicon glyphicon-tasks"></span> ${project.numberOfTasks} ${project.numberOfTasks eq 1 ? 'zadanie ' : 'zadań '}
                            <span class="glyphicon glyphicon-file"></span> ${project.numberOfFiles} ${project.numberOfFiles eq 1 ? 'plik ' : 'plików '}
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="row col-md-8 col-md-offset-2">
                <div class="alert alert-info text-center">
                    <strong>Brak projektów do wyświetlania.</strong> Utwórz nowy projekt, lub poproś o dodanie do
                    istniejącego, aby rozpocząć pracę.
                </div>
            </div>
        </c:otherwise>
    </c:choose>

</div>

</body>
</html>