package FilmManager.controller;

import FilmInfo.service.FilmService;
import FilmInfo.service.FilmServiceMethods;
import FilmManager.service.FilmManagerService;
import FilmManager.service.FilmManagerServiceMethods;
import model.bean.*;
import utils.Alert;
import utils.Controller;
import utils.ErrorHandler;
import utils.InvalidRequestException;
import utils.validator.FilmValidator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "FilmManagerServlet", value = "/film-manager/*")
@MultipartConfig
public class FilmManagerServlet extends Controller implements ErrorHandler {

    FilmManagerService filmManagerService;
    FilmService filmInfoService;
    FilmValidator filmValidator;

    public void setFilmValidator(FilmValidator filmValidator) {
        this.filmValidator = filmValidator;
    }

    public void setFilmManagerService(FilmManagerService filmManagerService) {
        this.filmManagerService = filmManagerService;
    }

    public void setFilmInfoService(FilmService filmInfoService) {
        this.filmInfoService = filmInfoService;
    }

    public FilmManagerServlet() {
        filmManagerService = new FilmManagerServiceMethods();
        filmInfoService = new FilmServiceMethods();
        filmValidator = new FilmValidator();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String path = getPath(request);
            String reloadPath;
            switch(path) {
                case "/remove": {
                    authorize(session);
                    reloadPath = request.getContextPath() + "/film/schedule";
                    request.setAttribute("reload", reloadPath);
                    int filmId = Integer.parseInt(request.getParameter("filmId"));
                    if (filmManagerService.removeFilm(filmId)) {

                        session.setAttribute("alert", new Alert(List.of("Film rimosso con successo."), "success"));
                        response.sendRedirect(reloadPath);
                    } else
                        internalError("Si è verificato un errore durante la rimozione del film.");
                    break;
                }

                case "/add":
                    authorize(session);
                    session.removeAttribute("alert");
                    session.removeAttribute("film");
                    session.removeAttribute("datePublishing");
                    session.removeAttribute("filmToJson");
                    session.setAttribute("formType", "add");
                    request.getRequestDispatcher(view("site/movie/form")).forward(request, response);
                    break;

                case "/update": {
                    authorize(session);
                    int filmId = Integer.parseInt(request.getParameter("filmId"));
                    Film film = filmInfoService.fetch(filmId);
                    if (film != null) {
                        session.setAttribute("film", film);
                        session.setAttribute("formType", "update");
                        session.setAttribute("datePublishing", film.getDatePublishing());
                        session.setAttribute("filmToJson", film.toJson());

                        request.getRequestDispatcher(view("site/movie/form")).forward(request, response);
                        session.removeAttribute("alert");
                    } else
                        notFound();
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
            HttpSession session = request.getSession();
            String path = getPath(request);

            switch(path) {
                case "/add": {
                    authorize(session);
                    request.setAttribute("back", view("site/movie/form"));
                    validate(filmValidator.validateFilm(request));

                    Film film = new Film();
                    setFilmValues(request, film);

                    if (filmManagerService.insert(film)) {
                        film.writeCover(getUploadPath(), request.getPart("cover"));
                        film.writePoster(getUploadPath(), request.getPart("poster"));

                        request.setAttribute("alert", new Alert(List.of("Film aggiunto con successo."), "success"));
                        request.getRequestDispatcher(view("site/movie/form")).forward(request, response);
                    } else
                        internalError();
                    break;
                }

                case "/update": {
                    authorize(session);
                    request.setAttribute("back", view("site/movie/form"));

                    validate(filmValidator.validateUpdateFilm(request));

                    Film oldFilm = (Film) session.getAttribute("film");
                    if(!hasModifiedFilm(request, oldFilm)) {
                        session.setAttribute("alert", new Alert(List.of("Devi modificare almeno un campo."), "danger"));
                        response.sendRedirect(getServletContext().getContextPath()+"/film-manager/update?filmId="+oldFilm.getId());
                        break;
                    }

                    Film film = new Film();
                    film.setId(oldFilm.getId());
                    film.setCover(oldFilm.getCover());
                    film.setPoster(oldFilm.getPoster());
                    setFilmValues(request, film);

                    if (filmManagerService.update(film)) {

                        String cover = request.getPart("cover").getSubmittedFileName();
                        if(!cover.isEmpty())
                            film.writeCover(getUploadPath(), request.getPart("cover"));

                        String poster = request.getPart("poster").getSubmittedFileName();
                        if(!poster.isEmpty())
                            film.writePoster(getUploadPath(), request.getPart("poster"));

                        session.setAttribute("alert", new Alert(List.of("Film modificato con successo."), "success"));
                        //request.getRequestDispatcher(view("site/movie/form")).forward(request, response);
                        response.sendRedirect(getServletContext().getContextPath()+"/film-manager/update?filmId="+film.getId());
                    } else
                        internalError();
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

    private boolean hasModifiedFilm(HttpServletRequest request, Film oldFilm) throws ServletException, IOException {
        int length = Integer.parseInt(request.getParameter("length")),
            genre = Integer.parseInt(request.getParameter("genre"));

        String  title = request.getParameter("title"),
                plot = request.getParameter("plot"),
                cover = request.getPart("cover").getSubmittedFileName(),
                poster = request.getPart("poster").getSubmittedFileName();

        LocalDate datePublishing = getLocalDateFromString(request, "date-publishing");

        ArrayList<String> actorsFirstname = getParamsArrayList(request, "ActorsFirstname");
        ArrayList<String> actorsLastname = getParamsArrayList(request, "ActorsLastname");
        ArrayList<String> actorsId = getParamsArrayList(request, "ActorsId");
        int actorsSize = actorsFirstname.size();

        ArrayList<String> directorsFirstname = getParamsArrayList(request, "DirectorsFirstname");
        ArrayList<String> directorsLastname = getParamsArrayList(request, "DirectorsLastname");
        ArrayList<String> directorsId = getParamsArrayList(request, "DirectorsId");
        int directorsSize = directorsFirstname.size();

        ArrayList<String> productionFirstname = getParamsArrayList(request, "ProductionFirstname");
        ArrayList<String> productionLastname = getParamsArrayList(request, "ProductionLastname");
        ArrayList<String> productionId = getParamsArrayList(request, "ProductionId");
        int productionSize = productionFirstname.size();

        ArrayList<String> houseProductionName = getParamsArrayList(request, "HouseProductionName");
        ArrayList<String> houseProductionId = getParamsArrayList(request, "HouseProductionId");
        int houseProductionSize = houseProductionName.size();

        if(length != oldFilm.getLength())
            return true;

        if(genre != oldFilm.getGenre())
            return true;

        if(!title.equals(oldFilm.getTitle()))
            return true;

        if(!plot.equals(oldFilm.getPlot()))
            return true;

        if(!cover.isEmpty())
            return true;

        if(!poster.isEmpty())
            return true;

        if(!datePublishing.equals(oldFilm.getDatePublishing()))
            return true;

        if(actorsSize != oldFilm.getActorList().size())
            return true;

        if(directorsSize != oldFilm.getDirectorList().size())
            return true;

        if(houseProductionSize != oldFilm.getHouseProductionList().size())
            return true;

        if(productionSize != oldFilm.getProductionList().size())
            return true;

        String firstname, lastname, name;
        int occurrenceCounter = 0, id;
        for(int i = 0; i < actorsSize; i++)
        {
            firstname = actorsFirstname.get(i);
            lastname = actorsLastname.get(i);
            id = Integer.parseInt(actorsId.get(i));

            for(Actor actor : oldFilm.getActorList()) {
                if (actor.getId() == id && firstname.equals(actor.getFirstname()) && lastname.equals(actor.getLastname())) {
                    occurrenceCounter++;
                    break;
                }
            }
        }

        if(occurrenceCounter != actorsSize)
            return true;

        occurrenceCounter = 0;
        for(int i = 0; i < directorsSize; i++)
        {
            firstname = directorsFirstname.get(i);
            lastname = directorsLastname.get(i);
            id = Integer.parseInt(directorsId.get(i));

            for(Director director : oldFilm.getDirectorList()) {
                if (director.getId() == id && firstname.equals(director.getFirstname()) && lastname.equals(director.getLastname())) {
                    occurrenceCounter++;
                    break;
                }
            }
        }

        if(occurrenceCounter != directorsSize)
            return true;

        occurrenceCounter = 0;
        for(int i = 0; i < productionSize; i++)
        {
            firstname = productionFirstname.get(i);
            lastname = productionLastname.get(i);
            id = Integer.parseInt(productionId.get(i));

            for(Production production : oldFilm.getProductionList()) {
                if (production.getId() == id && firstname.equals(production.getFirstname()) && lastname.equals(production.getLastname())) {
                    occurrenceCounter++;
                    break;
                }
            }
        }

        if(occurrenceCounter != productionSize)
            return true;

        occurrenceCounter = 0;
        for(int i = 0; i < houseProductionSize; i++)
        {
            name = houseProductionName.get(i);
            id = Integer.parseInt(houseProductionId.get(i));

            for(HouseProduction houseProduction : oldFilm.getHouseProductionList()) {
                if (houseProduction.getId() == id && name.equals(houseProduction.getName())) {
                    occurrenceCounter++;
                    break;
                }
            }
        }

        if(occurrenceCounter != houseProductionSize)
            return true;

        return false;
    }

    private void setFilmValues(HttpServletRequest request, Film film) throws ServletException, IOException {
        ArrayList<String> actorsFirstname = getParamsArrayList(request, "ActorsFirstname");
        ArrayList<String> actorsLastname = getParamsArrayList(request, "ActorsLastname");
        ArrayList<String> actorsId = getParamsArrayList(request, "ActorsId");
        int actorsSize = actorsFirstname.size();


        ArrayList<String> directorsFirstname = getParamsArrayList(request, "DirectorsFirstname");
        ArrayList<String> directorsLastname = getParamsArrayList(request, "DirectorsLastname");
        ArrayList<String> directorsId = getParamsArrayList(request, "DirectorsId");
        int directorsSize = directorsFirstname.size();

        ArrayList<String> productionFirstname = getParamsArrayList(request, "ProductionFirstname");
        ArrayList<String> productionLastname = getParamsArrayList(request, "ProductionLastname");
        ArrayList<String> productionId = getParamsArrayList(request, "ProductionId");
        int productionSize = productionFirstname.size();

        ArrayList<String> houseProductionName = getParamsArrayList(request, "HouseProductionName");
        ArrayList<String> houseProductionId = getParamsArrayList(request, "HouseProductionId");
        int houseProductionSize = houseProductionName.size();

        LocalDate datePublishing = getLocalDateFromString(request, "date-publishing");

        String cover = request.getPart("cover").getSubmittedFileName();
        if(!cover.isEmpty())
            film.setCover(cover);

        String poster = request.getPart("poster").getSubmittedFileName();
        if(!poster.isEmpty())
            film.setPoster(poster);

        film.setDatePublishing(datePublishing);
        film.setGenre(Integer.parseInt(request.getParameter("genre")));
        film.setLength(Integer.parseInt(request.getParameter("length")));
        film.setPlot(request.getParameter("plot"));
        film.setTitle(request.getParameter("title"));

        Actor actor;
        Director director;
        Production production;
        HouseProduction houseProduction;
        String firstname, lastname, name;
        int id;
        //"0"+id serve ad evitare eccezioni nel caso in cui la stringa fosse vuota
        for (int i = 0; i < actorsSize; i++) {
            firstname = actorsFirstname.get(i);
            lastname = actorsLastname.get(i);
            id = Integer.parseInt("0"+actorsId.get(i));

            actor = new Actor(id, firstname, lastname, film);
            film.addActor(actor);
        }

        for (int i = 0; i < directorsSize; i++) {
            firstname = directorsFirstname.get(i);
            lastname = directorsLastname.get(i);
            id = Integer.parseInt("0"+directorsId.get(i));

            director = new Director(id, firstname, lastname, film);
            film.addDirector(director);
        }

        for (int i = 0; i < productionSize; i++) {
            firstname = productionFirstname.get(i);
            lastname = productionLastname.get(i);
            id = Integer.parseInt("0"+productionId.get(i));

            production = new Production(id, firstname, lastname, film);
            film.addProduction(production);
        }

        for (int i = 0; i < houseProductionSize; i++) {
            name = houseProductionName.get(i);
            id = Integer.parseInt("0"+houseProductionId.get(i));

            houseProduction = new HouseProduction(id, name, film);
            film.addHouseProduction(houseProduction);
        }
    }
}
