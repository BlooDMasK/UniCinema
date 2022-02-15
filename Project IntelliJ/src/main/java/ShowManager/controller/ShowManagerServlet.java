package ShowManager.controller;

import ShowManager.service.ShowService;
import ShowManager.service.ShowServiceMethods;
import model.bean.Film;
import model.bean.Room;
import model.bean.Show;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Controller;
import utils.ErrorHandler;
import utils.InvalidRequestException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

@WebServlet(name = "ShowManagerServlet", value = "/show-manager/*")
public class ShowManagerServlet extends Controller implements ErrorHandler {

    /**
     * {@link ShowService}
     */
    ShowService showService;
    JSONObject jsonObject;

    /**
     * Metodo che setta l'oggetto JSONObject
     */
    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public ShowManagerServlet() throws SQLException {
        showService = new ShowServiceMethods();
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
             * Rappresenta la sessione attuale {@link HttpSession}.
             */
            HttpSession session = request.getSession();
            switch(path) {
                /**
                 * Implementa le funzionalità che permettono di rimuovere uno spettacolo
                 */
                case "/remove":
                    authorize(session);
                    if(isAjax(request)) {
                        int showId = Integer.parseInt(request.getParameter("showId"));
                        if(showService.remove(showId)) {
                            jsonObject.put("result", "success");
                            sendJson(response, jsonObject);
                        } else
                            internalError();
                    }
                    break;

                /**
                 * Implementa le funzionalità che permettono di aggiungere uno spettacolo
                 */
                case "/add": {
                    authorize(session);

                    int roomId = Integer.parseInt(request.getParameter("room")),
                        filmId = Integer.parseInt(request.getParameter("filmId"));

                    LocalDate date = getLocalDateFromString(request, "date");
                    LocalTime time = getLocalTimeFromString(request, "time");

                    Film film = new Film();
                    film.setId(filmId);

                    Room room = new Room();
                    room.setId(roomId);

                    Show show = new Show(date, time);
                    show.setFilm(film);
                    show.setRoom(room);

                    if (showService.insert(show)) {
                        response.sendRedirect(getServletContext().getContextPath()+"/film/details?filmId="+filmId);
                    } else
                        internalError();
                    break;
                }

                /**
                 * Implementa le funzionalità che permettono di aggiornare uno spettacolo
                 */
                case "/update": {
                    authorize(session);

                    int showId = Integer.parseInt(request.getParameter("showId")),
                        filmId = Integer.parseInt(request.getParameter("filmId"));

                    LocalDate date = getLocalDateFromString(request, "date");
                    LocalTime time = getLocalTimeFromString(request, "time");

                    Show show = new Show(date, time);
                    show.setId(showId);

                    if(showService.update(show)) {
                        response.sendRedirect(getServletContext().getContextPath()+"/film/details?filmId="+filmId);
                    } else
                        internalError();
                    break;
                }

                /**
                 * Implementa le funzionalità che permettono di restituire la lista delle date e degli orari disponibili per modificare uno spettacolo (escluso dalla lista di ricerca)
                 */
                case "/get-show": {
                    authorize(session);
                    if(isAjax(request)) {
                        int showId = Integer.parseInt(request.getParameter("showId"));

                        Show show = showService.fetch(showId);
                        if(show != null) {
                            int roomId = show.getRoom().getId();
                            LocalDate date = show.getDate();

                            ArrayList<Show> showList = showService.fetchDaily(roomId, date, show);
                            if (!showList.isEmpty())
                                jsonObject.put("timeList", getAvailableDateList(show.getFilm().getLength(), date, showList));
                            else {
                                JSONArray list = new JSONArray();
                                for (int i = STARTING_HOUR; i <= ENDING_HOUR; i++)
                                    list.put(i + ":00");

                                jsonObject.put("timeList", list);
                            }

                            jsonObject.put("roomId", roomId);
                            jsonObject.put("date", date);
                            sendJson(response, jsonObject);
                        } else
                            notFound();
                    }
                    break;
                }

                /**
                 * Implementa le funzionalità che permettono di restituire la lista delle date e degli orari disponibili per inserire uno spettacolo
                 */
                case "/get-all-show": {
                    authorize(session);
                    if (isAjax(request)) {
                        int roomId = Integer.parseInt(request.getParameter("room")),
                            filmLength = Integer.parseInt(request.getParameter("film-length"));

                        String stringDate = request.getParameter("date");
                        if(!stringDate.isEmpty() && !stringDate.isBlank()) {
                            LocalDate date = getLocalDateFromString(request, "date");
                            ArrayList<Show> showList = showService.fetchDaily(roomId, date);
                            if (!showList.isEmpty()) {
                                jsonObject.put("timeList", getAvailableDateList(filmLength, date, showList));
                                sendJson(response, jsonObject);
                            } else {
                                JSONArray list = new JSONArray();
                                for (int i = STARTING_HOUR; i <= ENDING_HOUR; i++)
                                    list.put(i + ":00");

                                jsonObject.put("timeList", list);
                                sendJson(response, jsonObject);
                            }
                        }
                    }
                    break;
                }
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
     * Implementa la funzionalità che permettono di verificare che due date non siano sovrapposte
     * @param start1 data di inizio 1
     * @param end1 data di fine 1
     * @param start2 data di inizio 2
     * @param end2 data di fine 2
     * @return true se si sovrappongono, false
     */
    private boolean isOverlapping(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    /**
     * Implementa le funzionalità che permettono di convertire minuti in ore
     * @param filmMinutes minuti da convertire
     * @return numero di ore approssimato per eccesso per qualsiasi valore decimale
     */
    private long toHour(int filmMinutes) {
        double minutes = Math.ceil((double) filmMinutes/60); //approssimo per eccesso per qualsiasi valore decimale.
        return (long) minutes;
    }

    /**
     * Implementa le funzionalità che permettono di restituire la lista delle date e degli orari disponibili sotto forma di JSONArray
     * @param filmLength durata del film
     * @param date data dello spettacolo
     * @param showList lista spettacoli
     * @return
     */
    public JSONArray getAvailableDateList(int filmLength, LocalDate date, ArrayList<Show> showList) {
        long hourTime = toHour(filmLength) + 1; //Aggiungo 1 ora per liberare la sala
        LocalTime time = LocalTime.of(STARTING_HOUR, 0);
        LocalDateTime localDateTime = LocalDateTime.of(date, time);

        JSONArray list = new JSONArray();

        boolean skipFlag;
        do {
            skipFlag = false;
            for (Show show : showList) {
                LocalTime showTime = show.getTime();
                LocalDateTime showDateTime = LocalDateTime.of(date, showTime);
                long showLength = toHour(show.getFilm().getLength()) + 1;
                if (isOverlapping(localDateTime, localDateTime.plusHours(hourTime), showDateTime, showDateTime.plusHours(showLength))) {
                    skipFlag = true;
                    break;
                }
            }

            if (!skipFlag)
                list.put(localDateTime.getHour() + ":" + (localDateTime.getMinute() < 10 ? localDateTime.getMinute() + "0" : localDateTime.getMinute()));

            localDateTime = localDateTime.plusHours(1);
        } while (localDateTime.getHour() <= ENDING_HOUR);

        return list;
    }

    /**
     * Stabiliscono l'orario di apertura e chiusura del film
     */
    private static int STARTING_HOUR = 16, ENDING_HOUR = 22;
}
