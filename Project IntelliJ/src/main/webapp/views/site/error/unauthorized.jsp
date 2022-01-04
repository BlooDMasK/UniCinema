<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <jsp:include page="../../partial/head.jsp">
        <jsp:param name="styles" value=""/>
    </jsp:include>
</head>
<body class="bg-error">
<div class="flex-wrapper d-flex flex-column justify-content-center">
    <div class="container text-center" style="margin-bottom: 15rem;">
        <p class="fs-1">Errore 401</p>
        <p class="fs-3">Non autorizzato</p>
        <p class="fs-5">Mettetevi comodi, lo spettacolo sta per cominciare</p>
        <a class="btn btn-dark rounded-3" href="${pageContext.request.contextPath}/pages">Homepage</a>
    </div>
</div>
</body>