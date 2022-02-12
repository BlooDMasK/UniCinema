package UniCinemaTest.ShowManager.controller;

import ShowManager.controller.ShowManagerServlet;
import ShowManager.service.ShowServiceMethods;
import model.bean.Film;
import model.bean.Room;
import model.bean.Show;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import utils.InvalidRequestException;

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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

import static org.mockito.Mockito.*;

public class ShowManagerServletTest {

    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private HttpSession session;
    @Mock private PrintWriter printWriter;
    @Mock private ShowServiceMethods showServiceMethods;
    @Mock private JSONObject jsonObject;
    @Mock private Show show;
    @Mock private Room room;
    @Mock private Film film;
    @Mock private ArrayList<Show> showList;
    @Mock private Iterator<Show> showIterator;
    @Mock private JSONArray jsonArray;

    @Spy private ShowManagerServlet showManagerServlet;

    @Before
    public void setUp() throws IOException, InvalidRequestException {
        MockitoAnnotations.initMocks(this);

        when(showManagerServlet.getServletConfig()).thenReturn(servletConfig);
        when(showManagerServlet.getServletContext()).thenReturn(servletContext);

        when(servletContext.getInitParameter("basePath")).thenReturn("/views/");
        when(servletContext.getInitParameter("engine")).thenReturn(".jsp");

        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);

        when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        when(response.getWriter()).thenReturn(printWriter);

        doNothing().when(showManagerServlet).authorize(session);

        showManagerServlet.setShowService(showServiceMethods);
    }

    @Test
    public void doPostRemove() throws SQLException, ServletException, IOException {
        when(showManagerServlet.getPath(request)).thenReturn("/remove");

        when(request.getParameter("showId")).thenReturn("1");
        when(showServiceMethods.remove(1)).thenReturn(true);

        showManagerServlet.doPost(request,response);

        verify(showManagerServlet).sendJson(response, jsonObject);
    }

    @Test
    public void doPostAdd() throws SQLException, ServletException, IOException {
        when(showManagerServlet.getPath(request)).thenReturn("/add");

        when(request.getParameter("room")).thenReturn("1");
        when(request.getParameter("filmId")).thenReturn("1");

        when(request.getParameter("date")).thenReturn("2022-02-01");
        when(request.getParameter("time")).thenReturn("20:00");
        when(showServiceMethods.insert((Show) anyObject())).thenReturn(true);

        showManagerServlet.doPost(request,response);

        verify(response).sendRedirect(servletContext.getContextPath()+"/film/details?filmId=1");
    }

    @Test
    public void doPostUpdate() throws SQLException, ServletException, IOException {
        when(showManagerServlet.getPath(request)).thenReturn("/update");

        when(request.getParameter("showId")).thenReturn("1");
        when(request.getParameter("filmId")).thenReturn("1");

        when(request.getParameter("date")).thenReturn("2022-02-01");
        when(request.getParameter("time")).thenReturn("20:00");
        when(showServiceMethods.update((Show) anyObject())).thenReturn(true);

        showManagerServlet.doPost(request, response);

        verify(response).sendRedirect(servletContext.getContextPath()+"/film/details?filmId=1");
    }

    @Test
    public void doPostGetShow() throws SQLException, ServletException, IOException {
        when(showManagerServlet.getPath(request)).thenReturn("/get-show");

        when(request.getParameter("showId")).thenReturn("1");
        when(showServiceMethods.fetch(1)).thenReturn(show);
        when(show.getRoom()).thenReturn(room);
        when(room.getId()).thenReturn(1);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.of(18, 0);
        when(show.getDate()).thenReturn(date);
        when(show.getFilm()).thenReturn(film);
        when(show.getTime()).thenReturn(time);
        when(film.getLength()).thenReturn(60);
        when(showServiceMethods.fetchDaily(1, date, show)).thenReturn(showList);

        when(showIterator.hasNext()).thenReturn(false);
        when(showList.iterator()).thenReturn(showIterator);

        showManagerServlet.doPost(request,response);

        verify(showManagerServlet).sendJson(response, jsonObject);
    }

    @Test
    public void doPostGetAllShow() {

    }
}