<!-- login -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it">
<head>
    <jsp:include page="../../partial/head.jsp">
        <jsp:param name="styles" value="bootstrap,signin"/>
    </jsp:include>
</head>
<body class="bg-img">
    <jsp:include page="../../partial/site/header.jsp"/> <!-- importo la navbar -->
    <div class="d-flex flex-column justify-content-center" style="min-height: 100vh;">
        <div class="container container-form bg-dark border-radius-1 shadow">
            <div>
                <p class="text-light fs-1 fw-light">Login</p>
                <hr>
                <c:if test="${not empty alert}">
                    <%@ include file="../../partial/site/alert.jsp"%>
                </c:if>
                <form method="post" action="${pageContext.request.contextPath}/account/signin" class="needs-validation" novalidate>
                    <div class="mb-3">
                        <label for="signinEmail" class="form-label text-light fs-1-5 fw-light">Email</label>
                        <input name="email" type="email" class="form-control rounded-pill fs-1-5 fw-light" id="signinEmail" placeholder="Digita una email..." required minlength="6" maxlength="30" pattern="^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$">
                        <div class="custom-feedback"></div>
                    </div>
                    <div class="mb-3">
                        <label for="signinPassword" class="form-label text-light fs-1-5 fw-light">Password</label>
                        <input name="password" type="password" class="form-control rounded-pill fs-1-5 fw-light" id="signinPassword" aria-describedby="passwordHelpSignin" placeholder="Digita una password..." required minlength="8" maxlength="32" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z;:=_+^#$@!%*?&àèìòù\d]{8,32}$">
                        <div class="custom-feedback"></div>
                    </div>
                    <hr>
                    <button type="submit" class="btn btn-light rounded-pill text-dark fs-1-5">Entra</button>
                    <a href="${pageContext.request.contextPath}/signup" class="text-light">Non hai un account? Registrati.</a>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="../../partial/site/footer.jsp"/>
</body>
</html>