package PurchaseManager.controller;

import PurchaseManager.service.PurchaseService;
import PurchaseManager.service.PurchaseServiceMethods;
import ShowManager.service.ShowService;
import ShowManager.service.ShowServiceMethods;
import model.bean.Purchase;
import model.bean.Room;
import model.bean.Show;
import model.bean.Ticket;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@WebServlet(name = "PurchaseServlet", value = "/purchase/*")
public class PurchaseServlet extends Controller implements ErrorHandler {

    ShowService showService = new ShowServiceMethods();
    PurchaseService purchaseService = new PurchaseServiceMethods();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String path = getPath(request);
            HttpSession session = request.getSession();
            switch (path) {
                case "/seat-choice":
                    authenticate(session);
                    int showId = Integer.parseInt(request.getParameter("showId"));
                    Optional<Show> show = showService.fetch(showId);
                    Optional<Room> room = showService.fetchRoom(showId);
                    if(show.isPresent() && room.isPresent()) {
                        ArrayList<Ticket> ticketList = purchaseService.fetchTickets(showId);

                        HashMap<String, Ticket> ticketMap = new LinkedHashMap<>();

                        for(Ticket ticket : ticketList)
                            ticketMap.put(ticket.getRowLetter()+"-"+ticket.getSeat(), ticket);

                        request.setAttribute("show", show.get());
                        request.setAttribute("film", show.get().getFilm());
                        request.setAttribute("room", room.get());
                        request.setAttribute("ticketMap", ticketMap);

                        request.getRequestDispatcher(view("site/movie/purchase")).forward(request, response);
                    }
                    break;
            }
        } catch (SQLException ex) {
            log(ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            e.handle(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String path = getPath(request);
            switch (path) {
                case "/seat-check":
                    if(isAjax(request)) {
                        int showId = Integer.parseInt(request.getParameter("showId"));
                        String[] key = request.getParameter("key").split("-");
                        char row = key[0].toCharArray()[0];
                        int seat = Integer.parseInt(key[1]);

                        JSONObject root = new JSONObject();
                        if(purchaseService.findTicket(showId, row, seat))
                            root.put("occupied", true);
                        else
                            root.put("occupied", false);

                        sendJson(response, root);
                    }
                    break;

                case "/get-ticket":
                    authenticate(session);
                    Purchase purchase = new Purchase(getSessionAccount(session));

                    int purchaseId = purchaseService.insert(purchase);
                    if(purchaseId > 0) {

                        ArrayList<Ticket> ticketList = new ArrayList<>();

                        Optional<Show> show = showService.fetch(Integer.parseInt(request.getParameter("showId")));
                        if(show.isEmpty())
                            internalError("Errore nella ricerca dello spettacolo");

                        for(int i = 1; i < 5; i++) {
                            String param = request.getParameter("ticket" + i);
                            if (!param.isEmpty()) {
                                String[] key = param.split("-");
                                char row = key[0].toCharArray()[0];
                                int seat = Integer.parseInt(key[1]);

                                Ticket ticket = new Ticket(TICKET_PRICE, seat, row, show.get(), purchase);
                                ticketList.add(ticket);
                            }
                        }

                        if(purchaseService.insert(ticketList)) {

                            request.setAttribute("ticketList", ticketList);
                            request.setAttribute("purchase", purchase);
                            request.setAttribute("show", show.get());
                            request.setAttribute("film", show.get().getFilm());
                            Optional<Room> room = showService.fetchRoom(show.get().getId());
                            if(room.isPresent())
                                request.setAttribute("room", room.get());
                            else
                                internalError("Errore nella ricerca della sala");

                            request.getRequestDispatcher(view("site/movie/purchase-completed")).forward(request, response);
                        } else
                            internalError("Errore nella creazione dei ticket");

                    } else
                        internalError("Errore nella creazione dell'acquisto");
                    break;

                case "/list":
                    if(isAjax(request)) {
                        int accountId = getSessionAccount(session).getId();
                        Paginator paginator = new Paginator(parsePage(request), 5);
                        int size = purchaseService.countAll(accountId);

                        ArrayList<Purchase> purchaseList = purchaseService.fetchAll(accountId, paginator);

                        JSONObject root = new JSONObject();
                        JSONArray list = new JSONArray();

                        for(Purchase purchaseItem : purchaseList)
                            list.put(purchaseItem.toJson());

                        root.put("purchaseList", list);
                        root.put("pages", paginator.getPages(size));

                        sendJson(response, root);
                    }
                    break;
            }
        } catch (SQLException ex) {
            log(ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            e.handle(request, response);
        }
    }

    public final static double TICKET_PRICE = 7.0;
}