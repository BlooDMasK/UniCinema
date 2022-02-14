package UniCinemaTest.FilmInfo.controller;

import Authentication.service.AuthenticationServiceMethods;
import FilmInfo.controller.FilmInfoServlet;
import FilmInfo.service.FilmServiceMethods;
import ReviewManager.service.ReviewServiceMethods;
import ShowManager.service.ShowServiceMethods;
import model.bean.Account;
import model.bean.Film;
import model.bean.Show;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import utils.Controller;
import utils.InvalidRequestException;
import utils.validator.AccountValidator;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class FilmInfoServletTest {

    /*
    Corrisponde a:
    authenticationServlet = new AuthenticationServlet();
    authenticationServlet = Mockito.spy(authenticationServlet);
     */
    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private HttpSession session;
    @Mock private Account account;
    @Mock private Film film;
    @Mock private Show show;
    @Mock private ArrayList<Film> filmList;
    @Mock private ArrayList<Show> showList;
    @Mock private FilmServiceMethods filmServiceMethods;
    @Mock private ShowServiceMethods showServiceMethods;
    @Mock private PrintWriter printWriter;
    @Mock private Iterator<Show> showIterator;
    @Mock private Iterator<Film> filmIterator;
    @Mock private Map<Integer, Film> filmMap;
    @Mock private JSONObject jsonObject;

    @Spy private FilmInfoServlet filmInfoServlet;

    @Before
    public void setUp() throws IOException {

        MockitoAnnotations.initMocks(this);

        when(filmInfoServlet.getServletConfig()).thenReturn(servletConfig);
        when(filmInfoServlet.getServletContext()).thenReturn(servletContext);

        when(servletContext.getInitParameter("basePath")).thenReturn("/views/");
        when(servletContext.getInitParameter("engine")).thenReturn(".jsp");

        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("accountSession")).thenReturn(account);

        when(filmInfoServlet.getSessionAccount(session)).thenReturn(account);

        when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        when(response.getWriter()).thenReturn(printWriter);

        filmInfoServlet.setFilmService(filmServiceMethods);
        filmInfoServlet.setShowService(showServiceMethods);
        filmInfoServlet.setJsonObject(jsonObject);
    }

    @Test
    public void doGetDetails() throws ServletException, IOException, SQLException {
        when(filmInfoServlet.getPath(request)).thenReturn("/details");
        when(request.getRequestDispatcher("/views/site/movie/details.jsp")).thenReturn(requestDispatcher);

        when(request.getParameter("filmId")).thenReturn("1");
        when(filmServiceMethods.fetch(1)).thenReturn(film);
        when(showServiceMethods.fetchAll(film)).thenReturn(showList);

        filmInfoServlet.doGet(request, response);

        verify(film).setShowList(showList);
        verify(request).setAttribute("film", film);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doGetDetailsFilmNull() throws ServletException, IOException, SQLException, InvalidRequestException {
        when(filmInfoServlet.getPath(request)).thenReturn("/details");

        when(request.getParameter("filmId")).thenReturn("1");
        when(filmServiceMethods.fetch(1)).thenReturn(null);

        doThrow(new InvalidRequestException("Risorsa non trovata", List.of("Risorsa non trovata"), HttpServletResponse.SC_NOT_FOUND))
                .when(filmInfoServlet).notFound();

        filmInfoServlet.doGet(request, response);

        verify(filmInfoServlet).notFound();
    }

    @Test
    public void doGetSchedule() throws SQLException, ServletException, IOException {
        when(filmInfoServlet.getPath(request)).thenReturn("/schedule");
        when(request.getRequestDispatcher("/views/site/movie/schedule.jsp")).thenReturn(requestDispatcher);

        when(showServiceMethods.fetchAll()).thenReturn(showList);

        when(showIterator.hasNext()).thenReturn(true, true, true, false);
        when(showIterator.next()).thenReturn(show, show, show, show);
        when(showList.iterator()).thenReturn(showIterator);

        when(filmMap.containsKey(anyInt())).thenReturn(true);
        when(filmServiceMethods.fetch(anyInt())).thenReturn(film); //Caso in cui filmOptional != null
        when(film.getShowList()).thenReturn(showList);

        when(show.getFilm()).thenReturn(film);

        filmInfoServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostSearch() throws SQLException, ServletException, IOException {
        when(filmInfoServlet.getPath(request)).thenReturn("/search");

        when(request.getParameter("title")).thenReturn("Spiderman");
        when(filmServiceMethods.search("Spiderman")).thenReturn(filmList);

        when(filmIterator.hasNext()).thenReturn(true, false);
        when(filmIterator.next()).thenReturn(film, film);
        when(filmList.iterator()).thenReturn(filmIterator);

        when(film.toJson()).thenReturn(jsonObject);

        filmInfoServlet.doPost(request, response);

        verify(filmInfoServlet).sendJson(response, jsonObject);
    }

    @Test
    public void doPostSearchNotAjax() throws SQLException, ServletException, IOException {
        when(filmInfoServlet.getPath(request)).thenReturn("/search");
        when(request.getHeader("X-Requested-With")).thenReturn("HttpRequest");

        filmInfoServlet.doPost(request, response);

        assertFalse(filmInfoServlet.isAjax(request));
    }
}
