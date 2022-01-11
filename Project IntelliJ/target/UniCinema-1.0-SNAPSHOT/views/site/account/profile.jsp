<!-- profile -->
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="it">
<head>
    <jsp:include page="../../partial/head.jsp">
        <jsp:param name="styles" value="bootstrap,profile"/>
        <jsp:param name="scripts" value="paginator,profile,alert"/>
    </jsp:include>
</head>
<body class="bg-img">
<jsp:include page="../../partial/site/header.jsp"/> <!-- importo la navbar -->
<div class="d-flex flex-column justify-content-center" style="min-height: 100vh;">
    <script>
        let accountId = ${account.id};
    </script>
    <div class="container profile-bg-div bg-dark mt-5 mb-5">
        <p class="text-light fs-1 fw-light profile-bg-div-title">Profilo</p>
        <div class="emp-profile">
            <form id="profile-edit-form" novalidate>
                <div class="row justify-content-evenly">
                    <div class="col-md-10">
                        <div class="row">
                            <div class="col">
                                <div class="profile-head">
                                    <h5>
                                        <span id="profile-info-title-firstname">${account.firstname}&nbsp;</span><span id="profile-info-title-lastname">${account.lastname}</span>
                                    </h5>
                                    <c:choose>
                                        <c:when test="${not account.administrator}">
                                            <h6>Cliente</h6>
                                        </c:when>
                                        <c:otherwise>
                                            <h6>Amministratore</h6>
                                        </c:otherwise>
                                    </c:choose>
                                    <p class="profile-rating">RECENSIONI TOTALI: <span>${reviewCount}</span></p>
                                </div>
                            </div>
                            <div class="col-md-2 profile-edit-btn-div" method="post" style="text-align: right;">
                                <div class="col">
                                    <!-- Modifica/Conferma -->
                                    <input type="button" class="btn btn-dark rounded-pill" id="profile-edit-confirm" name="btnAddMore" value="Modifica" style="width: 100%;">
                                </div>
                                <div class="col mt-1">
                                    <!-- Compare solo se clicco su modifica -->
                                    <input type="button" class="btn btn-dark rounded-pill" id="profile-edit-cancel" name="btnAddMore" value="Annulla" style="width: 100%; display: none;">
                                </div>
                            </div>
                        </div>
                        <div class="row profile-nav">
                            <ul class="nav nav-tabs" id="myTab" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active" id="list-info-list" data-bs-toggle="list" role="tab" href="#list-info" aria-controls="list-info">Info</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" id="list-purchases-list" data-bs-toggle="list" role="tab" href="#list-purchases" aria-controls="list-purchases">Ordini</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="row justify-content-evenly">
                    <div class="col-md-10">
                        <div class="tab-content profile-tab" id="myTabContent">
                            <div class="tab-pane fade show active" id="list-info" role="tabpanel" aria-labelledby="list-info-list">
                                <%@ include file="../../partial/site/alert.jsp"%>
                                <div class="row">
                                    <div class="col">
                                        <label>User Id</label>
                                    </div>
                                    <div class="col-md-7">
                                        <p>${account.id}</p>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <label>Name</label>
                                    </div>
                                    <div class="col-md-7">
                                        <p class="profile-info-label" id="profile-info-firstlast-name">
                                            <span id="profile-info-firstname">${account.firstname}</span> <span id="profile-info-lastname">${account.lastname}</span>
                                        </p>
                                        <div class="input-group input-group-sm mb-3 profile-info-edit" style="display: none">
                                            <input type="text" name="firstname" aria-label="Firstname" class="form-control" placeholder="Nome">
                                            <input type="text" name="lastname" aria-label="Lastname" class="form-control" placeholder="Cognome">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <label>Email</label>
                                    </div>
                                    <div class="col-md-7">
                                        <p class="profile-info-label" id="profile-info-email">${account.email}</p>
                                        <div class="input-group input-group-sm mb-3 profile-info-edit" style="display: none">
                                            <input type="text" name="email" aria-label="Email" class="form-control" placeholder="Email">
                                        </div>
                                    </div>
                                </div>
                                <div class="row profile-info-edit" style="display: none">
                                    <div class="col">
                                        <label>Password nuova</label>
                                    </div>
                                    <div class="col-md-7">
                                        <div class="input-group input-group-sm mb-3">
                                            <input type="password" name="password" aria-label="NewPswrd" class="form-control" placeholder="Password nuova" id="signupPassword" aria-describedby="passwordHelpSignup" minlength="8" maxlength="32" pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z;:=_+^#$@!%*?&àèìòù\d]{8,32}$">
                                            <div id="passwordHelpSignup" class="form-text" style="color: #495057">
                                                La password deve essere compresa tra 8-32 caratteri, deve contenere una maiuscola e un numero.
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="tab-pane fade" id="list-purchases" role="tabpanel" aria-labelledby="list-purchases-list">
                                <div id="accordion" class="mb-2">

                                </div>
                                <jsp:include page="../../partial/site/paginator/paginator.jsp"/>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="../../partial/site/footer.jsp"/>
</body>
</html>
