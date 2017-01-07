<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%--@elvariable id="error" type="java.lang.String"--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <%@ include file="css.jsp" %>
    <title>System obiegu dokumentów - Logowanie</title>
</head>
<body>

<div class="page-header text-center">
    <h1>
        <img src="<spring:url value="/images/logo.png"/>" width="40px" height="40px">
        System obiegu dokumentów
        <small>Logowanie</small>
    </h1>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Zaloguj się</h3>
                </div>
                <div class="panel-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">
                            <spring:message code="AbstractUserDetailsAuthenticationProvider.badCredentials"/>
                            <br/>
                        </div>
                    </c:if>
                    <form action="<c:url value="/login"/>" method="post">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="Nazwa użytkownika" name='username'
                                       type="text">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Hasło" name='password' type="password"
                                       value="">
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input class="btn btn-lg btn-primary btn-block" type="submit" value="Zaloguj się">
                        </fieldset>
                    </form>
                    <a href="<spring:url value="/register" />" class="btn btn-info btn-block">Rejestracja</a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>