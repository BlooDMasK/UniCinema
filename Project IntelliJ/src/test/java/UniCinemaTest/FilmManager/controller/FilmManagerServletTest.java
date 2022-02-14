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
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
    @Mock private Part cover, poster;
    @Mock private InputStream inputStream;

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

    @Test
    public void doPostAdd() throws ServletException, IOException, SQLException {
        when(filmManagerServlet.getPath(request)).thenReturn("/add");
        when(request.getRequestDispatcher("/views/site/movie/form.jsp")).thenReturn(requestDispatcher);

        when(filmValidator.validateFilm(request)).thenReturn(requestValidator);

        setFilmValueForTest();

        when(filmManagerServiceMethods.insert(any(Film.class))).thenReturn(true);

        when(poster.getInputStream()).thenReturn(inputStream);
        when(cover.getInputStream()).thenReturn(inputStream);

        filmManagerServlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostUpdate() throws ServletException, IOException {
        when(filmManagerServlet.getPath(request)).thenReturn("/update");
        when(request.getAttribute("back")).thenReturn("/views/site/movie/form.jsp");
        when(filmValidator.validateUpdateFilm(request)).thenReturn(requestValidator);

        ArrayList<Actor> actorList = new ArrayList<>(List.of(
                new Actor(1, "Tom", "Holland"),
                new Actor(2, "Zendaya", "Coleman")
        ));
        ArrayList<Director> directorList = new ArrayList<>(List.of(
                new Director(1, "Jon", "Watts")
        ));
        ArrayList<Production> productionList = new ArrayList<>(List.of(
                new Production(1, "Kevin", "Feige"),
                new Production(2, "Amy", "Pascal")
        ));
        ArrayList<HouseProduction> houseProductionList = new ArrayList<>(List.of(
                new HouseProduction(1, "Marvel Studios"),
                new HouseProduction(2, "Columbia Pictures"),
                new HouseProduction(3, "Pascal Pictures")
        ));
        Film film = new Film(1, 150, 3, "Spider-Man: No Way Home", "Peter Parker è un ragazzo",
                "cover.png", "poster.png", LocalDate.of(2022, 2, 5), actorList,
                directorList, houseProductionList, productionList);

        when(session.getAttribute("film")).thenReturn(film);

        hasModifiedFilmValueForTest();

        filmManagerServlet.doPost(request, response);

        verify(response).sendRedirect(servletContext.getContextPath()+"/film-manager/update?filmId="+film.getId());
    }

    private void hasModifiedFilmValueForTest() throws ServletException, IOException {
        when(request.getParameter("length")).thenReturn("150");
        when(request.getParameter("genre")).thenReturn("3");
        when(request.getParameter("plot")).thenReturn("Peter Parker è un ragazzo");
        when(request.getParameter("title")).thenReturn("Spider-Man: No Way Home");

        when(request.getPart("cover")).thenReturn(cover);
        when(request.getPart("poster")).thenReturn(poster);
        when(request.getPart("cover").getSubmittedFileName()).thenReturn("");
        when(request.getPart("poster").getSubmittedFileName()).thenReturn("");

        when(request.getParameter("date-publishing")).thenReturn("2022-02-05");

        String[] stringArray;

        //Attori
        stringArray = new String[]{"Tom", "Zendaya"};
        when(request.getParameterValues("ActorsFirstname")).thenReturn(stringArray);
        stringArray = new String[]{"Holland", "Coleman"};
        when(request.getParameterValues("ActorsLastname")).thenReturn(stringArray);
        stringArray = new String[]{"1", "2"};
        when(request.getParameterValues("ActorsId")).thenReturn(stringArray);

        //Registi
        stringArray = new String[]{"Jon"};
        when(request.getParameterValues("DirectorsFirstname")).thenReturn(stringArray);
        stringArray = new String[]{"Watts"};
        when(request.getParameterValues("DirectorsLastname")).thenReturn(stringArray);
        stringArray = new String[]{"1"};
        when(request.getParameterValues("DirectorsId")).thenReturn(stringArray);

        //Produzione
        stringArray = new String[]{"Kevin", "Amy"};
        when(request.getParameterValues("ProductionFirstname")).thenReturn(stringArray);
        stringArray = new String[]{"Feige", "Pascal"};
        when(request.getParameterValues("ProductionLastname")).thenReturn(stringArray);
        stringArray = new String[]{"1", "2"};
        when(request.getParameterValues("ProductionId")).thenReturn(stringArray);

        //Casa di produzione
        stringArray = new String[]{"Marvel Studios", "Columbia Pictures", "Pascal Pictures"};
        when(request.getParameterValues("HouseProductionName")).thenReturn(stringArray);
        stringArray = new String[]{"1", "2", "3"};
        when(request.getParameterValues("HouseProductionId")).thenReturn(stringArray);
    }

    private void setFilmValueForTest() throws ServletException, IOException {
        String[] stringArray;

        //Attori
        stringArray = new String[]{"Tom", "Zendaya"};
        when(request.getParameterValues("ActorsFirstname")).thenReturn(stringArray);
        stringArray = new String[]{"Holland", "Coleman"};
        when(request.getParameterValues("ActorsLastname")).thenReturn(stringArray);
        stringArray = new String[]{"1", "2"};
        when(request.getParameterValues("ActorsId")).thenReturn(stringArray);

        //Registi
        stringArray = new String[]{"Jon"};
        when(request.getParameterValues("DirectorsFirstname")).thenReturn(stringArray);
        stringArray = new String[]{"Watts"};
        when(request.getParameterValues("DirectorsLastname")).thenReturn(stringArray);
        stringArray = new String[]{"1"};
        when(request.getParameterValues("DirectorsId")).thenReturn(stringArray);

        //Produzione
        stringArray = new String[]{"Kevin", "Amy"};
        when(request.getParameterValues("ProductionFirstname")).thenReturn(stringArray);
        stringArray = new String[]{"Feige", "Pascal"};
        when(request.getParameterValues("ProductionLastname")).thenReturn(stringArray);
        stringArray = new String[]{"1", "2"};
        when(request.getParameterValues("ProductionId")).thenReturn(stringArray);

        //Casa di produzione
        stringArray = new String[]{"Marvel Studios", "Columbia Pictures", "Pascal Pictures"};
        when(request.getParameterValues("HouseProductionName")).thenReturn(stringArray);
        stringArray = new String[]{"1", "2", "3"};
        when(request.getParameterValues("HouseProductionId")).thenReturn(stringArray);

        when(request.getParameter("date-publishing")).thenReturn("2022-02-05");

        when(request.getPart("cover")).thenReturn(cover);
        when(request.getPart("poster")).thenReturn(poster);

        when(request.getPart("cover").getSubmittedFileName()).thenReturn("cover.png");
        when(request.getPart("poster").getSubmittedFileName()).thenReturn("poster.png");

        when(request.getParameter("genre")).thenReturn("3");
        when(request.getParameter("length")).thenReturn("150");
        when(request.getParameter("plot")).thenReturn("Peter Parker è un ragazzo");
        when(request.getParameter("title")).thenReturn("Spider-Man: No Way Home");
    }

    @Test
    public void doGetNotValid() throws ServletException, IOException {
        when(filmManagerServlet.getPath(request)).thenReturn("/");

        filmManagerServlet.doGet(request, response);

        assertEquals(filmManagerServlet.getPath(request), "/");
    }

    @Test
    public void doPostNotValid() throws ServletException, IOException {
        when(filmManagerServlet.getPath(request)).thenReturn("/");

        filmManagerServlet.doGet(request, response);

        assertEquals(filmManagerServlet.getPath(request), "/");
    }
}
