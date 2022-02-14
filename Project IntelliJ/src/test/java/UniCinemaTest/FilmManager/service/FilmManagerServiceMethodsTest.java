package UniCinemaTest.FilmManager.service;

import FilmManager.service.FilmManagerServiceMethods;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import model.bean.Film;
import model.dao.actor.ActorDAOMethods;
import model.dao.director.DirectorDAOMethods;
import model.dao.film.FilmDAOMethods;
import model.dao.houseproduction.HouseProductionDAOMethods;
import model.dao.production.ProductionDAOMethods;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.time.LocalDate;

import static junit.framework.TestCase.assertEquals;
import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class FilmManagerServiceMethodsTest {

    @Mock private ActorDAOMethods actorDAOMethods;
    @Mock private DirectorDAOMethods directorDAOMethods;
    @Mock private HouseProductionDAOMethods houseProductionDAOMethods;
    @Mock private ProductionDAOMethods productionDAOMethods;
    @Mock private FilmDAOMethods filmDAOMethods;

    private FilmManagerServiceMethods filmManagerServiceMethods;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        filmManagerServiceMethods = new FilmManagerServiceMethods();

        filmManagerServiceMethods.setActorDAO(actorDAOMethods);
        filmManagerServiceMethods.setDirectorDAO(directorDAOMethods);
        filmManagerServiceMethods.setHouseProductionDAO(houseProductionDAOMethods);
        filmManagerServiceMethods.setProductionDAO(productionDAOMethods);
        filmManagerServiceMethods.setFilmDAO(filmDAOMethods);
    }

    @Test
    @Parameters(value = "12")
    public void removeFilm(int filmId) throws SQLException {

        when(filmDAOMethods.delete(filmId)).thenReturn(true);
        assertEquals(filmManagerServiceMethods.removeFilm(filmId), true);
    }

    @Test
    @Parameters(method = "provideFilm")
    public void insert(Film film) throws SQLException {

        when(filmDAOMethods.insertAndReturnID(film)).thenReturn(1);
        when(actorDAOMethods.insert(film.getActorList())).thenReturn(true);
        when(directorDAOMethods.insert(film.getDirectorList())).thenReturn(true);
        when(houseProductionDAOMethods.insert(film.getHouseProductionList())).thenReturn(true);
        when(productionDAOMethods.insert(film.getProductionList())).thenReturn(true);

        assertTrue(filmManagerServiceMethods.insert(film));

        when(filmDAOMethods.insertAndReturnID(film)).thenReturn(0);
        assertFalse(filmManagerServiceMethods.insert(film));

        when(filmDAOMethods.insertAndReturnID(film)).thenReturn(1);
        when(actorDAOMethods.insert(film.getActorList())).thenReturn(false);
        assertFalse(filmManagerServiceMethods.insert(film));

        when(actorDAOMethods.insert(film.getActorList())).thenReturn(true);
        when(directorDAOMethods.insert(film.getDirectorList())).thenReturn(false);
        assertFalse(filmManagerServiceMethods.insert(film));

        when(directorDAOMethods.insert(film.getDirectorList())).thenReturn(true);
        when(houseProductionDAOMethods.insert(film.getHouseProductionList())).thenReturn(false);
        assertFalse(filmManagerServiceMethods.insert(film));

        when(houseProductionDAOMethods.insert(film.getHouseProductionList())).thenReturn(true);
        when(productionDAOMethods.insert(film.getProductionList())).thenReturn(false);
        assertFalse(filmManagerServiceMethods.insert(film));
    }

    @Test
    @Parameters(method = "provideFilm")
    public void update(Film film) throws SQLException {

        when(filmDAOMethods.update(film)).thenReturn(true);
        when(actorDAOMethods.update(film.getActorList(), film.getId())).thenReturn(true);
        when(directorDAOMethods.update(film.getDirectorList(), film.getId())).thenReturn(true);
        when(houseProductionDAOMethods.update(film.getHouseProductionList(), film.getId())).thenReturn(true);
        when(productionDAOMethods.update(film.getProductionList(), film.getId())).thenReturn(true);

        assertTrue(filmManagerServiceMethods.update(film));

        when(filmDAOMethods.update(film)).thenReturn(false);
        assertFalse(filmManagerServiceMethods.update(film));

        when(filmDAOMethods.update(film)).thenReturn(true);
        when(actorDAOMethods.update(film.getActorList(), film.getId())).thenReturn(false);
        assertFalse(filmManagerServiceMethods.update(film));

        when(actorDAOMethods.update(film.getActorList(), film.getId())).thenReturn(true);
        when(directorDAOMethods.update(film.getDirectorList(), film.getId())).thenReturn(false);
        assertFalse(filmManagerServiceMethods.update(film));

        when(directorDAOMethods.update(film.getDirectorList(), film.getId())).thenReturn(true);
        when(houseProductionDAOMethods.update(film.getHouseProductionList(), film.getId())).thenReturn(false);
        assertFalse(filmManagerServiceMethods.update(film));

        when(houseProductionDAOMethods.update(film.getHouseProductionList(), film.getId())).thenReturn(true);
        when(productionDAOMethods.update(film.getProductionList(), film.getId())).thenReturn(false);
        assertFalse(filmManagerServiceMethods.update(film));
    }

    private Object[] provideFilm() {
        return $(new Film(12, 120, 5,
                "titoloFilm", "tramaFilm", "cover.png",
                "poster.png", LocalDate.now()));
    }

    /*

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        filmManagerServiceMethods = new FilmManagerServiceMethods();

        filmManagerServiceMethods.setActorDAO(actorDAO);
        filmManagerServiceMethods.setDirectorDAO(directorDAO);
        filmManagerServiceMethods.setHouseProductionDAO(houseProductionDAO);
        filmManagerServiceMethods.setProductionDAO(productionDAO);
        filmManagerServiceMethods.setFilmDAO(filmDAO);
    }

    @ParameterizedTest
    @ValueSource(ints = 12)
    public void removeFilm(int filmId) throws SQLException {

        when(filmDAO.delete(filmId)).thenReturn(true);
        assertEquals(filmManagerServiceMethods.removeFilm(filmId), true);
    }


    @ParameterizedTest
    @MethodSource("provideFilm")
    public void insert(Film film) throws SQLException {

        when(filmDAO.insertAndReturnID(film)).thenReturn(1);

        when(actorDAO.insert(film.getActorList())).thenReturn(true);
        when(directorDAO.insert(film.getDirectorList())).thenReturn(true);
        when(houseProductionDAO.insert(film.getHouseProductionList())).thenReturn(true);
        when(productionDAO.insert(film.getProductionList())).thenReturn(true);

        assertEquals(filmManagerServiceMethods.insert(film), true);
    }

    @ParameterizedTest
    @MethodSource("provideFilm")
    public void update(Film film) throws SQLException {

        when(filmDAO.update(film)).thenReturn(true);
        when(actorDAO.update(film.getActorList(), film.getId())).thenReturn(true);
        when(directorDAO.update(film.getDirectorList(),film.getId())).thenReturn(true);
        when(houseProductionDAO.update(film.getHouseProductionList(), film.getId())).thenReturn(true);
        when(productionDAO.update(film.getProductionList(), film.getId())).thenReturn(true);

        assertEquals(filmManagerServiceMethods.update(film), true);
    }



    private static Stream<Arguments> provideFilm() throws NoSuchAlgorithmException {
        return Stream.of(Arguments.of(new Film(12, 120, 5,
                "titoloFilm", "tramaFilm", "cover.png",
                 "poster.png", LocalDate.now())));
    }
     */
}
