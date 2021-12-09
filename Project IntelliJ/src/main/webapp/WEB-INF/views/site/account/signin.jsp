<!-- login -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <form method="post" action="#">
                    <div class="mb-3">
                        <label for="signinEmail" class="form-label text-light fs-1-5 fw-light">Email</label>
                        <input type="email" class="form-control rounded-pill fs-1-5 fw-light" id="signinEmail" placeholder="Digita una email...">
                    </div>
                    <div class="mb-3">
                        <label for="signinPassword" class="form-label text-light fs-1-5 fw-light">Password</label>
                        <input type="password" class="form-control rounded-pill fs-1-5 fw-light" id="signinPassword" aria-describedby="passwordHelpSignin" placeholder="Digita una password...">
                        <div id="passwordHelpSignin" class="form-text text-light">
                            La password deve essere compresa tra 8-32 caratteri, deve contenere una maiuscola e un numero.
                        </div>
                    </div>
                    <hr>
                    <button type="submit" class="btn btn-light rounded-pill text-dark fs-1-5">Entra</button>
                    <a href="${pageContext.request.contextPath}/account/signup" class="text-light">Non hai un account? Registrati.</a>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="../../partial/site/footer.jsp"/>
</body>
</html>