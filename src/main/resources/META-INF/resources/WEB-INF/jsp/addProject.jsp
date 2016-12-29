<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.html" %>
    <title>System obiegu dokumentów - stwórz projekt</title>
</head>
<body>
<section>
    <div class="jumbotron">
        <div class="container">
            <h1>System obiegu dokumentów</h1>
            <p>Stwórz projekt</p>
        </div>
        <form action="<c:url value="/logout" />" method="post">
            <input type="submit" value="Wyloguj" class="btn btn-danger btn-mini pull-right"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</section>
<section class="container">
    <form:form modelAttribute="newProject" class="form-horizontal">
        <fieldset>
            <legend>
                <spring:message code="addProject.form.legend"/>
            </legend>

            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="name">
                    <spring:message code="addProject.form.name.label"/>
                </label>
                <div class="col-lg-10">
                    <form:input path="name" id="name" type="text" class="form:input-large"/>
                    <p><form:errors path="name" cssClass="text-danger"/></p>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="description">
                    <spring:message code="addProject.form.description.label"/>
                </label>
                <div class="col-lg-10">
                    <form:textarea path="description" id="description" rows="4"/>
                    <p><form:errors path="name" cssClass="text-danger"/></p>
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
</section>
</body>
</html>