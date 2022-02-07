package UniCinemaTest.FilmManager.controller;

import FilmInfo.service.FilmServiceMethods;
import FilmManager.controller.FilmManagerServlet;
import FilmManager.service.FilmManagerServiceMethods;
import model.bean.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import utils.Alert;
import utils.InvalidRequestException;
import utils.validator.FilmValidator;
import utils.validator.RequestValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class FilmManagerServletTest {

    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private HttpSession session;
    @Mock private PrintWriter printWriter;
    @Mock private FilmManagerServiceMethods filmManagerServiceMethods;
    @Mock private FilmServiceMethods filmServiceMethods;
    @Mock private FilmValidator filmValidator;
    @Mock private Film film;
    @Mock private RequestValidator requestValidator;
    @Mock private ArrayList<String> actorsFirstName, actorsLastName, actorsId,
                                    directorsFirstName, directorsLastName, directorsId,
                                    productionFirstName, productionLastname, productionId,
                                    houseProductionName, houseProductionId;
    @Mock private Actor actor;
    @Mock private Director director;
    @Mock private Production production;
    @Mock private HouseProduction houseProduction;


    @Spy private FilmManagerServlet filmManagerServlet;

    @Before
    public void setUp() throws IOException, InvalidRequestException {

        MockitoAnnotations.initMocks(this);

        when(filmManagerServlet.getServletConfig()).thenReturn(servletConfig);
        when(filmManagerServlet.getServletContext()).thenReturn(servletContext);

        when(servletContext.getInitParameter("basePath")).thenReturn("/views/");
        when(servletContext.getInitParameter("engine")).thenReturn(".jsp");

        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);

        when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        when(response.getWriter()).thenReturn(printWriter);

        when(request.getContextPath()).thenReturn("UniCinema_war_exploded");

        doNothing().when(filmManagerServlet).authorize(session);

        filmManagerServlet.setFilmManagerService(filmManagerServiceMethods);
        filmManagerServlet.setFilmValidator(filmValidator);
        filmManagerServlet.setFilmInfoService(filmServiceMethods);
    }


    @Test
    public void doGetRemove() throws SQLException, ServletException, IOException, InvalidRequestException {
        when(filmManagerServlet.getPath(request)).thenReturn("/remove");

        when(request.getParameter("filmId")).thenReturn("1");
        when(filmManagerServiceMethods.removeFilm(1)).thenReturn(true);

        String reloadPath = request.getContextPath() + "/film/schedule";

        filmManagerServlet.doGet(request, response);

        verify(filmManagerServlet).authorize(session);
        verify(request).setAttribute("reload", reloadPath);
        verify(response).sendRedirect(reloadPath);
    }

    @Test
    public void doGetAdd() throws ServletException, IOException, InvalidRequestException {
        when(filmManagerServlet.getPath(request)).thenReturn("/add");
        when(request.getRequestDispatcher("/views/site/movie/form.jsp")).thenReturn(requestDispatcher);

        filmManagerServlet.doGet(request, response);

        verify(filmManagerServlet).authorize(session);
        verify(session).removeAttribute("alert");
        verify(session).removeAttribute("film");
        verify(session).removeAttribute("datePublishing");
        verify(session).removeAttribute("filmToJson");
        verify(session).setAttribute("formType", "add");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doGetUpdate() throws ServletException, IOException, SQLException {
        when(filmManagerServlet.getPath(request)).thenReturn("/update");
        when(request.getRequestDispatcher("/views/site/movie/form.jsp")).thenReturn(requestDispatcher);

        when(request.getParameter("filmId")).thenReturn("1");
        when(filmServiceMethods.fetch(1)).thenReturn(film);

        filmManagerServlet.doGet(request, response);

        verify(session).setAttribute("film", film);
        verify(session).setAttribute("formType", "update");
        verify(session).setAttribute("datePublishing", film.getDatePublishing());
        verify(session).setAttribute("filmToJson", film.toJson());
        verify(requestDispatcher).forward(request, response);
        verify(session).removeAttribute("alert");
    }

    //TODO: dopostadd
    @Test
    public void doPostAdd() throws ServletException, IOException {
        when(filmManagerServlet.getPath(request)).thenReturn("/add");
        when(request.getRequestDispatcher("/views/site/movie/form.jsp")).thenReturn(requestDispatcher);

        when(filmValidator.validateFilm(request)).thenReturn(requestValidator);

/*
        String[] stringArray;

        stringArray = new String[]{"Tom"};
        when(request.getParameterValues("ActorsFirstname")).thenReturn(stringArray);
        when(filmManagerServlet.getParamsArrayList(request, "ActorsFirstname")).thenReturn(actorsFirstName);

        stringArray = new String[]{"Holland"};
        when(request.getParameterValues("ActorsLastname")).thenReturn(stringArray);
        when(filmManagerServlet.getParamsArrayList(request, "ActorsLastname")).thenReturn(actorsLastName);

        stringArray = new String[]{"1"};
        when(request.getParameterValues("ActorsId")).thenReturn(stringArray);
        when(filmManagerServlet.getParamsArrayList(request, "ActorsId")).thenReturn(actorsId);

        String[] actorsFirstName = {"Tom"},
                 actorsLastName = {"Holland"},
                 actorsId = {"1"};

        when(request.getParameterValues("ActorsFirstName")).thenReturn(actorsFirstName);
        when(request.getParameterValues("ActorsLastName")).thenReturn(actorsLastName);
        when(request.getParameterValues("ActorsId")).thenReturn(actorsId);
*/
        filmManagerServlet.doPost(request, response);

        /*
        verify(film).setCover(anyString());
        verify(film).setPoster(anyString());
        verify(film).setDatePublishing((LocalDate) anyObject());
        verify(film).setGenre(anyInt());
        verify(film).setLength(anyInt());
        verify(film).setPlot(anyString());
        verify(film).setTitle(anyString());
        verify(film).addActor(actor);
        verify(film).addDirector(director);
        verify(film).addProduction(production);
        verify(film).addHouseProduction(houseProduction);*/
    }

    @Test
    public void doPostUpdate() {

    }
}
