package UniCinemaTest.FilmInfo.service;

import FilmInfo.service.FilmServiceMethods;
import model.bean.Film;
import model.dao.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class FilmServiceMethodsTest {

    @Mock private FilmDAO filmDAO;

    private FilmServiceMethods filmInfoServiceMethods;

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
    }
}
