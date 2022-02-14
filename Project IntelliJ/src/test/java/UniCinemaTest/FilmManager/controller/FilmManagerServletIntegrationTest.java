package UniCinemaTest.FilmManager.controller;

import FilmInfo.service.FilmService;
import FilmInfo.service.FilmServiceMethods;
import FilmManager.controller.FilmManagerServlet;
import FilmManager.service.FilmManagerService;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FilmManagerServletIntegrationTest {

    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private PrintWriter printWriter;
    @Mock private Part cover, poster;
    @Mock private InputStream inputStream;
    @Mock private RequestDispatcher requestDispatcher;

    @Spy private FilmManagerServlet filmManagerServlet;
    private FilmManagerService filmManagerService;
    private FilmService filmService;
    private FilmValidator filmValidator;

    @Before
    public void setUp() throws IOException, InvalidRequestException, SQLException {

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

        filmManagerService = new FilmManagerServiceMethods();
        filmService = new FilmServiceMethods();
        filmValidator = new FilmValidator();
    }

    @Test
    public void doPostAddFilm() throws ServletException, IOException, SQLException {
        when(filmManagerServlet.getPath(request)).thenReturn("/add");
        when(request.getRequestDispatcher("/views/site/movie/form.jsp")).thenReturn(requestDispatcher);

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

        RequestValidator requestValidator = filmValidator.validateFilm(request);
        assertFalse(requestValidator.hasErrors());

        Film film = new Film();
        filmManagerServlet.setFilmValues(request, film);
        assertNotNull(film.getActorList());
        assertNotNull(film.getDirectorList());
        assertNotNull(film.getProductionList());
        assertNotNull(film.getHouseProductionList());
        assertEquals(film.getDatePublishing(), LocalDate.of(2022, 2, 5));
        assertEquals(film.getLength(), 150);
        assertEquals(film.getTitle(), "Spider-Man: No Way Home");
        assertEquals(film.getCover(), "cover.png");
        assertEquals(film.getPoster(), "poster.png");
        assertEquals(film.getGenre(), 3);

        boolean inserted = filmManagerService.insert(film);
        assertTrue(inserted);

        when(poster.getInputStream()).thenReturn(inputStream);
        when(cover.getInputStream()).thenReturn(inputStream);

        filmManagerServlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }
}
