package UniCinemaTest.PageManagerTest.controller;

import FilmInfo.service.FilmServiceMethods;
import FilmManager.service.FilmManagerServiceMethods;
import PageManager.PageServlet;
import model.bean.Film;
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
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class PageServletTest {

    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private PrintWriter printWriter;
    @Mock private FilmServiceMethods filmServiceMethods;
    @Mock private ArrayList<Film> filmList;

    @Spy private PageServlet pageServlet;

    @Before
    public void setUp() throws IOException, InvalidRequestException {
        MockitoAnnotations.initMocks(this);

        when(pageServlet.getServletConfig()).thenReturn(servletConfig);
        when(pageServlet.getServletContext()).thenReturn(servletContext);

        when(servletContext.getInitParameter("basePath")).thenReturn("/views/");
        when(servletContext.getInitParameter("engine")).thenReturn(".jsp");

        when(response.getWriter()).thenReturn(printWriter);

        pageServlet.setFilmService(filmServiceMethods);
    }

    @Test
    public void doGetHomepage() throws SQLException, ServletException, IOException {
        when(pageServlet.getPath(request)).thenReturn("/");
        when(request.getRequestDispatcher("/views/site/homepage.jsp")).thenReturn(requestDispatcher);

        ArrayList<Film> filmList = new ArrayList<>();
        when(filmServiceMethods.fetchLastReleases(anyInt())).thenReturn(filmList);

        pageServlet.doGet(request, response);

        verify(request).setAttribute("filmCarousel", filmList);
        verify(request).setAttribute("filmLastReleases", filmList);
        verify(request).setAttribute("filmComingSoon", filmList);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doGetNotValid() throws ServletException, IOException {
        when(pageServlet.getPath(request)).thenReturn("/path");

        pageServlet.doGet(request, response);

        assertEquals(pageServlet.getPath(request), "/path");
    }
}
