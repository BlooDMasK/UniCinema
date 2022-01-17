<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light bg-dark">
    <div class="container">
        <a href="${pageContext.request.contextPath}" style="text-decoration: none">
            <span class="fs-2 logo-font" style="color: var(--bs-yellow)">Uni</span><span class="fs-2 logo-font" style="color: var(--bs-white)">Cinema</span>
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="${pageContext.request.contextPath}/film/schedule">Programmazione</a>
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
                    <div class="d-flex my-auto">
                        <div class="dropdown" id="search-dropdiv">
                            <input id="search-bar" class="form-control me-2 rounded-pill border-0 dropdown-toggle" placeholder="Cerca un film..." aria-label="Cerca" autocomplete="off">
                            <ul id="search-dropdown" class="dropdown-menu" aria-labelledby="dropdownMenuButton1">

                            </ul>
                        </div>
                        <div id="search-icon" class="align-self-center ms-1"><%@include file="../../../static/icons/search.svg"%></div>
                    </div>
                </li>
                <li class="nav-item">
                    <c:choose>
                        <c:when test="${empty accountSession}">
                            <a class="login-icon nav-link align-self-center" href="${pageContext.request.contextPath}/account/signin"><%@include file="../../../static/icons/log-in.svg"%></a>
                        </c:when>
                        <c:otherwise>
                            <div class="dropdown">
                                <a class="login-icon nav-link align-self-center dropdown-toggle" href="${pageContext.request.contextPath}/account/info" data-bs-toggle="dropdown" data-bs-auto-close="true" aria-expanded="false"><%@include file="../../../static/icons/person-circle.svg"%></a>
                                <ul class="dropdown-menu rounded-0 bg-light" aria-labelledby="user-icon">
                                    <li class="dropdown-row">
                                        <a class="d-flex flex-row" href="${pageContext.request.contextPath}/account/profile">
                                            <div class="dropdown-icon"><%@include file="../../../static/icons/user.svg"%></div>
                                            <div class="dropdown-text align-self-center">Profilo</div>
                                        </a>
                                    </li>
                                    <li class="dropdown-row">
                                        <a class="d-flex flex-row" href="${pageContext.request.contextPath}/account/profile?edit=1">
                                            <div class="dropdown-icon"><%@include file="../../../static/icons/settings.svg"%></div>
                                            <div class="dropdown-text align-self-center">Modifica</div>
                                        </a>
                                    </li>
                                    <li class="dropdown-row">
                                        <a class="d-flex flex-row" href="${pageContext.request.contextPath}/account/profile?purchases=1">
                                            <div class="dropdown-icon"><%@include file="../../../static/icons/chronology.svg"%></div>
                                            <div class="dropdown-text align-self-center">Storico Ordini</div>
                                        </a>
                                    </li>
                                    <li class="dropdown-row">
                                        <a class="d-flex flex-row" href="${pageContext.request.contextPath}/account/logout">
                                            <div class="dropdown-icon"><%@include file="../../../static/icons/log-out.svg"%></div>
                                            <div class="dropdown-text align-self-center">Logout</div>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </li>
            </ul>
        </div>
    </div>
</nav>