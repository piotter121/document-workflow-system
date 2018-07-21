<%--@elvariable id="version" type="pl.edu.pw.ee.pyskp.documentworkflow.dtos.VersionInfoDTO"--%>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<%--@elvariable id="taskId" type="java.lang.String"--%>
<%--@elvariable id="projectId" type="java.lang.String"--%>
<%--@elvariable id="fileId" type="java.lang.String"--%>
<%--@elvariable id="fileName" type="java.lang.String"--%>
<%--@elvariable id="diffData" type="pl.edu.pw.ee.pyskp.documentworkflow.dtos.DiffData"--%>
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
        <small>Wersja ${version.versionString}</small>
    </h1>
</div>

<%@ include file="navbarTaskActive.jsp" %>

<div class="container-fluid">

    <div class="row">

        <div class="text-center">
            <h3>
                Zmiany wprowadzone w wersji ${version.versionString}
            </h3>
            <blockquote>
                <p>${version.message}</p>
                <footer>
                    ${version.author.fullName} w dniu
                    <fmt:formatDate value="${version.saveDate}" type="both" dateStyle="long" timeStyle="short"/>
                </footer>
            </blockquote>
        </div>

        <c:if test="${not empty version.previousVersionString}">
            <div class="col-md-6">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            Poprzednia wersja - ${version.previousVersionString}
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
        </c:if>

        <c:choose>
            <c:when test="${empty version.previousVersionString}">
                <c:set var="newContentColWidth" value="col-md-offset-1 col-md-10"/>
            </c:when>
            <c:otherwise>
                <c:set var="newContentColWidth" value="col-md-6"/>
            </c:otherwise>
        </c:choose>

        <div class="${newContentColWidth}">
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