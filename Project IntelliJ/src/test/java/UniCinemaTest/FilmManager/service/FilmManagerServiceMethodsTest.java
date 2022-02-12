package UniCinemaTest.FilmManager.service;

import FilmManager.service.FilmManagerServiceMethods;
import model.bean.Account;
import model.bean.Film;
import model.dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FilmManagerServiceMethodsTest {

    @Mock private ActorDAO actorDAO;
    @Mock private DirectorDAO directorDAO;
    @Mock private HouseProductionDAO houseProductionDAO;
    @Mock private ProductionDAO productionDAO;
    @Mock private FilmDAO filmDAO;


    private FilmManagerServiceMethods filmManagerServiceMethods;


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
}
