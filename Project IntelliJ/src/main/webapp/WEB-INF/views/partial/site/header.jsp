<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light bg-dark">
    <div class="container">
        <div>
            <span class="fs-2 logo-font" style="color: var(--bs-yellow)">Uni</span><span class="fs-2 logo-font" style="color: var(--bs-white)">Cinema</span>
        </div>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Programmazione</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Info & Costi</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Prossimamente</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">FAQs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Profilo</a>
                </li>
                <li class="nav-item">
                    <form class="d-flex my-auto">
                        <input class="form-control me-2 rounded-pill border-0" type="search" placeholder="Cerca un film..." aria-label="Cerca">
                        <a id="search-icon" class="align-self-center" href="#"><%@include file="../../../../icons/search.svg"%></a>
                    </form>
                </li>
                <li class="nav-item">
                    <a id="login-icon" class="nav-link align-self-center" href="${pageContext.request.contextPath}/account/signin"><%@include file="../../../../icons/log-in.svg"%></a>
                </li>
            </ul>
        </div>
    </div>
</nav>