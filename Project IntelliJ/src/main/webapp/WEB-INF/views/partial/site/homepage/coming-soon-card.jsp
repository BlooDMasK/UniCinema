<%@ page import="Model.film.Film" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row row-cols-1 row-cols-md-3 p-5">
    <c:forEach items="${filmComingSoon}" var="film">
        <div class="col mb-4">
            <a href="#" class="card shadow">
                <div class="overflow-hidden">
                    <img class="card-img-top" src="images/${film.cover}" alt="Card image cap">
                    <div class="card-icon">
                        <%@include file="../../../../../icons/info.svg"%>
                    </div>
                    <h5 class="card-img-title d-flex flex-column">
                        <div>${film.title}</div>
                        <div class="fw-normal">In uscita: <%= ((Film) pageContext.findAttribute("film")).getDatePublishing().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></div>
                    </h5>
                </div>
            </a>
        </div>
    </c:forEach>
</div>