<%@ page import="java.time.LocalTime" %>
<%@ page import="model.bean.Show" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.TextStyle" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.bean.Ticket" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="it">
<head>
    <jsp:include page="../../partial/head.jsp">
        <jsp:param name="styles" value="bootstrap,purchase"/>
        <jsp:param name="scripts" value="purchase"/>
    </jsp:include>
</head>
<body class="bg-img">
    <script>let showId = ${show.id}</script>
    <jsp:include page="../../partial/site/header.jsp"/> <!-- importo la navbar -->
    <div class="d-flex flex-column align-items-center mt-5 mb-5">
        <div class="d-flex flex-column bg-dark text-center text-light justify-content-center pb-3" style="border-radius: 1rem;width: 90%">
            <p class="fs-1 fw-light">Acquisto biglietto</p>
            <hr class="w-50 align-self-center">
            <p class="fs-2 fw-light">${film.title}</p>
            <%
                LocalTime localTime = ((Show) request.getAttribute("show")).getTime();
                LocalDate localDate = ((Show) request.getAttribute("show")).getDate();
            %>
            <p class="fs-4 fw-light">Spettacolo delle <%= localTime.getHour() + ":" + ((localTime.getMinute() < 10) ? (localTime.getMinute() + "0") : localTime.getMinute()) %>, in data <span><%= localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALY) %> </span> <span><%= localDate.getDayOfMonth() + " " + localDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ITALIAN) %></span></p>
            <hr class="w-75 align-self-center">
            <p class="fs-4 fw-light">Seleziona le poltrone</p>
            <form method="post" action="${pageContext.request.contextPath}/purchase/get-ticket" id="purchaseForm">
                <div class="d-flex flex-column align-items-center mb-3">
                    <p class="rounded-pill bg-light text-dark w-25 fw-light">Schermo | Sala ${room.id}</p>
                    <div>
                        <%
                            HashMap<String, Ticket> ticketMap = (HashMap<String, Ticket>) request.getAttribute("ticketMap");
                        %>
                        <c:forEach begin="1" end="${room.n_rows}" var="row">
                            <div class="row mb-1">
                                <c:set var="letter" value="&\#${(row+64)}" />
                                <c:forEach begin="1" end="${room.n_seats}" var="seat">
                                    <div class="col me-1 ms-1">
                                        <%
                                            String colorClass = "btn-outline-light";
                                            int letterAscii = (Integer) pageContext.findAttribute("row");
                                            String letter = Character.toString(letterAscii+64);

                                            int seat = (int) pageContext.findAttribute("seat");

                                            String key = letter + "-" + seat;
                                            if(ticketMap.containsKey(key))
                                                colorClass = "btn-danger";
                                        %>
                                        <button type="button" class="btn <%=colorClass%> btn-seat btn-sm">${letter}-${seat}</button>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="d-flex justify-content-evenly mt-3 mb-2" style="width: 50%">
                        <div class="border border-light fw-light legend">Disponibile</div>
                        <div class="bg-success fw-light legend">Selezionato</div>
                        <div class="bg-danger fw-light legend">Occupato</div>
                    </div>
                    <small class="text-warning">Attenzione: puoi selezionare un massimo di 4 posti.</small>
                    <small class="text-danger" id="purchaseWarning"></small>
                </div>

                <input type="hidden" name="ticket1" id="ticket1">
                <input type="hidden" name="ticket2" id="ticket2">
                <input type="hidden" name="ticket3" id="ticket3">
                <input type="hidden" name="ticket4" id="ticket4">
                <input type="hidden" name="showId" value="${show.id}">
                <button type="button" class="btn btn-light align-self-center" id="purchaseTicket">Acquista Biglietto</button>
            </form>
        </div>
    </div>
    <jsp:include page="../../partial/site/footer.jsp"/>
</body>
</html>