package FilmInfo.controller;

import FilmInfo.service.FilmService;
import FilmInfo.service.FilmServiceMethods;
import ShowManager.service.ShowService;
import ShowManager.service.ShowServiceMethods;
import model.bean.Film;
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
import java.util.*;

import static java.lang.Math.round;

@WebServlet(name = "FilmInfoServlet", value = "/film/*")
public class FilmInfoServlet extends Controller implements ErrorHandler {
    /**
     * I service per effettuare le operazioni di persistenza.
     */
    private FilmService filmService;
    private ShowService showService;
    private JSONObject jsonObject;

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void setFilmService(FilmService filmService) {
        this.filmService = filmService;
    }

    public void setShowService(ShowService showService) {
        this.showService = showService;
    }

    public FilmInfoServlet() throws SQLException {
        filmService = new FilmServiceMethods();
        showService = new ShowServiceMethods();
        jsonObject = new JSONObject();
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

            switch (path) {
                /**
                 * Implementa la funzionalità di visualizzazione dettagli di un film.
                 */
                case "/details": { //Dettagli film
                    int filmId = Integer.parseInt(request.getParameter("filmId"));
                    Film film = filmService.fetch(filmId);
                    if (film != null) {
                        ArrayList<Show> showList = showService.fetchAll(film);

                        film.setShowList(showList);
                        request.setAttribute("film", film);

                        request.getRequestDispatcher(view("site/movie/details")).forward(request, response);
                    } else
                        notFound();
                }
                break;

                /**
                 * Implementa la funzionalità di visualizzazione programmazione film.
                 */
                case "/schedule": { //Palinsesto, Programmazione
                    ArrayList<Show> showList = showService.fetchAll();

                    Map<Integer, Film> filmMap = new LinkedHashMap<>();
                    Film filmOptional;
                    int filmId;
                    for (Show show : showList) {
                        filmId = show.getFilm().getId();
                        if (!filmMap.containsKey(filmId)) {
                            filmOptional = filmService.fetch(filmId);
                            if (filmOptional != null) {
                                filmOptional.getShowList().add(show);
                                filmMap.put(filmId, filmOptional);
                            }
                        } else
                            filmMap.get(filmId).getShowList().add(show);

                        Collections.sort(filmMap.get(filmId).getShowList());
                    }
                    request.setAttribute("filmList", new ArrayList<>(filmMap.values()));

                    request.getRequestDispatcher(view("site/movie/schedule")).forward(request, response);
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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String path = getPath(request);

            switch(path) {
                case "/search":
                    if(isAjax(request)) {
                        String title = request.getParameter("title");
                        ArrayList<Film> filmList = filmService.search(title);

                        JSONArray list = new JSONArray();
                        if(filmList != null)
                            for(Film film : filmList)
                                list.put(film.toJson());

                        jsonObject.put("filmList", list);

                        sendJson(response, jsonObject);
                    }
                    break;
            }
        } catch (SQLException ex) {
            log(ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
