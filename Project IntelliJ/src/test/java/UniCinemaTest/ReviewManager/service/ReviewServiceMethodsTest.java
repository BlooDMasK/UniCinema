package UniCinemaTest.ReviewManager.service;

import ReviewManager.service.ReviewServiceMethods;
import model.bean.Film;
import model.bean.Review;
import model.dao.ReviewDAO;
import org.apache.commons.validator.Arg;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import utils.Paginator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class ReviewServiceMethodsTest {

    @Mock
    private ReviewDAO reviewDAO;

    private ReviewServiceMethods reviewServiceMethods;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        reviewServiceMethods = new ReviewServiceMethods();
        reviewServiceMethods.setReviewDAO(reviewDAO);
    }

    @ParameterizedTest
    @MethodSource("provideFilm")
    public void countAll(Film film) throws SQLException {
        when(reviewDAO.countAll(film)).thenReturn(15);
        assertEquals(reviewServiceMethods.countAll(film), 15);
    }

    @ParameterizedTest
    @MethodSource("provideFilmWithPaginator")
    public void fetchAll(Film film, Paginator paginator) throws SQLException {
        ArrayList<Review> reviewList = new ArrayList<>();
        when(reviewDAO.fetchAll(film, paginator)).thenReturn(reviewList);
        assertEquals(reviewServiceMethods.fetchAll(film, paginator), reviewList);
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    public void fetchAll(int filmId) throws SQLException {
        ArrayList<Review> reviewList = new ArrayList<>();
        when(reviewDAO.fetchAll(filmId)).thenReturn(reviewList);
        assertEquals(reviewServiceMethods.fetchAll(filmId), reviewList);
    }

    @ParameterizedTest
    @MethodSource("provideReviewListWithStars")
    public void averageStars(ArrayList<Review> reviewList, int stars) {
        assertEquals(reviewServiceMethods.averageStars(reviewList, stars), 2.5);
    }

    @ParameterizedTest
    @MethodSource("provideReviewListWithStars")
    public void percentageStars(ArrayList<Review> reviewList, int stars) {
        assertEquals(reviewServiceMethods.percentageStars(reviewList, stars), 50.0);
    }

    @ParameterizedTest
    @MethodSource("provideReview")
    public void insert(Review review) throws SQLException {
        when(reviewDAO.insert(review)).thenReturn(true);
        assertEquals(reviewServiceMethods.insert(review), true);
    }

    @ParameterizedTest
    @MethodSource("provideReviewId")
    public void fetch(int accountId, int filmId) throws SQLException {
        Review review = new Review();
        when(reviewDAO.fetch(accountId, filmId)).thenReturn(review);
        assertEquals(reviewServiceMethods.fetch(accountId, filmId), review);
    }

    @ParameterizedTest
    @ValueSource(ints = {2})
    public void delete(int accountId) throws SQLException {
        when(reviewDAO.delete(accountId)).thenReturn(true);
        assertEquals(reviewServiceMethods.delete(accountId), true);
    }

    @ParameterizedTest
    @ValueSource(ints = {2})
    public void countByAccountId(int id) throws SQLException {
        when(reviewDAO.countByAccountId(id)).thenReturn(15);
        assertEquals(reviewServiceMethods.countByAccountId(id), 15);
    }

    public static Stream<Arguments> provideFilm() {
        return Stream.of(
            Arguments.of(new Film(12, 120, 2, "Titolo", "Trama", "cover.png", "poster.png", LocalDate.now()))
        );
    }

    private static Stream<Arguments> provideFilmWithPaginator() {
        return Stream.of(
            Arguments.of(
                    new Film(12, 120, 2, "Titolo", "Trama", "cover.png", "poster.png", LocalDate.now()),
                    new Paginator(1, 5)
            )
        );
    }

    private static Stream<Arguments> provideReviewListWithStars() {
        return Stream.of(
            Arguments.of(new ArrayList<>(
                    List.of(new Review("Descrizione recensione1", "Titolo recensione1", LocalDate.now(), LocalTime.now(), 5),
                            new Review("Descrizione recensione2", "Titolo recensione2", LocalDate.now(), LocalTime.now(), 5),
                            new Review("Descrizione recensione3", "Titolo recensione3", LocalDate.now(), LocalTime.now(), 2),
                            new Review("Descrizione recensione4", "Titolo recensione4", LocalDate.now(), LocalTime.now(), 4),
                            new Review("Descrizione recensione4", "Titolo recensione4", LocalDate.now(), LocalTime.now(), 4),
                            new Review("Descrizione recensione5", "Titolo recensione5", LocalDate.now(), LocalTime.now(), 5))
            ), 5)
        );
    }

    public static Stream<Arguments> provideReview() {
        return Stream.of(
            Arguments.of(new Review("Descrizione recensione", "Titolo recensione", LocalDate.now(), LocalTime.now(), 5))
        );
    }

    public static Stream<Arguments> provideReviewId() {
        return Stream.of(
            Arguments.of(2, 5)
        );
    }
}