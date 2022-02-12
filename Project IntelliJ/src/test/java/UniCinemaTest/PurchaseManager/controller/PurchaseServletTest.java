package UniCinemaTest.PurchaseManager.controller;

import PurchaseManager.controller.PurchaseServlet;
import PurchaseManager.service.PurchaseServiceMethods;
import ShowManager.service.ShowServiceMethods;
import model.bean.*;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import utils.InvalidRequestException;
import utils.Paginator;

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
import java.util.HashMap;
import java.util.Iterator;

import static org.mockito.Mockito.*;

public class PurchaseServletTest {

    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private HttpSession session;
    @Mock private PrintWriter printWriter;
    @Mock private PurchaseServiceMethods purchaseServiceMethods;
    @Mock private ShowServiceMethods showServiceMethods;
    @Mock private Show show;
    @Mock private Room room;
    @Mock private ArrayList<Ticket> ticketList;
    @Mock private Iterator<Ticket> ticketIterator;
    @Mock private Ticket ticket;
    @Mock private JSONObject jsonObject;
    @Mock private Account account;
    @Mock private ArrayList<Purchase> purchaseList;
    @Mock private Iterator<Purchase> purchaseIterator;
    @Mock private Purchase purchase;
    @Mock private Paginator paginator;

    @Spy private PurchaseServlet purchaseServlet;

    @Before
    public void setUp() throws IOException, InvalidRequestException {

        MockitoAnnotations.initMocks(this);

        when(purchaseServlet.getServletConfig()).thenReturn(servletConfig);
        when(purchaseServlet.getServletContext()).thenReturn(servletContext);

        when(servletContext.getInitParameter("basePath")).thenReturn("/views/");
        when(servletContext.getInitParameter("engine")).thenReturn(".jsp");

        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);

        when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        when(response.getWriter()).thenReturn(printWriter);

        doNothing().when(purchaseServlet).authenticate(session);

        purchaseServlet.setPurchaseService(purchaseServiceMethods);
        purchaseServlet.setShowService(showServiceMethods);
        purchaseServlet.setJsonObject(jsonObject);
    }

    @Test
    public void doGetSeatChoice() throws ServletException, IOException, SQLException, InvalidRequestException {
        when(purchaseServlet.getPath(request)).thenReturn("/seat-choice");
        when(request.getRequestDispatcher("/views/site/movie/purchase.jsp")).thenReturn(requestDispatcher);

        when(request.getParameter("showId")).thenReturn("1");
        when(showServiceMethods.fetch(1)).thenReturn(show);
        when(showServiceMethods.fetchRoom(1)).thenReturn(room);
        when(purchaseServiceMethods.fetchTickets(1)).thenReturn(ticketList);

        when(ticketIterator.hasNext()).thenReturn(true, true, false);
        when(ticketIterator.next()).thenReturn(ticket, ticket, ticket);
        when(ticketList.iterator()).thenReturn(ticketIterator);

        purchaseServlet.doGet(request, response);

        verify(purchaseServlet).authenticate(session);
        verify(request).setAttribute("show", show);
        verify(request).setAttribute("film", show.getFilm());
        verify(request).setAttribute("room", room);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostSeatCheck() throws ServletException, IOException {
        when(purchaseServlet.getPath(request)).thenReturn("/seat-check");

        when(request.getParameter("showId")).thenReturn("1");
        when(request.getParameter("key")).thenReturn("G-6");

        purchaseServlet.doPost(request,response);

        verify(purchaseServlet).sendJson(response, jsonObject);
    }

    @Test
    public void doPostGetTicket() throws ServletException, IOException, SQLException {
        when(purchaseServlet.getPath(request)).thenReturn("/get-ticket");
        when(request.getRequestDispatcher("/views/site/movie/purchase-completed.jsp")).thenReturn(requestDispatcher);

        when(purchaseServiceMethods.insert((Purchase) anyObject())).thenReturn(1);
        when(request.getParameter("showId")).thenReturn("1");
        when(showServiceMethods.fetch(1)).thenReturn(show);
        when(request.getParameter("ticket1")).thenReturn("G-6");
        when(request.getParameter("ticket2")).thenReturn("");
        when(request.getParameter("ticket3")).thenReturn("");
        when(request.getParameter("ticket4")).thenReturn("");
        when(request.getParameter("ticket5")).thenReturn("");
        when(purchaseServiceMethods.insert((ArrayList<Ticket>) anyObject())).thenReturn(true);
        when(showServiceMethods.fetchRoom(anyInt())).thenReturn(room);

        purchaseServlet.doPost(request,response);

        verify(request).setAttribute("show", show);
        verify(request).setAttribute("film", show.getFilm());
        verify(request).setAttribute("room", room);
        verify(requestDispatcher).forward(request,response);
    }

    @Test
    public void doPostList() throws SQLException, ServletException, IOException {
        when(purchaseServlet.getPath(request)).thenReturn("/list");
        when(purchaseServlet.getSessionAccount(session)).thenReturn(account);
        when(account.getId()).thenReturn(1);
        when(request.getParameter("page")).thenReturn("1");
        when(purchaseServiceMethods.countAll(1)).thenReturn(1);
        when(purchaseServiceMethods.fetchAll(1, paginator)).thenReturn(purchaseList);

        when(purchaseIterator.hasNext()).thenReturn(true, true, false);
        when(purchaseIterator.next()).thenReturn(purchase, purchase, purchase);
        when(purchaseList.iterator()).thenReturn(purchaseIterator);

        purchaseServlet.doPost(request, response);

        verify(purchaseServlet).sendJson(response, jsonObject);
    }
}