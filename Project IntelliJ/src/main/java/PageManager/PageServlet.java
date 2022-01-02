package PageManager;

import utils.Controller;
import utils.ErrorHandler;
import model.bean.Film;
import model.dao.FilmDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "PageServlet", value = "/pages/*")
public class PageServlet extends Controller implements ErrorHandler {

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
            String path = getPath(request);
            FilmDAO filmDAO = new FilmDAO();
            switch (path) {
                case "/": //Homepage
                    List<Film> filmCarousel = filmDAO.fetchLastReleases(3);
                    List<Film> filmLastReleases = filmDAO.fetchLastReleases(6);
                    List<Film> filmComingSoon = filmDAO.fetchComingSoon(6);

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
            System.out.println(ex.getMessage());
        } /*catch (InvalidRequestException e) {
            log(e.getMessage());
            e.handle(request, response);
        }*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
