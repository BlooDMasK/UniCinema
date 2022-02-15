package UniCinemaTest.PurchaseManager.controller;

import PurchaseManager.controller.PurchaseServlet;
import PurchaseManager.service.PurchaseService;
import PurchaseManager.service.PurchaseServiceMethods;
import ShowManager.service.ShowService;
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
import java.util.Iterator;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class PurchaseServletIntegrationTest {

    @Mock
    private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private PrintWriter printWriter;

    @Spy
    private PurchaseServlet purchaseServlet;
    private PurchaseService purchaseService;
    private JSONObject jsonObject;

    @Before
    public void setUp() throws IOException, InvalidRequestException, SQLException {

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

        purchaseService = new PurchaseServiceMethods();

        jsonObject = new JSONObject();
        purchaseServlet.setJsonObject(jsonObject);
    }

    @Test
    public void doPostSeatCheck() throws SQLException, ServletException, IOException {
        when(purchaseServlet.getPath(request)).thenReturn("/seat-check");

        String rowSeat = "C-5";
        String[] key = rowSeat.split("-");
        char row = key[0].toCharArray()[0];
        int seat = Integer.parseInt(key[1]);

        when(request.getParameter("showId")).thenReturn("1");
        when(request.getParameter("key")).thenReturn(rowSeat);

        assertFalse(purchaseService.findTicket(1, row, seat));

        purchaseServlet.doPost(request, response);

        verify(purchaseServlet).sendJson(response, jsonObject);
    }
}
