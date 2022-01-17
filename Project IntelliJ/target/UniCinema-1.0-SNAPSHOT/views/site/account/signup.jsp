<!-- register -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
      <c:if test="${not empty alert}">
        <%@ include file="../../partial/site/alert.jsp"%>
      </c:if>
      <form method="post" action="${pageContext.request.contextPath}/signup" class="needs-validation" novalidate>

        <div class="mb-3">
          <label for="signupFirstname" class="form-label text-light fs-1-5 fw-light">Nome</label>
          <input name="firstname" type="text" class="form-control rounded-pill fs-1-5 fw-light" id="signupFirstname" placeholder="Digita un nome..." required minlength="3" maxlength="25" pattern="^[A-Za-zàèìòù]{3,25}$">
          <div class="invalid-feedback">
            Il nome deve essere di 3-25 caratteri.
          </div>
        </div>
        <div class="mb-3">
          <label for="signupLastname" class="form-label text-light fs-1-5 fw-light">Cognome</label>
          <input name="lastname" type="text" class="form-control rounded-pill fs-1-5 fw-light" id="signupLastname" placeholder="Digita un cognome..." required minlength="3" maxlength="25" pattern="^[A-Za-zàèìòù]{3,25}$">
          <div class="invalid-feedback">
            Il cognome deve essere di 3-25 caratteri.
          </div>
        </div>
        <div class="mb-3">
          <label for="signupEmail" class="form-label text-light fs-1-5 fw-light">Email</label>
          <input name="email" type="email" class="form-control rounded-pill fs-1-5 fw-light" id="signupEmail" placeholder="Digita una email..." required minlength="6" maxlength="30" pattern="^[a-zA-Z0-9.!#$%&'*+/=?^_{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$">
          <div class="invalid-feedback">
            L'email deve essere di 6-30 caratteri.
          </div>
        </div>
        <div class="mb-3">
          <label for="signupPassword" class="form-label text-light fs-1-5 fw-light">Password</label>
          <input name="password" type="password" class="form-control rounded-pill fs-1-5 fw-light" id="signupPassword" aria-describedby="passwordHelpSignup" placeholder="Digita una password..." required minlength="8" maxlength="32" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z;:=_+^#$@!%*?&àèìòù\d]{8,32}$">
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
