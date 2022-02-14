package UniCinemaTest.FilmInfo.service;

import FilmInfo.service.FilmServiceMethods;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import model.bean.Film;
import model.dao.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class FilmServiceMethodsTest {

    @Mock private FilmDAO filmDAO;

    private FilmServiceMethods filmInfoServiceMethods;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        filmInfoServiceMethods = new FilmServiceMethods();

        filmInfoServiceMethods.setFilmDAO(filmDAO);
    }

    @Test
    @Parameters(value = "7")
    public void fetch(int filmId) throws SQLException {

        Film film = new Film();
        film.setId(filmId);

        when(filmDAO.fetch(filmId)).thenReturn(film);
        assertEquals(film, filmInfoServiceMethods.fetch(filmId));
    }

    @Test
    @Parameters(value = "titoloFilm")
    public void search(String title) throws SQLException {
        ArrayList<Film> films = new ArrayList<>();

        when(filmDAO.searchFromTitle(title)).thenReturn(films);
        assertEquals(filmInfoServiceMethods.search(title), films);
    }

    /*
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        filmInfoServiceMethods = new FilmServiceMethods();

        filmInfoServiceMethods.setFilmDAO(filmDAO);
    }


    @ParameterizedTest
    @ValueSource(ints = {7})
    public void fetch(int filmId) throws SQLException {

        Film film = new Film();
        film.setId(filmId);

        when(filmDAO.fetch(filmId)).thenReturn(film);
        assertEquals(film, filmInfoServiceMethods.fetch(filmId));
    }


    @ParameterizedTest
    @ValueSource(strings = {"titoloFilm"})
    public void search(String title) throws SQLException {
        ArrayList<Film> films = new ArrayList<>();

        when(filmDAO.searchFromTitle(title)).thenReturn(films);
        assertEquals(filmInfoServiceMethods.search(title), films);
    }*/
}
