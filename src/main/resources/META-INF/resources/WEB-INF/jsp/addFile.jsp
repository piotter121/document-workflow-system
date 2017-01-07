<%--@elvariable id="task" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.TaskInfoDTO"--%>
<%--@elvariable id="currentUser" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.UserInfoDTO"--%>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <%@ include file="css.jsp" %>
    <title>System obiegu dokumentów - dodaj nowy plik</title>
</head>
<body>

<div class="page-header">
    <h1>
        ${task.name}
        <small>Dodawanie nowego pliku</small>
    </h1>
</div>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li><a href="<spring:url value="/"/>"><span class="glyphicon glyphicon-home"></span> Strona główna</a></li>
            <li><a href="<spring:url value="/projects"/>">Projekty</a></li>
            <li class="active"><a href="<spring:url value="/tasks"/>">Zadania</a></li>
        </ul>

        <p class="navbar-text">Zalogowany jako ${currentUser.fullName}</p>

        <form class="navbar-form navbar-right" action="<c:url value="/logout" />" method="post">
            <input type="submit" value="Wyloguj" class="btn btn-danger btn-mini"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</nav>

<div class="container-fluid">

    <div class="col-md-10 col-md-offset-1">
        <form:form method="post" modelAttribute="newFileForm" class="form-horizontal"
                   enctype="multipart/form-data">
            <fieldset>
                <div class="form-group">
                    <label class="control-label col-md-2" for="name">
                        Wyświetlana nazwa pliku
                    </label>
                    <div class="col-md-5">
                        <form:input path="name" id="name" type="text" class="form-control"/>
                        <p><form:errors path="name" cssClass="text-danger"/></p>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2" for="description">
                        Dodatkowy opis
                    </label>
                    <div class="col-md-5">
                        <form:textarea path="description" id="description" rows="4" cssClass="form-control"/>
                        <p><form:errors path="description" cssClass="text-danger"/></p>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2" for="file">
                        Plik
                    </label>
                    <div class="col-md-5">
                        <input id="file" type="file" name="file">
                        <p><form:errors path="file" cssClass="text-danger"/></p>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2" for="versionString">
                        Numer inicjalnej wersji pliku
                    </label>
                    <div class="col-md-5">
                        <form:input path="versionString" id="versionString" type="text" cssClass="form-control"/>
                        <p><form:errors path="versionString" cssClass="text-danger"/></p>
                    </div>
                </div>

                <div class="form-group">
                    <label class="control-label col-md-2" for="versionMessage">
                        Informacje o tym co zostało już w pliku zrobione
                    </label>
                    <div class="col-md-5">
                        <form:textarea path="versionMessage" id="versionMessage" rows="4" cssClass="form-control"/>
                        <p><form:errors path="versionMessage" cssClass="text-danger"/></p>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-lg-offset-2 col-lg-5">
                        <input type="submit" id="btnAdd" class="btn btn-primary" value="Dodaj"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </div>
                </div>
            </fieldset>
        </form:form>
    </div>
</div>

</body>
</html>