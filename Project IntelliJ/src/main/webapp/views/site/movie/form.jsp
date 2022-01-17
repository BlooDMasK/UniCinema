<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it">
<head>
    <jsp:include page="../../partial/head.jsp">
        <jsp:param name="styles" value="bootstrap"/>
        <jsp:param name="scripts" value="form-film"/>
    </jsp:include>
</head>
<body class="bg-img">
<script>
    let formType = '${formType}';
    <c:if test="${not empty filmToJson}">
        let film = ${filmToJson};
    </c:if>
</script>
<jsp:include page="../../partial/site/header.jsp"/> <!-- importo la navbar -->
<div class="d-flex flex-column align-items-center mt-5 mb-5">
    <div class="d-flex flex-column justify-content-center" style="width:60%; min-height: 100vh;">
        <div class="container container-form bg-dark rounded-3 shadow" style="padding: 1rem 2.5rem;">
            <div>
                <p class="text-light fs-1 fw-light">
                    <c:choose>
                        <c:when test="${formType == 'add'}">
                            Aggiungi Film
                        </c:when>
                        <c:when test="${formType == 'update'}">
                            Modifica Film
                        </c:when>
                    </c:choose>
                </p>
                <hr class="text-light">
                <c:if test="${not empty alert}">
                    <%@ include file="../../partial/site/alert.jsp"%>
                </c:if>
                <form method="post" action="${pageContext.request.contextPath}/film-manager/${not empty film ? "update" : "add"}" class="needs-validation" enctype="multipart/form-data" novalidate>

                    <div class="mb-3">
                        <label for="title" class="form-label text-light fs-1-5 fw-light">Titolo</label>
                        <input name="title" type="text" class="form-control rounded-3 fs-1-5 fw-light" id="title" value="${not empty film ? film.title : ""}" placeholder="Digita un titolo..." required minlength="3" maxlength="50" pattern="^[A-Za-zàèìòù0-9 -.,]{3,50}$">
                        <div class="invalid-feedback">
                            Il titolo deve essere di 3-50 caratteri.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="length" class="form-label text-light fs-1-5 fw-light">Durata</label>
                        <div class="d-flex">
                            <input name="length" type="number" class="form-control rounded-3 fs-1-5 fw-light" id="length" value="${not empty film ? film.length : ""}" placeholder="Durata..." required min="1" max="600">
                            <span class="input-group-text">min.</span>
                        </div>
                        <div class="invalid-feedback">
                            La durata deve essere di minimo 1 minuto, massimo 600 minuti.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="date-publishing" class="form-label text-light fs-1-5 fw-light">Data di pubblicazione</label>
                        <input name="date-publishing" type="date" class="form-control rounded-3 fs-1-5 fw-light" value="${datePublishing}" id="date-publishing" required>
                        <div class="invalid-feedback">
                            Devi inserire una data.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="genre" class="form-label text-light fs-1-5 fw-light">Genere</label>
                        <select name="genre" class="form-select form-control rounded-3 fs-1-5 fw-light" id="genre" required>
                            <option value="1" selected>Animazione</option>
                            <option value="2">Avventura</option>
                            <option value="3">Azione</option>
                            <option value="4">Biografico</option>
                            <option value="5">Commedia</option>
                            <option value="6">Documentario</option>
                            <option value="7">Drammatico</option>
                            <option value="8">Fantascienza</option>
                            <option value="9">Fantasy/Fantastico</option>
                            <option value="10">Guerra</option>
                            <option value="11">Horror</option>
                            <option value="12">Musical</option>
                            <option value="13">Storico</option>
                            <option value="14">Thriller</option>
                            <option value="15">Western</option>
                        </select>
                        <div class="invalid-feedback">
                            Devi scegliere un genere.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="plot" class="form-label text-light fs-1-5 fw-light">Trama</label>
                        <textarea name="plot" class="form-control rounded-3 fs-1-5 fw-light" id="plot" placeholder="Trama..." required minlength="10" maxlength="1000">${not empty film ? film.plot : ""}</textarea>
                        <div class="invalid-feedback">
                            La trama deve essere di 10-1000 caratteri.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="cover" class="form-label text-light fs-1-5 fw-light">Cover (16:9)</label>
                        <input name="cover" type="file" class="form-control rounded-3 fs-1-5 fw-light" id="cover" required>
                        <div class="invalid-feedback">
                            Devi scegliere una cover (16:9).
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="poster" class="form-label text-light fs-1-5 fw-light">Locandina (verticale)</label>
                        <input name="poster" type="file" class="form-control rounded-3 fs-1-5 fw-light" id="poster" required>
                        <div class="invalid-feedback">
                            Devi scegliere una locandina (verticale).
                        </div>
                    </div>
                    <hr class="text-light">
                    <div id="accordion">
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingActors">
                                <button class="accordion-button fs-1-5" type="button" data-bs-toggle="collapse" data-bs-target="#collapseActors" aria-expanded="true" aria-controls="collapseActors">
                                    Attori
                                </button>
                            </h2>
                            <div id="collapseActors" class="accordion-collapse collapse show" aria-labelledby="headingActors" data-bs-parent="#accordion">
                                <div class="accordion-body overflow-auto" style="max-height: 20rem;">

                                </div>
                            </div>
                        </div>
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingDirectors">
                                <button class="accordion-button collapsed fs-1-5" type="button" data-bs-toggle="collapse" data-bs-target="#collapseDirectors" aria-expanded="false" aria-controls="collapseDirectors">
                                    Registi
                                </button>
                            </h2>
                            <div id="collapseDirectors" class="accordion-collapse collapse" aria-labelledby="headingDirectors" data-bs-parent="#accordion">
                                <div class="accordion-body overflow-auto" style="max-height: 20rem;">

                                </div>
                            </div>
                        </div>
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingHouseProduction">
                                <button class="accordion-button collapsed fs-1-5" type="button" data-bs-toggle="collapse" data-bs-target="#collapseHouseProduction" aria-expanded="false" aria-controls="collapseHouseProduction">
                                    Case di produzione
                                </button>
                            </h2>
                            <div id="collapseHouseProduction" class="accordion-collapse collapse" aria-labelledby="headingHouseProduction" data-bs-parent="#accordion">
                                <div class="accordion-body overflow-auto" style="max-height: 20rem;">

                                </div>
                            </div>
                        </div>
                        <div class="accordion-item">
                            <h2 class="accordion-header" id="headingProduction">
                                <button class="accordion-button collapsed fs-1-5" type="button" data-bs-toggle="collapse" data-bs-target="#collapseProduction" aria-expanded="false" aria-controls="collapseProduction">
                                    Produzione
                                </button>
                            </h2>
                            <div id="collapseProduction" class="accordion-collapse collapse" aria-labelledby="headingProduction" data-bs-parent="#accordion">
                                <div class="accordion-body overflow-auto" style="max-height: 20rem;">

                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-light rounded-3 text-dark fs-1-5 mt-4">
                        <c:choose>
                            <c:when test="${formType == 'add'}">
                                Aggiungi
                            </c:when>
                            <c:when test="${formType == 'update'}">
                                Modifica
                            </c:when>
                        </c:choose>
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../partial/site/footer.jsp"/>
</body>
</html>