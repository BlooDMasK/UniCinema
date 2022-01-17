package FilmInfo.controller;

import FilmInfo.service.FilmService;
import FilmInfo.service.FilmServiceMethods;
import ReviewManager.service.ReviewService;
import ReviewManager.service.ReviewServiceMethods;
import ShowManager.service.ShowService;
import ShowManager.service.ShowServiceMethods;
import model.bean.Film;
import model.bean.Review;
import model.bean.Show;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.Controller;
import utils.ErrorHandler;
import utils.InvalidRequestException;
import utils.Paginator;

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
    private FilmService filmService = new FilmServiceMethods();
    private ShowService showService = new ShowServiceMethods();

    /**
     * Implementa le funzionalità svolte durante una chiamata di tipo GET
     * @param request oggetto rappresentante la chiamata Http request
     * @param response oggetto rappresentante la chiamata Http response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            HttpSession session = request.getSession();

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
                    Optional<Film> film = filmService.fetch(filmId);
                    if (film.isPresent()) {
                        ArrayList<Show> showList = showService.fetchAll(film.get());

                        film.get().setShowList(showList);
                        request.setAttribute("film", film.get());

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
                    Optional<Film> filmOptional;
                    int filmId;
                    for (Show show : showList) {
                        filmId = show.getFilm().getId();
                        if (!filmMap.containsKey(filmId)) {
                            filmOptional = filmService.fetch(filmId);
                            if (filmOptional.isPresent()) {
                                filmOptional.get().getShowList().add(show);
                                filmMap.put(filmId, filmOptional.get());
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

                        JSONObject root = new JSONObject();
                        root.put("filmList", list);

                        sendJson(response, root);
                    }
                    break;
            }
        } catch (SQLException ex) {
            log(ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}
