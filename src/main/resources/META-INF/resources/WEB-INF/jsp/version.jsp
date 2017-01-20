<%--@elvariable id="previousVersion" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.VersionInfoDTO"--%>
<%--@elvariable id="version" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.VersionInfoDTO"--%>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%--@elvariable id="currentUser" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.UserInfoDTO"--%>
<%--@elvariable id="task" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.TaskInfoDTO"--%>
<%--@elvariable id="file" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.FileMetadataDTO"--%>
<%--@elvariable id="diffData" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.DiffData"--%>
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
        <small>Wersja ${version.versionString}</small>
    </h1>
</div>

<%@ include file="navbarTaskActive.jsp" %>

<div class="container-fluid">

    <div class="row">

        <h3 class="text-center">
            Zmiany wprowadzone w wersji ${version.versionString}
        </h3>


        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        Poprzednia wersja - ${previousVersion.versionString}
                    </h3>
                </div>

                <div class="panel-body">
                    <c:forEach var="i" begin="1" end="${diffData.oldContent.lines.size()}">
                        <c:set var="diffType" value="${diffData.getDiffTypeForOldVersion(i)}"/>
                        <c:choose>
                            <c:when test="${diffType eq 'INSERT'}">
                                <c:set var="rowColor" value="bg-success"/>
                            </c:when>
                            <c:when test="${diffType eq 'DELETE'}">
                                <c:set var="rowColor" value="bg-danger"/>
                            </c:when>
                            <c:when test="${diffType eq 'MODIFICATION'}">
                                <c:set var="rowColor" value="bg-info"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="rowColor" value=""/>
                            </c:otherwise>
                        </c:choose>
                        <div class="row ${rowColor}">
                            <div class="col-md-1">
                                <b>${i}</b>
                            </div>
                            <div class="col-md-11">
                                <c:choose>
                                    <c:when test="${diffType eq 'INSERT'}">
                                        <ins>${diffData.oldContent.getLine(i)}</ins>
                                    </c:when>
                                    <c:when test="${diffType eq 'DELETE'}">
                                        <del>${diffData.oldContent.getLine(i)}</del>
                                    </c:when>
                                    <c:otherwise>
                                        ${diffData.oldContent.getLine(i)}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        Obecna wersja - ${version.versionString}
                    </h3>
                </div>

                <div class="panel-body">
                    <c:forEach var="i" begin="1" end="${diffData.newContent.lines.size()}">
                        <c:set var="diffType" value="${diffData.getDiffTypeForNewVersion(i)}"/>
                        <c:choose>
                            <c:when test="${diffType eq 'INSERT'}">
                                <c:set var="rowColor" value="bg-success"/>
                            </c:when>
                            <c:when test="${diffType eq 'DELETE'}">
                                <c:set var="rowColor" value="bg-danger"/>
                            </c:when>
                            <c:when test="${diffType eq 'MODIFICATION'}">
                                <c:set var="rowColor" value="bg-info"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="rowColor" value=""/>
                            </c:otherwise>
                        </c:choose>
                        <div class="row ${rowColor}">
                            <div class="col-md-1">
                                <b>${i}</b>
                            </div>
                            <div class="col-md-11">
                                <c:choose>
                                    <c:when test="${diffType eq 'INSERT'}">
                                        <ins>${diffData.newContent.getLine(i)}</ins>
                                    </c:when>
                                    <c:when test="${diffType eq 'DELETE'}">
                                        <del>${diffData.newContent.getLine(i)}</del>
                                    </c:when>
                                    <c:otherwise>
                                        ${diffData.newContent.getLine(i)}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

    </div>

</div>

</body>
</html>