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
    <title>System obiegu dokumentów - dodawanie nowej wersji pliku</title>
</head>
<body>

<div class="page-header">
    <h1>
        <img src="<spring:url value="/images/logo.png"/>" width="40px" height="40px">
        <a href="<spring:url value="/projects/${task.projectId}/tasks/${task.id}/files/${file.id}"/>">
            ${file.name}
        </a>
        <small>Dodawanie nowej wersji pliku</small>
    </h1>
</div>

<%@ include file="navbarTaskActive.jsp"%>



</body>
</html>