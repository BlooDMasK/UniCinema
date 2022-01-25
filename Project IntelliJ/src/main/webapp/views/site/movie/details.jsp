<%@ page import="model.bean.Film" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="model.bean.Show" %>
<%@ page import="java.time.format.TextStyle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.bean.Room" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="it">
<head>
    <jsp:include page="../../partial/head.jsp">
        <jsp:param name="styles" value="bootstrap,review,details-film"/>
        <jsp:param name="scripts" value="review,paginator,alert,details-film"/>
    </jsp:include>
</head>
<body class="bg-img">
    <%
        Film film = ((Film) request.getAttribute("film"));
        pageContext.setAttribute("dateMap", Show.toHashMapDateTime(film.getShowList()));
        int showCount = 0;
    %>
    <script>
        const filmId = '${film.id}';
        let accountIsAdministrator = accountId = 0;
        let modifyShow = false;

        <c:if test="${not empty accountSession}">
            accountId = ${accountSession.id};
            accountIsAdministrator = ${accountSession.administrator};
            modifyShow = ${empty dateMap ? false : true};
        </c:if>
    </script>
    <jsp:include page="../../partial/site/header.jsp"/> <!-- importo la navbar -->
    <div class="d-flex flex-column justify-content-center mt-5 mb-5">
        <div class="container bg-dark" style="border-radius: 1rem">
            <div class="row">
                <div class="col-md-3" style="text-align: center;margin-top: .7rem">
                    <img src="${pageContext.request.contextPath}/images/${film.poster}" class="img-fluid" alt="...">
                    <hr class="text-light">
                    <div class="d-flex flex-column align-items-end text-light">
                        <small class="fw-bold">Data di uscita</small>
                            <small><%= film.getDatePublishing() %></small>
                        <small class="fw-bold">Durata</small>
                            <small>${film.length}min.</small>
                        <small class="fw-bold mt-1">Regia</small>
                        <c:forEach items="${film.directorList}" var="director">
                            <small>${director.firstname} ${director.lastname}</small>
                        </c:forEach>
                        <small class="fw-bold mt-1">Cast</small>
                        <c:forEach items="${film.actorList}" var="actor">
                            <small>${actor.firstname} ${actor.lastname}</small>
                        </c:forEach>
                        <small class="fw-bold mt-1">Produzione</small>
                        <c:forEach items="${film.productionList}" var="production">
                            <small>${production.firstname} ${production.lastname}</small>
                        </c:forEach>
                        <small class="fw-bold mt-1">Casa di produzione</small>
                        <c:forEach items="${film.houseProductionList}" var="houseProduction">
                            <small>${houseProduction.name}</small>
                        </c:forEach>

                    </div>
                </div>
                <div class="col text-light" style="border-left-width: 1px;border-left-style: inset;margin-top: .7rem;">
                    <p class="film-title text-light fs-1 fw-light">${film.title}</p>
                    <p class="fs-5" style="color: var(--bs-gray-500)"><%= film.toStringGenre() %></p>
                    <p>${film.plot}</p>
                    <div class="text-light">
                        <hr>
                        <h5 class="film-spectacle">Spettacoli</h5>
                        <div class="show-div">
                            <c:choose>
                                <c:when test="${empty dateMap}">
                                    <h5>Nessuno spettacolo disponibile.</h5>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${dateMap}" var="date">
                                        <c:set var="dateKey" value="${date.key}" />
                                        <% LocalDate localDate = (LocalDate) pageContext.findAttribute("dateKey"); %>
                                        <div class="mb-2">
                                            <span class="fw-bold"><%= localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALY).substring(0, 3).toUpperCase() %> </span> <span><%= localDate.getDayOfMonth() + " " + localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN).toUpperCase() %></span>
                                        </div>
                                        <div class="d-flex mb-2 show-list-div">
                                            <c:forEach items="${date.value}" var="show">
                                                <%
                                                    Show show = (Show) pageContext.findAttribute("show");
                                                    LocalTime localTime = show.getTime();
                                                    Room room = show.getRoom();
                                                %>
                                                <c:choose>
                                                    <c:when test="${empty accountSession}">
                                                        <a class="btn btn-outline-light rounded-3 me-2" href="${pageContext.request.contextPath}/account/signin" style="width: 10%">
                                                            <%= localTime.getHour() + ":" + ((localTime.getMinute() < 10) ? (localTime.getMinute() + "0") : localTime.getMinute()) + " Sala " + room.getId() %>
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <% int showId = film.getShowList().get(showCount++).getId(); %>
                                                        <a class="btn btn-outline-light btn-show rounded-3 me-2" href="${pageContext.request.contextPath}/purchase/seat-choice?showId=<%=showId%>" style="width: 10%">
                                                            <%= localTime.getHour() + ":" + ((localTime.getMinute() < 10) ? (localTime.getMinute() + "0") : localTime.getMinute()) + " Sala " + room.getId() %>
                                                            <input type="hidden" name="showId" value="<%=showId%>">
                                                        </a>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <script>
                        let filmLength = '${film.length}';
                        let dateNow = <%=LocalDate.now()%>;

                    </script>
                    <form id="showForm" method="post" action="${pageContext.request.contextPath}/show-manager/add" class="collapse needs-validation" novalidate>

                    </form>
                </div>
                <div style="text-align: -webkit-center">
                    <hr class="text-light">
                </div>
                <div class="row mb-4">
                    <!-- customer reviews -->
                    <div class="col-lg-4 col-md-5 col-12 mb-4 mb-lg-0 pr-lg-6 text-light">
                        <div class="mb-6">
                            <h4 class="mb-3">Recensioni utenti</h4>
                            <span id="reviewStatsStars"></span>
                            <span class="h5" id="reviewStatsAvg">0 su 5</span>
                            <p class="font-14" id="reviewStatsCount">0 recensioni</p>
                            <div class="row align-items-center mb-1 ">
                                <div class="col-md-2 col-2 pr-0">
                                    <div class="font-12" style="color: var(--bs-gray-500)">5 Star</div>
                                </div>
                                <div class="col-md-8 col-8 pr-2">
                                    <div class="progress" style="height: 8px;">
                                        <div class="progress-bar bg-warning" id="reviewStatsBar5" role="progressbar" aria-valuenow="78" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="col-md-2 col-2">
                                    <div class="font-12 text-warning" id="reviewStatsPercentage5">0%</div>
                                </div>
                            </div>
                            <div class="row align-items-center mb-1">
                                <div class="col-md-2 col-2 pr-0">
                                    <div class="font-12" style="color: var(--bs-gray-500)">4 Star</div>
                                </div>
                                <div class="col-md-8 col-8 pr-2">
                                    <div class="progress" style="height: 8px;">
                                        <div class="progress-bar bg-warning" id="reviewStatsBar4" role="progressbar" aria-valuenow="12" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="col-md-2 col-2">
                                    <div class="font-12 text-warning" id="reviewStatsPercentage4">0%</div>
                                </div>
                            </div>
                            <div class="row align-items-center mb-1">
                                <div class="col-md-2 col-2 pr-0">
                                    <div class="font-12" style="color: var(--bs-gray-500)">3 Star</div>
                                </div>
                                <div class="col-md-8 col-8 pr-2">
                                    <div class="progress" style="height: 8px;">
                                        <div class="progress-bar bg-warning" id="reviewStatsBar3" role="progressbar" aria-valuenow="5" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="col-md-2 col-2">
                                    <div class="font-12 text-warning" id="reviewStatsPercentage3">0%</div>
                                </div>
                            </div>
                            <div class="row align-items-center mb-1">
                                <div class="col-md-2 col-2 pr-0">
                                    <div class="font-12" style="color: var(--bs-gray-500)">2 Star</div>
                                </div>
                                <div class="col-md-8 col-8 pr-2">
                                    <div class="progress" style="height: 8px;">
                                        <div class="progress-bar bg-warning" id="reviewStatsBar2" role="progressbar" aria-valuenow="2" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="col-md-2 col-2">
                                    <div class="font-12 text-warning" id="reviewStatsPercentage2">0%</div>
                                </div>
                            </div>
                            <div class="row align-items-center mb-2">
                                <div class="col-md-2 col-2 pr-0">
                                    <div class="font-12" style="color: var(--bs-gray-500)">1 Star</div>
                                </div>
                                <div class="col-md-8 col-8 pr-2">
                                    <div class="progress" style="height: 8px;">
                                        <div class="progress-bar bg-warning" id="reviewStatsBar1" role="progressbar" aria-valuenow="1" aria-valuemin="0" aria-valuemax="100"></div>
                                    </div>
                                </div>
                                <div class="col-md-2 col-2">
                                    <div class="font-12 text-warning" id="reviewStatsPercentage1">0%</div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <h4 class="mb-1">Recensisci questo film</h4>
                            <p class="font-12 ">Condividi il tuo pensiero con gli utenti</p>
                            <c:choose>
                                <c:when test="${empty accountSession}">
                                    <a class="btn btn-light btn-block" href="${pageContext.request.contextPath}/account/signin">
                                        Scrivi una recensione
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-light btn-block" data-bs-toggle="collapse" id="collapseReviewButton" href="#collapseReviewBox" role="button" aria-expanded="false" aria-controls="collapseReviewBox">
                                        Scrivi una recensione
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <!-- customer reviews list -->
                    <div class="col-lg-8 col-md-7 col-12 text-light" id="review-list">
                        <div class="d-flex align-items-center justify-content-between mb-4">
                            <div>
                                <h4 class="mb-0" id="reviewTitle">Recensioni</h4>
                            </div>
                        </div>
                        <!-- scrivi una recensione -->
                        <form class="collapse mb-4 pb-4" id="collapseReviewBox" novalidate>
                            <jsp:include page="../../partial/site/alert.jsp"/>
                            <div class="form-group">
                                <label for="reviewWriteTitle">Titolo</label>
                                <input type="text" class="form-control" name="reviewWriteTitle" id="reviewWriteTitle" placeholder="Titolo della recensione" minlength="3" maxlength="40" pattern="^[A-Za-z':;.,àèìòù0-9?! ]{3,40}$" required>

                                <label for="reviewWriteDescription">Descrizione</label>
                                <textarea class="form-control" name="reviewWriteDescription" id="reviewWriteDescription" rows="3" placeholder="Descrizione della recensione" minlength="5" maxlength="500" pattern="^[A-Za-z0-9\W]{5,500}$" required></textarea>

                                <div class="d-flex justify-content-between mt-2">
                                    <div class="d-flex mt-1 mb-1">
                                        <span class="fa fa-star me-2 text-warning review-star">
                                            <div class="text-dark number review-star-text">5</div>
                                            <input type="hidden" value="5" name="reviewWriteStars" id="reviewWriteStars">
                                        </span>
                                        <div class="d-flex flex-column justify-content-center">
                                            <span class="fa fa-plus text-success btn btn-review btn-outline-light mb-1"></span>
                                            <span class="fa fa-minus text-danger btn btn-review btn-outline-light"></span>
                                        </div>
                                    </div>
                                    <div class="align-self-center">
                                        <c:choose>
                                            <c:when test="${empty accountSession}">
                                                <a href="${pageContext.request.contextPath}/account/signin" class="btn btn-light btn-block">Pubblica</a>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-light btn-block">Pubblica</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </form>
                        <!-- reviews - vedi file review.js -->
                        <div class="review-div">

                        </div>
                        <jsp:include page="../../partial/site/paginator/paginator.jsp"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../../partial/site/footer.jsp"/>
</body>
</html>
