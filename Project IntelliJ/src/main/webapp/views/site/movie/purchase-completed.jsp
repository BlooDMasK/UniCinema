<%@ page import="java.time.LocalTime" %>
<%@ page import="model.bean.Show" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.TextStyle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.bean.Ticket" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it">
<head>
    <jsp:include page="../../partial/head.jsp">
        <jsp:param name="styles" value="bootstrap,purchase"/>
        <jsp:param name="scripts" value="purchase"/>
    </jsp:include>
</head>
<body class="bg-img">
<jsp:include page="../../partial/site/header.jsp"/> <!-- importo la navbar -->
<div class="d-flex flex-column align-items-center mt-5 mb-5">
    <div class="d-flex flex-column bg-dark text-center text-light justify-content-center pb-3" style="border-radius: 1rem;width: 90%">
        <p class="fs-1 fw-light">Acquisto completato</p>
        <hr class="w-50 align-self-center">
        <p class="fs-2 fw-light">Ti ringraziamo per averci scelto</p>
        <hr class="w-75 align-self-center">
        <p class="fs-4 fw-light">Riepilogo acquisto</p>
        <div class="container text-light">
            <%
                LocalTime localTime = ((Show) request.getAttribute("show")).getTime();
                LocalDate localDate = ((Show) request.getAttribute("show")).getDate();
            %>
            <div class="row justify-content-md-center">
                <div class="col-md-4"></div>
                <div class="col-md-2 align-self-center">
                    <img src="${pageContext.request.contextPath}/images/${film.poster}" class="img-fluid" alt="...">
                </div>
                <div class="col-md-6 text-light text-start" style="border-left-width: 1px;border-left-style: inset;">
                    <p class="text-light fs-4 fw-light">Film: ${film.title}</p>
                    <p class="text-light fs-4 fw-light">Spettacolo #${show.id}</p>
                    <p class="text-light fs-4 fw-light">Data: <span><%= localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALY) %> </span> <span><%= localDate.getDayOfMonth() + " " + localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN) %></span></p>
                    <p class="text-light fs-4 fw-light">Orario: <%= localTime.getHour() + ":" + ((localTime.getMinute() < 10) ? (localTime.getMinute() + "0") : localTime.getMinute()) %></p>
                    <p class="text-light fs-4 fw-light">Sala ${room.id}</p>
                </div>
                <hr class="w-75 align-self-center mt-2">
            </div>
            <div class="d-flex justify-content-center">
                <table class="table table-responsive table-light table-striped" style="width: 70%;">
                    <caption>
                        Puoi recuperare i codici univoci anche dallo <a class="text-muted" href="${pageContext.request.contextPath}/account/profile?purchases=1">storico ordini</a>.
                        <br>
                        Torna alla <a class="text-muted" href="${pageContext.request.contextPath}/film/schedule">programmazione</a>.
                    </caption>
                    <thead class="table-light">
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Lettera</th>
                            <th scope="col">Poltrona</th>
                            <th scope="col">Codice Univoco</th>
                            <th scope="col">Prezzo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${ticketList}" var="ticket">
                            <tr>
                                <th scope="row">${ticket.id}</th>
                                <td>${ticket.rowLetter}</td>
                                <td>${ticket.seat}</td>
                                <td><%=((Ticket) pageContext.findAttribute("ticket")).generateUniqueCode()%></td>
                                <td>${ticket.price}&euro;</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../../partial/site/footer.jsp"/>
</body>
</html>