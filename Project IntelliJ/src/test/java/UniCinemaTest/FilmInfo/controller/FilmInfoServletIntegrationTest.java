package UniCinemaTest.FilmInfo.controller;

import Authentication.service.AuthenticationServiceMethods;
import FilmInfo.controller.FilmInfoServlet;
import FilmInfo.service.FilmService;
import FilmInfo.service.FilmServiceMethods;
import ShowManager.service.ShowService;
import ShowManager.service.ShowServiceMethods;
import model.bean.Film;
import model.bean.Show;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import utils.validator.AccountValidator;

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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilmInfoServletIntegrationTest {

    @Mock
    private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private PrintWriter printWriter;

    @Spy private FilmInfoServlet filmInfoServlet;
    private ShowService showService;
    private FilmService filmService;
    private JSONObject jsonObject;

    @Before
    public void setUp() throws IOException, SQLException {

        MockitoAnnotations.initMocks(this);

        when(filmInfoServlet.getServletConfig()).thenReturn(servletConfig);
        when(filmInfoServlet.getServletContext()).thenReturn(servletContext);

        when(servletContext.getInitParameter("basePath")).thenReturn("/views/");
        when(servletContext.getInitParameter("engine")).thenReturn(".jsp");

        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);

        when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        when(response.getWriter()).thenReturn(printWriter);

        showService = new ShowServiceMethods();
        filmService = new FilmServiceMethods();

        jsonObject = new JSONObject();
        filmInfoServlet.setJsonObject(jsonObject);
    }

    @Test
    public void doPostSearch() throws SQLException, ServletException, IOException {
        when(filmInfoServlet.getPath(request)).thenReturn("/search");

        assertTrue(filmInfoServlet.isAjax(request));

        String title = "Venom";
        when(request.getParameter("title")).thenReturn(title);

        ArrayList<Film> filmList = filmService.search(title);
        assertNotNull(filmList);

        filmInfoServlet.doPost(request, response);

        verify(filmInfoServlet).sendJson(response, jsonObject);
    }

}
