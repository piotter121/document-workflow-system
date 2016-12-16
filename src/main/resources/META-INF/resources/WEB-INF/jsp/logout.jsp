<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
    <title>System obiegu dokumentów</title>
</head>
<body>
<section>
    <div class="jumbotron">
        <div class="container">
            <h1>System obiegu dokumentów</h1>
            <p>Wylogowano pomyślnie</p>
        </div>
    </div>
</section>
<div class="container">
    <p>Wróć do <a href="<spring:url value="/"/>">strony głównej</a></p>
    <p>lub</p>
    <p>ponownie <a href="<spring:url value="/login"/>">zaloguj się</a></p>
</div>
</body>
</html>