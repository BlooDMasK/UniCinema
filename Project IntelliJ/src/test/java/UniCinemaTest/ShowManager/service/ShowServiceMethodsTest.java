package UniCinemaTest.ShowManager.service;

import FilmManager.service.FilmManagerServiceMethods;
import ShowManager.service.ShowServiceMethods;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import model.bean.Film;
import model.bean.Room;
import model.bean.Show;
import model.dao.RoomDAO;
import model.dao.ShowDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class ShowServiceMethodsTest {

    @Mock private ShowDAO showDAO;
    @Mock private RoomDAO roomDAO;

    private ShowServiceMethods showServiceMethods;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        showServiceMethods = new ShowServiceMethods();

        showServiceMethods.setShowDAO(showDAO);
        showServiceMethods.setRoomDAO(roomDAO);
    }

    @Test
    @Parameters(method = "provideFilm")
    public void fetchAll(Film film) throws SQLException {

        ArrayList<Show> showList = new ArrayList<>();

        when(showDAO.fetchAll(film)).thenReturn(showList);
        assertEquals(showServiceMethods.fetchAll(film), showList);

    }

    @Test
    @Parameters(method = "provideRoomDate")
    public void fetchDaily(int roomId, LocalDate date) throws SQLException {

        ArrayList<Show> showList = new ArrayList<>();

        when(showDAO.fetchDaily(roomId, date)).thenReturn(showList);
        assertEquals(showServiceMethods.fetchDaily(roomId,date), showList);

    }

    @Test
    @Parameters(method = "provideRoomDateShow")
    public void fetchDaily(int roomId, LocalDate date, Show show) throws SQLException {

        ArrayList<Show> showList = new ArrayList<>();
        when(showDAO.fetchDaily(roomId, date, show)).thenReturn(showList);
        assertEquals(showServiceMethods.fetchDaily(roomId, date, show), showList);
    }

    @Test
    @Parameters(value = "1")
    public void fetch(int id) throws SQLException {

        Room room = new Room();

        when(roomDAO.fetchFromShowId(id)).thenReturn(room);
        assertEquals(showServiceMethods.fetchRoom(id), room);
    }

    @Test
    @Parameters(value = "1")
    public void fetchRoom(int showId) throws SQLException {

        Room room = new Room();
        when(roomDAO.fetchFromShowId(showId)).thenReturn(room);
        assertEquals(showServiceMethods.fetchRoom(showId), room);
    }

    @Test
    @Parameters(value = "1")
    public void remove(int showId) throws SQLException {

        when(showDAO.delete(showId)).thenReturn(true);
        assertEquals(showServiceMethods.remove(showId), true);

    }

    @Test
    @Parameters(method = "provideShow")
    public void insert(Show show) throws SQLException {

        when(showDAO.insert(show)).thenReturn(true);
        assertEquals(showServiceMethods.insert(show), true);
    }

    @Test
    @Parameters(method = "provideShow")
    public void update(Show show) throws SQLException {

        when(showDAO.update(show)).thenReturn(true);
        assertEquals(showServiceMethods.update(show), true);
    }

    @Test
    public void fetchAll() throws SQLException {
        ArrayList<Show> showList = new ArrayList<>();
        when(showDAO.fetchAll()).thenReturn(showList);
        assertEquals(showServiceMethods.fetchAll(), showList);
    }

    private Object[] provideFilm() {
        return $(new Film(12, 120, 5,
                "titoloFilm", "tramaFilm", "cover.png",
                "poster.png", LocalDate.now()));
    }

    private Object[] provideRoomDate() {
        return $($(12, LocalDate.now()));
    }

    private Object[] provideRoomDateShow() {
        return $($($(12, LocalDate.now(), new Show(10, LocalDate.now(), LocalTime.now()))));
    }

    private Object[] provideShow() {
        return $(new Show(12, LocalDate.now(), LocalTime.now()));
    }

/*
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        showServiceMethods = new ShowServiceMethods();

        showServiceMethods.setShowDAO(showDAO);
        showServiceMethods.setRoomDAO(roomDAO);
    }


    @ParameterizedTest
    @MethodSource("provideFilm")
    public void fetchAll(Film film) throws SQLException {

        ArrayList<Show> showList = new ArrayList<>();

        when(showDAO.fetchAll(film)).thenReturn(showList);
        assertEquals(showServiceMethods.fetchAll(film), showList);

    }




    @ParameterizedTest
    @MethodSource("provideRoomDate")
    public void fetchDaily(int roomId, LocalDate date) throws SQLException {

        ArrayList<Show> showList = new ArrayList<>();

        when(showDAO.fetchDaily(roomId, date)).thenReturn(showList);
        assertEquals(showServiceMethods.fetchDaily(roomId,date), showList);

    }

    @ParameterizedTest
    @MethodSource("provideRoomDateShow")
    public void fetchDaily(int roomId, LocalDate date, Show show) throws SQLException {

        ArrayList<Show> showList = new ArrayList<>();
        when(showDAO.fetchDaily(roomId, date, show)).thenReturn(showList);
        assertEquals(showServiceMethods.fetchDaily(roomId, date, show), showList);
    }


    @ParameterizedTest
    @ValueSource(ints = {10})
    public void fetch(int id) throws SQLException {

        Room room = new Room();

        when(roomDAO.fetchFromShowId(id)).thenReturn(room);
        assertEquals(showServiceMethods.fetchRoom(id), room);
    }


    @ParameterizedTest
    @ValueSource(ints = 11)
    public void fetchRoom(int showId) throws SQLException {

       Room room = new Room();
       when(roomDAO.fetchFromShowId(showId)).thenReturn(room);
       assertEquals(showServiceMethods.fetchRoom(showId), room);
    }

    @ParameterizedTest
    @ValueSource(ints = 10)
    public void remove(int showId) throws SQLException {

        when(showDAO.delete(showId)).thenReturn(true);
        assertEquals(showServiceMethods.remove(showId), true);

    }


    @ParameterizedTest
    @MethodSource("provideShow")
    public void insert(Show show) throws SQLException {

        when(showDAO.insert(show)).thenReturn(true);
        assertEquals(showServiceMethods.insert(show), true);
    }


    @ParameterizedTest
    @MethodSource("provideShow")
    public void update(Show show) throws SQLException {

        when(showDAO.update(show)).thenReturn(true);
        assertEquals(showServiceMethods.update(show), true);
    }



    private static Stream<Arguments> provideFilm() throws NoSuchAlgorithmException {
        return Stream.of(Arguments.of(new Film(12, 120, 5, "titoloFilm", "tramaFilm", "cover.png", "poster.png", LocalDate.now())));
    }

    private static Stream<Arguments> provideRoomDate() {
        return Stream.of(Arguments.of(12, LocalDate.now()));
    }

    private static Stream<Arguments> provideRoomDateShow() {
        return Stream.of(Arguments.of(12, LocalDate.now(),
                new Show(10, LocalDate.now(), LocalTime.now())));
    }


    private static Stream<Arguments> provideShow() {
        return Stream.of(Arguments.of(new Show(12, LocalDate.now(), LocalTime.now())));
    }


*/
}
