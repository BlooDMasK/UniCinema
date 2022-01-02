<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <jsp:include page="../partial/head.jsp">
        <jsp:param name="styles" value="bootstrap,movie-card,homepage"/>
    </jsp:include>
</head>
<body class="bg-img">

        <div class="flex-wrapper d-flex flex-column">
        <jsp:include page="../partial/site/header.jsp"/> <!-- importo la navbar -->
        <!-- Carosello con le ultime 3 uscite -->
        <div id="carousel-homepage" class="carousel slide mb-5" data-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carousel-homepage" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carousel-homepage" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carousel-homepage" data-bs-slide-to="2" aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
                <c:set var="count" value="false"/> <!-- creo una variabile d'appoggio per settare il primo elemento del carosello ad active, altrimenti non funziona -->
                <c:forEach items="${filmCarousel}" var="film"> <!-- prendo dal context della request l'attributo filmCarousel e scorro la lista con le variabili 'film' -->
                    <div class="carousel-item ${not count ? 'active' : ''}"> <!-- verifico, se è false, setto l'active -->
                        <a href="${pageContext.request.contextPath}/film/details?filmId=${film.id}"><img class="d-block w-100 overlay-image" src="images/${film.cover}" alt="Slide"></a>
                        <div class="carousel-caption d-none d-md-block">
                            <h5>${film.title}</h5>
                            <p>${film.plot}</p>
                        </div>
                    </div>
                    <c:set var="count" value="true"/> <!-- alla fine setto a true, così da non fare il controllo con gli altri 2 film -->
                </c:forEach>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carousel-homepage" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Precedente</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carousel-homepage" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Successivo</span>
            </button>
        </div>
        <p class="text-wrap bg-dark text-light align-self-center fw-normal fs-1 rounded-pill homepage-paragraph">ULTIME USCITE</p>
        <div>
            <!-- importo la parte delle card delle ultime uscite -->
            <jsp:include page="../partial/site/homepage/movie-card.jsp" />
        </div>
        <p class="text-wrap bg-dark text-light align-self-center fw-normal fs-1 rounded-pill homepage-paragraph">COMING SOON</p>
        <div>
            <!-- importo la parte delle card delle prossime uscite -->
            <jsp:include page="../partial/site/homepage/coming-soon-card.jsp" />
        </div>
    </div>
    <jsp:include page="../partial/site/footer.jsp"/>
</body>
</html>
