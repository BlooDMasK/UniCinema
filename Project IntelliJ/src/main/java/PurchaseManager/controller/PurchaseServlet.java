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
import java.util.*;

@WebServlet(name = "PurchaseServlet", value = "/purchase/*")
public class PurchaseServlet extends Controller implements ErrorHandler {

    /**
     * {@link ShowService}
     */
    ShowService showService;
    PurchaseService purchaseService;
    JSONObject jsonObject;

    /**
     * Metodo che permette di settare il JSONObject
     * @param jsonObject
     */
    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public PurchaseServlet() throws SQLException {
        showService = new ShowServiceMethods();
        purchaseService = new PurchaseServiceMethods();
        jsonObject = new JSONObject();
    }

    /**
     * Metodo che permette di settare lo ShowService con la sua implementazione
     * @param showService
     */
    public void setShowService(ShowService showService) {
        this.showService = showService;
    }

    /**
     * Metodo che permette di settare il PurchaseService con la sua implementazione
     * @param purchaseService
     */
    public void setPurchaseService(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    /**
     * Implementa le funzionalità svolte durante una chiamata di tipo GET
     * @param request oggetto rappresentante la chiamata Http request
     * @param response oggetto rappresentante la chiamata Http response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            /**
             * Rappresenta il path che permette di smistare le funzionalità.
             */
            String path = getPath(request);

            /**
             * Rappresenta la sessione
             */
            HttpSession session = request.getSession();
            switch (path) {
                /**
                 * Implementa la funzionalità che permette di scegliere un posto in sala
                 */
                case "/seat-choice":
                    authenticate(session);
                    int showId = Integer.parseInt(request.getParameter("showId"));
                    Show show = showService.fetch(showId);
                    Room room = showService.fetchRoom(showId);
                    if(show != null && room != null) {
                        ArrayList<Ticket> ticketList = purchaseService.fetchTickets(showId);

                        HashMap<String, Ticket> ticketMap = new LinkedHashMap<>();

                        for(Ticket ticket : ticketList)
                            ticketMap.put(ticket.getRowLetter()+"-"+ticket.getSeat(), ticket);

                        request.setAttribute("show", show);
                        request.setAttribute("film", show.getFilm());
                        request.setAttribute("room", room);
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

    /**
     * Implementa le funzionalità svolte durante una chiamata di tipo POST
     * @param request oggetto rappresentante la chiamata Http request
     * @param response oggetto rappresentante la chiamata Http response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            /**
             * Rappresenta il path che permette di smistare le funzionalità.
             */
            String path = getPath(request);

            /**
             * Rappresenta la sessione
             */
            HttpSession session = request.getSession();
            switch (path) {
                /**
                 * Implementa la funzionalità che permette di verificare se un posto è occupato o meno
                 */
                case "/seat-check":
                    if(isAjax(request)) {
                        int showId = Integer.parseInt(request.getParameter("showId"));
                        String[] key = request.getParameter("key").split("-");
                        char row = key[0].toCharArray()[0];
                        int seat = Integer.parseInt(key[1]);

                        if(purchaseService.findTicket(showId, row, seat))
                            jsonObject.put("occupied", true);
                        else
                            jsonObject.put("occupied", false);

                        sendJson(response, jsonObject);
                    }
                    break;

                /**
                 * Implementa la funzionalità che permette di acquistare un biglietto
                 */
                case "/get-ticket":
                    authenticate(session);
                    Purchase purchase = new Purchase(getSessionAccount(session));

                    int purchaseId = purchaseService.insert(purchase);
                    if(purchaseId > 0) {

                        ArrayList<Ticket> ticketList = new ArrayList<>();

                        Show show = showService.fetch(Integer.parseInt(request.getParameter("showId")));
                        if(show == null)
                            internalError("Errore nella ricerca dello spettacolo");

                        for(int i = 1; i < 5; i++) {
                            String param = request.getParameter("ticket" + i);
                            if (!param.isEmpty()) {
                                String[] key = param.split("-");
                                char row = key[0].toCharArray()[0];
                                int seat = Integer.parseInt(key[1]);

                                Ticket ticket = new Ticket(TICKET_PRICE, seat, row, show, purchase);
                                ticketList.add(ticket);
                            }
                        }

                        if(purchaseService.insert(ticketList)) {

                            request.setAttribute("ticketList", ticketList);
                            request.setAttribute("purchase", purchase);
                            request.setAttribute("show", show);
                            request.setAttribute("film", show.getFilm());
                            Room room = showService.fetchRoom(show.getId());
                            if(room != null)
                                request.setAttribute("room", room);
                            else
                                internalError("Errore nella ricerca della sala");

                            request.getRequestDispatcher(view("site/movie/purchase-completed")).forward(request, response);
                        } else
                            internalError("Errore nella creazione dei ticket");

                    } else
                        internalError("Errore nella creazione dell'acquisto");
                    break;

                /**
                 * Implementa la funzionalità che permette di restituire una lista di purchase
                 */
                case "/list":
                    if(isAjax(request)) {
                        int accountId = getSessionAccount(session).getId();
                        Paginator paginator = new Paginator(parsePage(request), 5);
                        int size = purchaseService.countAll(accountId);

                        ArrayList<Purchase> purchaseList = purchaseService.fetchAll(accountId, paginator);

                        JSONArray list = new JSONArray();

                        for(Purchase purchaseItem : purchaseList)
                            list.put(purchaseItem.toJson());

                        jsonObject.put("purchaseList", list);
                        jsonObject.put("pages", paginator.getPages(size));

                        sendJson(response, jsonObject);
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

    /**
     * Stabilisce il prezzo del biglietto
     */
    public final static double TICKET_PRICE = 7.0;
}
