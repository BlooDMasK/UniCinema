<%@ page import="model.bean.Film" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.TextStyle" %>
<%@ page import="model.bean.Show" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it">
<head>
    <jsp:include page="../../partial/head.jsp">
        <jsp:param name="styles" value="bootstrap,schedule"/>
    </jsp:include>
</head>
<body class="bg-img">
<div class="d-flex flex-column">
    <jsp:include page="../../partial/site/header.jsp"/>
    <div class="align-self-center" style="max-width: 95%">
        <c:forEach items="${filmList}" var="film">
            <div class="card mb-3 bg-dark mt-2" style="border-radius: 0rem 1rem 1rem 0rem">
                <div class="row g-0 bg-dark" style="border-radius: 0rem 1rem 1rem 0rem">
                    <div class="col-md-2" style="align-self: center">
                        <img src="${pageContext.request.contextPath}/images/${film.poster}" class="img-fluid rounded-start" alt="...">
                    </div>
                    <div class="col-md-10">
                        <div class="card-body" style="color: var(--bs-light)">
                            <h5 class="card-title">${film.title}</h5>
                            <hr>
                            <p class="card-text">
                                <small>
                                    <span class="fw-bold">Genere</span> <%= ((Film) pageContext.findAttribute("film")).toStringGenre()%>
                                </small>
                            </p>
                            <p class="card-text">
                                <small>
                                    <span class="fw-bold">Regia</span> <%= ((Film) pageContext.findAttribute("film")).toStringDirectors()%>
                                </small>
                            </p>
                            <p class="card-text">
                                <small>
                                    <span class="fw-bold">Cast</span> <%= ((Film) pageContext.findAttribute("film")).toStringActors()%>
                                </small>
                            </p>
                            <p class="card-text">${film.plot}</p>
                            <hr>
                            <%
                                Film film = ((Film) pageContext.findAttribute("film"));
                                pageContext.setAttribute("dateMap", Show.toHashMapDateTime(film.getShowList()));
                                int showCount = 0;
                            %>
                            <!--------->
                            <div>
                                <h5 class="card-title" style="text-align: center">Spettacoli</h5>
                                <div id="carouselScheduling${film.id}" class="carousel slide" data-bs-touch="false" data-bs-interval="false" data-bs-ride="carousel" style="align-self: center;text-align: center;height: 6rem;">
                                    <div class="carousel-inner" style="height: 6rem;">
                                        <c:set var="count" value="false"/>
                                        <c:forEach items="${dateMap}" var="date">
                                            <c:set var="dateKey" value="${date.key}"/>
                                            <div class="carousel-item ${not count ? 'active' : ''}">
                                                <p class="card-title mt-2 mb-2"><%= ((LocalDate) pageContext.findAttribute("dateKey")).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN).substring(0, 3).toUpperCase() + " " + ((LocalDate) pageContext.findAttribute("dateKey")).getDayOfMonth() + " " + ((LocalDate) pageContext.findAttribute("dateKey")).getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN).toUpperCase() %></p>
                                                <c:forEach items="${date.value}" var="time">
                                                    <c:choose>
                                                        <c:when test="${empty accountSession}">
                                                            <a class="btn btn-outline-light rounded-3" href="${pageContext.request.contextPath}/account/signin">
                                                                <%= ((LocalTime) pageContext.findAttribute("time")).getHour() + ":" + ((((LocalTime) pageContext.findAttribute("time")).getMinute() < 10) ? (((LocalTime) pageContext.findAttribute("time")).getMinute() + "0") : ((LocalTime) pageContext.findAttribute("time")).getMinute()) %>
                                                            </a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <a class="btn btn-outline-light rounded-3" href="${pageContext.request.contextPath}/purchase/seat-choice?showId=<%=film.getShowList().get(showCount++).getId()%>">
                                                                <%= ((LocalTime) pageContext.findAttribute("time")).getHour() + ":" + ((((LocalTime) pageContext.findAttribute("time")).getMinute() < 10) ? (((LocalTime) pageContext.findAttribute("time")).getMinute() + "0") : ((LocalTime) pageContext.findAttribute("time")).getMinute()) %>
                                                            </a>
                                                        </c:otherwise>
                                                    </c:choose>

                                                </c:forEach>
                                            </div>
                                            <c:set var="count" value="true"/>
                                        </c:forEach>
                                    </div>
                                    <button class="carousel-control-prev" type="button" data-bs-target="#carouselScheduling${film.id}" data-bs-slide="prev">
                                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Previous</span>
                                    </button>
                                    <button class="carousel-control-next" type="button" data-bs-target="#carouselScheduling${film.id}" data-bs-slide="next">
                                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                        <span class="visually-hidden">Next</span>
                                    </button>
                                </div>
                                <div style="text-align: center">
                                    <a class="btn btn-outline-light rounded-3" href="${pageContext.request.contextPath}/film/details?filmId=${film.id}" role="button">Dettagli Film</a>
                                </div>
                            </div>
                            <!--------->
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<jsp:include page="../../partial/site/footer.jsp"/>
</body>
</html>
