package UniCinemaTest.PurchaseManager.service;

import PurchaseManager.service.PurchaseServiceMethods;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import model.bean.Purchase;
import model.bean.Ticket;
import model.dao.purchase.PurchaseDAOMethods;
import model.dao.ticket.TicketDAOMethods;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import utils.Paginator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class PurchaseServiceMethodsTest {

    @Mock private TicketDAOMethods ticketDAOMethods;
    @Mock private PurchaseDAOMethods purchaseDAOMethods;

    private PurchaseServiceMethods purchaseService;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);

        purchaseService = new PurchaseServiceMethods();
        purchaseService.setTicketDAO(ticketDAOMethods);
        purchaseService.setPurchaseDAO(purchaseDAOMethods);
    }

    @Test
    @Parameters(value = "1")
    public void fetchTickets(int showId) throws SQLException {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        when(ticketDAOMethods.fetchAll(showId)).thenReturn(ticketList);
        assertEquals(purchaseService.fetchTickets(showId), ticketList);
    }

    @Test
    @Parameters(value = "1, G, 8")
    public void findTicket(int showId, char row, int seat) throws SQLException {
        when(ticketDAOMethods.fetch(showId, row, seat)).thenReturn(true);
        assertEquals(purchaseService.findTicket(showId, row, seat), true);
    }

    @Test
    @Parameters(method = "provideTicketList")
    public void insert(ArrayList<Ticket> ticketList) throws SQLException {
        when(ticketDAOMethods.insert(ticketList)).thenReturn(true);
        assertEquals(purchaseService.insert(ticketList), true);
    }


    @Test
    @Parameters(method = "providePurchase")
    public void insert(Purchase purchase) throws SQLException {
        when(purchaseDAOMethods.insertAndReturnID(purchase)).thenReturn(23);
        assertEquals(purchaseService.insert(purchase), 23);
    }

    @Test
    @Parameters(method = "provideAccountPaginator")
    public void fetchAll(int accountId, Paginator paginator) throws SQLException {
        ArrayList<Purchase> purchaseList = new ArrayList<>();
        when(purchaseDAOMethods.fetchAll(accountId, paginator)).thenReturn(purchaseList);
        assertEquals(purchaseService.fetchAll(accountId, paginator), purchaseList);
    }

    @Test
    @Parameters(value = "1")
    public void countAll(int accountId) throws SQLException {
        when(purchaseDAOMethods.countAll(accountId)).thenReturn(10);
        assertEquals(purchaseService.countAll(accountId), 10);
    }

    private Object[] provideTicketList() {
        ArrayList<Ticket> ticketList = new ArrayList<>(List.of(new Ticket(10.0, 8, 'G'),
                new Ticket(10.0, 9, 'G'),
                new Ticket(10.0, 10, 'G')));
        return $(ticketList);
    }

    private Object[] providePurchase() {
        return $(new Purchase(3, LocalDate.now()));
    }

    private Object[] provideAccountPaginator() {
        return $($(1, new Paginator(1, 5)));
    }

    /*
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        purchaseService = new PurchaseServiceMethods();
        purchaseService.setTicketDAO(ticketDAO);
        purchaseService.setPurchaseDAO(purchaseDAO);
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void fetchTickets(int showId) throws SQLException {
        ArrayList<Ticket> ticketList = new ArrayList<>();
        when(ticketDAO.fetchAll(showId)).thenReturn(ticketList);
        assertEquals(purchaseService.fetchTickets(showId), ticketList);
    }

    @ParameterizedTest
    @MethodSource("provideTicketInfo")
    public void findTicket(int showId, char row, int seat) throws SQLException {
        when(ticketDAO.fetch(showId, row, seat)).thenReturn(true);
        assertEquals(purchaseService.findTicket(showId, row, seat), true);
    }

    @ParameterizedTest
    @MethodSource("provideTicketList")
    public void insert(ArrayList<Ticket> ticketList) throws SQLException {
        when(ticketDAO.insert(ticketList)).thenReturn(true);
        assertEquals(purchaseService.insert(ticketList), true);
    }

    @ParameterizedTest
    @MethodSource("providePurchase")
    public void insert(Purchase purchase) throws SQLException {
        when(purchaseDAO.insertAndReturnID(purchase)).thenReturn(23);
        assertEquals(purchaseService.insert(purchase), 23);
    }

    @ParameterizedTest
    @MethodSource("providePurchaseInfo")
    public void fetchAll(int accountId, Paginator paginator) throws SQLException {
        ArrayList<Purchase> purchaseList = new ArrayList<>();
        when(purchaseDAO.fetchAll(accountId, paginator)).thenReturn(purchaseList);
        assertEquals(purchaseService.fetchAll(accountId, paginator), purchaseList);
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void countAll(int accountId) throws SQLException {
        when(purchaseDAO.countAll(accountId)).thenReturn(10);
        assertEquals(purchaseService.countAll(accountId), 10);
    }

    private static Stream<Arguments> provideTicketInfo() {
        return Stream.of(
                Arguments.of(3, 'G', 8)
        );
    }

    private static Stream<Arguments> provideTicketList() {
        return Stream.of(
            Arguments.of(new ArrayList<>(
                    List.of(new Ticket(10.0, 8, 'G'),
                            new Ticket(10.0, 9, 'G'),
                            new Ticket(10.0, 10, 'G'))))
        );
    }

    private static Stream<Arguments> providePurchase() {
        return Stream.of(
            Arguments.of(new Purchase(3, LocalDate.now()))
        );
    }

    private static Stream<Arguments> providePurchaseInfo() {
        return Stream.of(
            Arguments.of(1, new Paginator(1, 5))
        );
    }*/
}
