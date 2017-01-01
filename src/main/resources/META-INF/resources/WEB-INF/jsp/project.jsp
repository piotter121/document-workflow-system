<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%@ include file="header.html" %>
    <title>System obiegu dokumentów - ${project.name}</title>
</head>
<body>
<section>
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
</section>
<section class="container">
    <div class="row">
        <div class="col-md-9">
            <legend>
                Zadania przypisane do projektu
            </legend>
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>Nazwa</th>
                    <th>Opis</th>
                    <th>Imię i nazwisko administratora</th>
                    <th>Liczba przypisanych plików</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${project.tasks}" var="task">
                    <tr>
                        <td>${task.name}</td>
                        <td>${task.description}</td>
                        <td>${task.administrator.fullName}</td>
                        <td>${task.numberOfFiles}</td>
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
                            <h4 class="list-group-item-heading">${project.creationDate}</h4>
                        </div>
                        <div class="list-group-item">
                            <p class="list-group-item-text">Data ostatniej modyfikacji</p>
                            <h4 class="list-group-item-heading">${project.lastModified}</h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>