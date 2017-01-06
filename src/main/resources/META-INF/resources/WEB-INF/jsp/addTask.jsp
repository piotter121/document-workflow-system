<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%@ include file="bootstrap.jsp" %>
    <title>System obiegu dokumentów - stwórz zadanie</title>
</head>
<body>

<div class="jumbotron">
    <div class="container">
        <h1>System obiegu dokumentów</h1>
        <p>Stwórz zadanie</p>
        <form action="<c:url value="/logout" />" method="post">
            <input type="submit" value="Wyloguj" class="btn btn-danger btn-mini pull-right"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>

<div class="container">
    <form:form modelAttribute="newTaskForm" class="form-horizontal">
        <fieldset>
            <legend>
                <spring:message code="addTask.form.legend"/>
            </legend>

            <div class="form-group">
                <label class="control-label col-md-2" for="name">
                    <spring:message code="addTask.form.name.label"/>
                </label>
                <div class="col-md-5">
                    <form:input path="name" id="name" type="text" class="form-control"/>
                    <p><form:errors path="name" cssClass="text-danger"/></p>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2" for="description">
                    <spring:message code="addTask.form.description.label"/>
                </label>
                <div class="col-md-5">
                    <form:textarea path="description" id="description" rows="4" cssClass="form-control"/>
                    <p><form:errors path="description" cssClass="text-danger"/></p>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2" for="administratorEmail">
                    <spring:message code="addTask.form.administrator.label"/>
                </label>
                <div class="col-md-5">
                    <form:input path="administratorEmail" id="administratorEmail" cssClass="form-control"/>
                    <p><form:errors path="administratorEmail" cssClass="text-danger"/></p>
                </div>
            </div>

            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <input type="submit" id="btnAdd" class="btn btn-primary" value="Stwórz"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </div>
            </div>
        </fieldset>
    </form:form>
</div>

</body>
</html>