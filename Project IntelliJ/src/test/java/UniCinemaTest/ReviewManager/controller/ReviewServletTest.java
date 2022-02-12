package UniCinemaTest.ReviewManager.controller;

import FilmInfo.service.FilmService;
import FilmInfo.service.FilmServiceMethods;
import ReviewManager.controller.ReviewServlet;
import ReviewManager.service.ReviewService;
import ReviewManager.service.ReviewServiceMethods;
import model.bean.Account;
import model.bean.Film;
import model.bean.Review;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import utils.InvalidRequestException;
import utils.Paginator;
import utils.validator.RequestValidator;
import utils.validator.ReviewValidator;

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

import static org.mockito.Mockito.*;

public class ReviewServletTest {

    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private PrintWriter printWriter;
    @Mock private ReviewServiceMethods reviewServiceMethods;
    @Mock private FilmServiceMethods filmServiceMethods;
    @Mock private ReviewValidator reviewValidator;
    @Mock private Film film;
    @Mock private ArrayList<Review> reviewList;
    @Mock private Paginator paginator;
    @Mock private Review review;
    @Mock private JSONObject jsonObject;
    @Mock private RequestValidator requestValidator;
    @Mock private Account account;

    @Spy private ReviewServlet reviewServlet;

    @Before
    public void setUp() throws IOException {

        MockitoAnnotations.initMocks(this);

        when(reviewServlet.getServletConfig()).thenReturn(servletConfig);
        when(reviewServlet.getServletContext()).thenReturn(servletContext);

        when(servletContext.getInitParameter("basePath")).thenReturn("/views/");
        when(servletContext.getInitParameter("engine")).thenReturn(".jsp");

        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);

        when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        when(response.getWriter()).thenReturn(printWriter);

        reviewServlet.setReviewService(reviewServiceMethods);
        reviewServlet.setReviewValidator(reviewValidator);
        reviewServlet.setFilmService(filmServiceMethods);
        reviewServlet.setJsonObject(jsonObject);
    }

    @Test
    public void doPostList() throws SQLException, ServletException, IOException {
        when(reviewServlet.getPath(request)).thenReturn("/list");

        when(request.getParameter("filmId")).thenReturn("1");
        when(filmServiceMethods.fetch(1)).thenReturn(film);
        when(reviewServiceMethods.countAll(film)).thenReturn(1);
        when(paginator.getPages(1)).thenReturn(1);
        when(reviewServiceMethods.fetchAll(film, paginator)).thenReturn(reviewList);
        when(review.toJson()).thenReturn(jsonObject);
        when(reviewServiceMethods.fetchAll(1)).thenReturn(reviewList);
        when(reviewServiceMethods.averageStars(reviewList, 0)).thenReturn(5.0);
        when(reviewServiceMethods.percentageStars(reviewList, 1)).thenReturn(0.0);
        when(reviewServiceMethods.percentageStars(reviewList, 2)).thenReturn(0.0);
        when(reviewServiceMethods.percentageStars(reviewList, 3)).thenReturn(0.0);
        when(reviewServiceMethods.percentageStars(reviewList, 4)).thenReturn(0.0);
        when(reviewServiceMethods.percentageStars(reviewList, 5)).thenReturn(100.0);
        when(request.getParameter("page")).thenReturn("1");

        reviewServlet.doPost(request, response);

        verify(reviewServlet).sendJson(response, jsonObject);
    }

    @Test
    public void doPostAdd() throws SQLException, ServletException, IOException, InvalidRequestException {
        when(reviewServlet.getPath(request)).thenReturn("/add");

        when(reviewValidator.validateReview(request)).thenReturn(requestValidator);
        when(reviewServlet.getSessionAccount(session)).thenReturn(account);
        when(request.getParameter("filmId")).thenReturn("1");
        when(reviewServiceMethods.fetch(1, 1)).thenReturn(null);
        when(request.getParameter("reviewWriteTitle")).thenReturn("Lo consiglio vivamente.");
        when(request.getParameter("reviewWriteDescription")).thenReturn("Un film fatto veramente bene, da vedere assolutamente al cinema.");
        when(request.getParameter("reviewWriteStars")).thenReturn("5");

        reviewServlet.doPost(request, response);

        verify(reviewServlet).validate(requestValidator);
        verify(reviewServiceMethods).insert((Review) anyObject());
        verify(reviewServlet).sendJson(response, jsonObject);
    }

    @Test
    public void doPostRemove() throws SQLException, ServletException, IOException {
        when(reviewServlet.getPath(request)).thenReturn("/remove");

        when(request.getParameter("accountId")).thenReturn("1");
        when(reviewServiceMethods.delete(1)).thenReturn(true);

        reviewServlet.doPost(request,response);

        verify(reviewServlet).sendJson(response, jsonObject);
    }
}
