<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%@ include file="bootstrap.jsp" %>
    <title>System obiegu dokumentów - ${project.name}</title>
</head>
<body>
<%--@elvariable id="project" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.ProjectInfoDTO"--%>
<%--@elvariable id="currentUser" type="pl.edu.pw.ee.pyskp.documentworkflow.dto.UserInfoDTO"--%>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.DefaultCsrfToken"--%>
<div class="jumbotron">
    <div class="container">
        <h1>System obiegu dokumentów</h1>
        <p>Szczegóły projektu</p>
        <form action="<c:url value="/logout" />" method="post">
            <input type="submit" value="Wyloguj" class="btn btn-danger btn-mini pull-right"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-9">
            <div class="btn-group">
                <c:if test="${currentUser eq project.administrator}">
                    <a href="<spring:url value="/projects/${project.id}/tasks/add"/>" class="btn btn-primary">
                        <span class="glyphicon glyphicon-plus"></span>
                        Dodaj nowe zadanie
                    </a>
                </c:if>

                <a href="mailto:${project.administrator.email}" class="btn btn-primary">
                    <span class="glyphicon glyphicon-envelope"></span>
                    Wyślij wiadomość do administratora projektu
                </a>
            </div>
            <%--@elvariable id="delete" type="java.lang.String"--%>
            <c:if test="${delete eq 'success'}">
                <div class="alert alert-success">
                    Pomyślnie usunięto zadanie
                </div>
            </c:if>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>Nazwa</th>
                    <th>Imię i nazwisko administratora</th>
                    <th>Liczba przypisanych plików</th>
                    <th>Data modyfikacji</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${project.tasks}" var="task">
                    <tr>
                        <td>${task.name}</td>
                        <td>${task.administrator.fullName}</td>
                        <td>${task.numberOfFiles}</td>
                        <td>${task.modificationDate}</td>
                        <td>
                            <div class="button-group">
                                <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">
                                    <span class="glyphicon glyphicon-cog"></span> Opcje <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu">
                                    <c:if test="${task.participants.contains(currentUser) or task.administrator eq currentUser}">
                                        <li><a href="<spring:url value="/projects/${task.projectId}/tasks/${task.id}"/>"
                                           class="">
                                            <span class="glyphicon glyphicon-info-sign"></span> Szczegóły
                                        </a></li>
                                    </c:if>
                                    <c:if test="${currentUser eq project.administrator}">
                                        <li><a href="<spring:url value="/projects/${task.projectId}/tasks/${task.id}/edit"/>">
                                            <span class="glyphicon glyphicon-edit"></span> Edytuj
                                        </a></li>
                                        <li><a data-toggle="modal" href="#confirmDelete">
                                            <span class="glyphicon glyphicon-remove"></span> Usuń
                                        </a></li>
                                        <div class="modal fade" id="confirmDelete" role="dialog">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h4 class="modal-title">Potwierdź usunięcie zadania</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="alert alert-warning">
                                                            <strong>Ostrzeżenie!</strong> Usunięcie zadania jest
                                                            nieodwracalne!
                                                        </div>
                                                        <p class="text-info">
                                                            Czy na pewno chcesz usunąć zadanie wraz ze
                                                            wszystkimi plikami?
                                                        </p>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <form method="post"
                                                              action="<spring:url value="/projects/${project.id}/tasks/${task.id}"/>">
                                                            <input class="btn btn-danger" type="submit" value="Usuń"/>
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                   value="${_csrf.token}"/>
                                                            <input type="hidden" name="_method" value="delete"/>
                                                            <button type="button" class="btn btn-default"
                                                                    data-dismiss="modal">
                                                                Anuluj
                                                            </button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>
                                </ul>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-md-3">
            <div class="panel panel-info">
                <div class="panel-heading">Szczegóły projektu</div>
                <div class="panel-body">
                    <div class="list-group">
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Nazwa</p>
                            <h4 class="list-group-item-text">${project.name}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Opis</p>
                            <h4 class="list-group-item-text">${project.description}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Administrator</p>
                            <h4 class="list-group-item-text">${project.administrator.fullName}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Data utworzenia</p>
                            <h4 class="list-group-item-text">
                                <fmt:formatDate pattern="dd.MM.yyyy K:mm" value="${project.creationDate}"/>
                            </h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-heading">Data ostatniej modyfikacji</p>
                            <h4 class="list-group-item-text">
                                <fmt:formatDate pattern="dd.MM.yyyy K:mm" value="${project.lastModified}"/>
                            </h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>