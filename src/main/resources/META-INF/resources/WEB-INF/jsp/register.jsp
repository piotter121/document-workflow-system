<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.html" %>
    <title>Zarejestruj się</title>
</head>
<body>
<section>
    <div class="jumbotron">
        <div class="container">
            <h1>System obiegu dokumentów</h1>
            <p>Rejestracja nowego użytkownika</p>
        </div>
    </div>
</section>
<section class="container">
    <form:form modelAttribute="newUser" class="form-horizontal">
        <fieldset>
            <legend>
                <spring:message code="register.form.legend"/>
            </legend>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="login">
                    <spring:message code="register.form.username.label"/>
                </label>
                <div class="col-lg-10">
                    <form:input path="login" id="login" type="text" class="form:input-large"/>
                    <p><form:errors path="login" cssClass="text-danger" /></p>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="email">
                    <spring:message code="register.form.email.label"/>
                </label>
                <div class="col-lg-10">
                    <form:input path="email" id="email" type="text" class="form:input-large"/>
                    <p><form:errors path="email" cssClass="text-danger" /></p>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="firstName">
                    <spring:message code="register.form.firstName.label"/>
                </label>
                <div class="col-lg-10">
                    <form:input path="firstName" id="firstName" type="text" class="form:input-large"/>
                    <p><form:errors path="firstName" cssClass="text-danger" /></p>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="lastName">
                    <spring:message code="register.form.lastName.label"/>
                </label>
                <div class="col-lg-10">
                    <form:input path="lastName" id="lastName" type="text" class="form:input-large"/>
                    <p><form:errors path="lastName" cssClass="text-danger" /></p>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="password">
                    <spring:message code="register.form.password.label"/>
                </label>
                <div class="col-lg-10">
                    <form:input path="password" id="password" type="password" class="form:input-large"/>
                    <p><form:errors path="password" cssClass="text-danger" /></p>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-lg-2 col-lg-2" for="passwordRepeated">
                    <spring:message code="register.form.passwordRepeated.label"/>
                </label>
                <div class="col-lg-10">
                    <form:input path="passwordRepeated" id="passwordRepeated" type="password" class="form:input-large"/>
                    <p><form:errors path="passwordRepeated" cssClass="text-danger" /></p>
                </div>
            </div>
            <div class="form-group">
                <div class="col-lg-offset-2 col-lg-10">
                    <input type="submit" id="btnRegister" class="btn btn-primary" value="Zarejestruj"/>
                </div>
            </div>
        </fieldset>
    </form:form>
</section>
</body>
</html>