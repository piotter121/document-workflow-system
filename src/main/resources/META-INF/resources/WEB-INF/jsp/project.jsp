<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%@ include file="head.html" %>
    <title>System obiegu dokumentów - ${project.name}</title>
</head>
<body>
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

<div class="container">
    <div class="row">
        <div class="col-md-9">
            <legend>
                Zadania przypisane do projektu
            </legend>
            <c:if test="${currentUser.login eq project.administrator.login}">
                <div class="btn-group">
                    <a href="<spring:url value="/projects/${project.id}/tasks/add"/>" class="btn btn-primary">
                        Dodaj nowe zadanie
                    </a>
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
                            <a href="<spring:url value="/projects/${task.projectId}/tasks/${task.id}"/>"
                               class="btn btn-info" role="button">
                                Szczegóły
                            </a>
                            <c:if test="${currentUser.login eq project.administrator.login}">
                                <a href="<spring:url value="/projects/${task.projectId}/tasks/${task.id}/edit"/>"><span
                                        class="glyphicons glyphicons-edit"></span></a>
                                <a data-toggle="modal" href="#confirmDelete">
                                    <span class="glyphicons glyphicons-delete"></span>
                                </a>
                                <div class="modal fade" id="confirmDelete" role="dialog">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h4 class="modal-title">Potwierdź usunięcie zadania</h4>
                                            </div>
                                            <div class="modal-body">
                                                <div class="alert alert-warning">
                                                    <strong>Ostrzeżenie!</strong> Usunięcie zadania jest nieodwracalne!
                                                </div>
                                                <p class="text-info">Czy na pewno chcesz usunąć zadanie wraz ze
                                                    wszystkimi plikami?</p>
                                            </div>
                                            <div class="modal-footer">
                                                <form method="DELETE"
                                                      action="<c:url value="/projects/${task.projectId}/tasks/${task.id}"/>">
                                                    <input type="submit" class="btn btn-danger" value="Usuń"/>
                                                </form>
                                                <button type="button" class="btn btn-default" data-dismiss="modal">
                                                    Anuluj
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
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
                            <p class="list-group-item-text">Nazwa</p>
                            <h4 class="list-group-item-heading">${project.name}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-text">Opis</p>
                            <h4 class="list-group-item-heading">${project.description}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-text">Administrator</p>
                            <h4 class="list-group-item-heading">${project.administrator.fullName}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-text">Data utworzenia</p>
                            <h4 class="list-group-item-heading">
                                <fmt:formatDate pattern="dd.MM.yyyy K:m"
                                                value="${project.creationDate}"/>
                            </h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-text">Data ostatniej modyfikacji</p>
                            <h4 class="list-group-item-heading">
                                <fmt:formatDate pattern="dd.MM.yyyy K:m"
                                                value="${project.lastModified}"/></h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>