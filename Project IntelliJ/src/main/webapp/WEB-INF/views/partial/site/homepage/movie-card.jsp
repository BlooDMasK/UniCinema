<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row row-cols-1 row-cols-md-3 p-5">
    <!-- scorro la lista delle ultie uscite tramite l'attributo filmLastReleases preso dal context della request -->
    <c:forEach items="${filmLastReleases}" var="film">
        <div class="col mb-4">
            <a href="#" class="card shadow">
                <div class="overflow-hidden">
                    <img class="card-img-top" src="images/${film.cover}" alt="Card image cap">
                    <div class="card-icon">
                        <%@include file="../../../../../icons/info.svg"%>
                    </div>
                    <h5 class="card-img-title">${film.title}</h5>
                </div>
            </a>
        </div>
    </c:forEach>
</div>