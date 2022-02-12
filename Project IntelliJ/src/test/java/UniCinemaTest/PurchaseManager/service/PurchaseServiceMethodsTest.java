package UniCinemaTest.PurchaseManager.service;

import PurchaseManager.service.PurchaseService;
import PurchaseManager.service.PurchaseServiceMethods;
import model.bean.Purchase;
import model.bean.Ticket;
import model.dao.PurchaseDAO;
import model.dao.TicketDAO;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import utils.Paginator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class PurchaseServiceMethodsTest {

    @Mock private TicketDAO ticketDAO;
    @Mock private PurchaseDAO purchaseDAO;

    private PurchaseServiceMethods purchaseService;

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
    }
}
