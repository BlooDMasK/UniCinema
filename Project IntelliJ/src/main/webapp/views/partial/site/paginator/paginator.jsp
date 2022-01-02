<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="pagination pagination-md">
    <c:forEach var="page" begin="1" end="${pages}">
        <li class="page-item ${page == 1 ? "active" : ""}">
            <a class="page-link" href="#">${page}</a>
        </li>
    </c:forEach>
</ul>