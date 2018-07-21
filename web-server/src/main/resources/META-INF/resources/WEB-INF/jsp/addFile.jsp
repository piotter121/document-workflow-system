<%--@elvariable id="currentUser" type="pl.edu.pw.ee.pyskp.documentworkflow.dtos.UserInfoDTO"--%>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%--@elvariable id="newFileForm" type="pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewFileForm"--%>
<%--@elvariable id="projectId" type="java.lang.String"--%>
<%--@elvariable id="taskId" type="java.lang.String"--%>
<%--@elvariable id="taskName" type="java.lang.String"--%>
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
        <img src="<spring:url value="/images/logo.png"/>" width="40px" height="40px">
        <a href="<spring:url value="/projects/${projectId}/tasks/${taskId}"/>">
            ${taskName}
        </a>
        <small>Dodawanie nowego pliku</small>
    </h1>
</div>

<%@ include file="navbarTaskActive.jsp" %>

<div class="container-fluid">

    <form:form method="post" modelAttribute="newFileForm" class="form-horizontal" enctype="multipart/form-data">
        <fieldset>
            <legend>
                Proszę wypełnić poniższy formularz aby dodać nowy plik do zadania
            </legend>

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
                    <form:input path="versionString" id="versionString" type="text" rows="4" cssClass="form-control"/>
                    <p><form:errors path="versionString" cssClass="text-danger"/></p>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-offset-2 col-md-5">
                    <input type="submit" id="btnAdd" class="btn btn-primary" value="Dodaj"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <a class="btn btn-default"
                       href="<spring:url value="/projects/${projectId}/tasks/${taskId}"/>">
                        Zrezygnuj
                    </a>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>

</body>
</html>