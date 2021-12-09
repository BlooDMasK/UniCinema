<!-- register -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
  <jsp:include page="../../partial/head.jsp">
    <jsp:param name="styles" value="bootstrap,signup"/>
  </jsp:include>
</head>
<body class="bg-img">
<jsp:include page="../../partial/site/header.jsp"/> <!-- importo la navbar -->
<div class="d-flex flex-column justify-content-center" style="min-height: 100vh;">
  <div class="container container-form bg-dark border-radius-1 shadow">
    <div>
      <p class="text-light fs-1 fw-light">Registrazione</p>
      <hr>
      <form method="post" action="#">

        <div class="mb-3">
          <label for="signupFirstname" class="form-label text-light fs-1-5 fw-light">Nome</label>
          <input type="text" class="form-control rounded-pill fs-1-5 fw-light" id="signupFirstname" placeholder="Digita un nome...">
        </div>
        <div class="mb-3">
          <label for="signupLastname" class="form-label text-light fs-1-5 fw-light">Cognome</label>
          <input type="text" class="form-control rounded-pill fs-1-5 fw-light" id="signupLastname" placeholder="Digita un cognome...">
        </div>
        <div class="mb-3">
          <label for="signupEmail" class="form-label text-light fs-1-5 fw-light">Email</label>
          <input type="email" class="form-control rounded-pill fs-1-5 fw-light" id="signupEmail" placeholder="Digita una email...">
        </div>
        <div class="mb-3">
          <label for="signupPassword" class="form-label text-light fs-1-5 fw-light">Password</label>
          <input type="password" class="form-control rounded-pill fs-1-5 fw-light" id="signupPassword" aria-describedby="passwordHelpSignup" placeholder="Digita una password...">
          <div id="passwordHelpSignup" class="form-text text-light">
            La password deve essere compresa tra 8-32 caratteri, deve contenere una maiuscola e un numero.
          </div>
        </div>
        <hr>
        <button type="submit" class="btn btn-light rounded-pill text-dark fs-1-5">Registrati</button>
        <a href="${pageContext.request.contextPath}/account/signin" class="text-light">Hai già un account? Effettua il login.</a>
      </form>
    </div>
  </div>
</div>
<jsp:include page="../../partial/site/footer.jsp"/>
</body>
</html>
