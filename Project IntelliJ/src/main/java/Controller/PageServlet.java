package Controller;

import Controller.api.Controller;
import Controller.api.ErrorHandler;
import Model.film.Film;
import Model.film.FilmDAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PageServlet", value = "/pages/*")
public class PageServlet extends Controller implements ErrorHandler {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String path = getPath(request);
            switch (path) {
                case "/": //Homepage
                    List<Film> filmCarousel = new FilmDAO().fetchLastReleases(3);
                    List<Film> filmLastReleases = new FilmDAO().fetchLastReleases(6);
                    List<Film> filmComingSoon = new FilmDAO().fetchComingSoon(6);

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
