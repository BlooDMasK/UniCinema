<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="alert alert-${alert.type}" role="alert">
    <ol>
        <c:forEach var="msg" items="${alert.messages}">
            <li class="alert-item">${msg}</li>
        </c:forEach>
    </ol>
    <button type="button" id="alert-close" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
<% session.removeAttribute("alert"); %>