package PageManager;

import FilmInfo.service.FilmService;
import FilmInfo.service.FilmServiceMethods;
import utils.Controller;
import utils.ErrorHandler;
import model.bean.Film;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "PageServlet", value = "/pages/*")
public class PageServlet extends Controller implements ErrorHandler {

    /**
     * {@link FilmService}
     */
    FilmService filmService;

    public PageServlet() throws SQLException {
        this.filmService = new FilmServiceMethods();
    }

    /**
     * Metodo che permette di settare il FilmServiceMethods e la sua implementazione
     * @param filmService
     */
    public void setFilmService(FilmServiceMethods filmService) {
        this.filmService = filmService;
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
            String path = getPath(request);
            switch (path) {
                /**
                 * Implementa la funzionalità di visualizzazione dell'homepage
                 */
                case "/": //Homepage
                    ArrayList<Film> filmCarousel = filmService.fetchLastReleases(3);
                    ArrayList<Film> filmLastReleases = filmService.fetchLastReleases(6);
                    ArrayList<Film> filmComingSoon = filmService.fetchComingSoon(6);

                    request.setAttribute("filmCarousel", filmCarousel);
                    request.setAttribute("filmLastReleases", filmLastReleases);
                    request.setAttribute("filmComingSoon", filmComingSoon);

                    //Nella homepage getto gli ultimi 3 film usciti
                    request.getRequestDispatcher(view("site/homepage")).forward(request, response);
                    break;

            }
        } catch (SQLException ex) {
            log(ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
