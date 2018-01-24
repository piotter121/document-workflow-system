<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%--@elvariable id="currentUser" type="pl.edu.pw.ee.pyskp.documentworkflow.dtos.UserInfoDTO"--%>
<%--@elvariable id="projectId" type="java.lang.String"--%>
<%--@elvariable id="taskId" type="java.lang.String"--%>
<%--@elvariable id="fileId" type="java.lang.String"--%>
<%--@elvariable id="fileName" type="java.lang.String"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="css.jsp" %>
    <title>System obiegu dokumentów - dodawanie nowej wersji pliku</title>
</head>
<body>

<div class="page-header">
    <h1>
        <img src="<spring:url value="/images/logo.png"/>" width="40px" height="40px">
        <a href="<spring:url value="/projects/${projectId}/tasks/${taskId}/files/${fileId}"/>">
            ${fileName}
        </a>
        <small>Dodawanie nowej wersji pliku</small>
    </h1>
</div>

<%@ include file="navbarTaskActive.jsp" %>

<div class="container-fluid">

    <%--@elvariable id="newVersionForm" type="pl.edu.pw.ee.pyskp.documentworkflow.dtos.NewVersionForm"--%>
    <form:form method="post" modelAttribute="newVersionForm" cssClass="form-horizontal" enctype="multipart/form-data">

        <fieldset>

            <legend>
                Uzupełnij poniższe pola, aby dodać nową wersję pliku
            </legend>

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
                    Numer wersji pliku
                </label>
                <div class="col-md-5">
                    <form:input path="versionString" id="versionString" type="text" cssClass="form-control"/>
                    <p><form:errors path="versionString" cssClass="text-danger"/></p>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2" for="message">
                    Wiadomość kontrolna
                </label>
                <div class="col-md-5">
                    <form:textarea path="message" id="message" cssClass="form-control"/>
                    <p><form:errors path="message" cssClass="text-danger"/></p>
                </div>
            </div>

        </fieldset>

        <div class="col-md-offset-2 col-md-5">
            <input type="submit" id="btnAdd" class="btn btn-primary" value="Zapisz"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <a class="btn btn-default"
               href="<spring:url value="/projects/${projectId}/tasks/${taskId}/files/${fileId}"/>">
                Zrezygnuj
            </a>
        </div>

    </form:form>

</div>

</body>
</html>