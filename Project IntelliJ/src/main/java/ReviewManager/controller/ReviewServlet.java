package ReviewManager.controller;

import FilmInfo.service.FilmService;
import FilmInfo.service.FilmServiceMethods;
import ReviewManager.service.ReviewService;
import ReviewManager.service.ReviewServiceMethods;
import model.bean.Film;
import model.bean.Review;
import org.json.JSONArray;
import org.json.JSONObject;
import utils.*;
import utils.validator.ReviewValidator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.round;

@WebServlet(name = "ReviewServlet", value = "/review/*")
public class ReviewServlet extends Controller implements ErrorHandler {

    ReviewService reviewService = new ReviewServiceMethods();
    FilmService filmService = new FilmServiceMethods();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * Implementa le funzionalità svolte durante una chiamata di tipo POST
     * @param request oggetto rappresentante la chiamata Http request
     * @param response oggetto rappresentante la chiamata Http response
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            HttpSession session = request.getSession();

            String path = getPath(request);
            switch(path) {
                case "/list":
                    if (isAjax(request)) {
                /*
                        Review List
                 */
                    int filmId = Integer.parseInt(request.getParameter("filmId"));
                    Optional<Film> film = filmService.fetch(filmId);
                    if (film.isPresent()) {
                        Paginator paginator = new Paginator(parsePage(request), 5);
                        int size = reviewService.countAll(film.get());

                        ArrayList<Review> reviewList = reviewService.fetchAll(film.get(), paginator);

                        JSONObject root = new JSONObject();

                    /*
                            0 - lista delle recensioni
                            1 - pages
                            2 - average
                            3 - average rounded
                            4 - percentuale 1 stella
                            5 - percentuale 2 stella
                            6 - percentuale 3 stella
                            7 - percentuale 4 stella
                            8 - percentuale 5 stella
                            9 - totale recensioni
                     */
                        JSONArray list = new JSONArray();

                        for (Review review : reviewList)
                            list.put(review.toJson());

                        root.put("reviewList", list);
                        root.put("pages", paginator.getPages(size));

                        /*
                                Review Stats
                         */
                        ArrayList<Review> reviewListTotal = reviewService.fetchAll(film.get().getId());

                        DecimalFormat df = new DecimalFormat("#.#");
                        double avg = reviewService.averageStars(reviewListTotal, 0);
                        root.put("reviewAverage", df.format(avg));
                        root.put("reviewAverageRounded", round(avg));

                        for (int i = 1; i < 6; i++)
                            root.put("reviewPercentage" + i, df.format(reviewService.percentageStars(reviewListTotal, i)).replace(",", ".") + "%");

                        root.put("reviewCount", reviewService.countAll(film.get()));
                        sendJson(response, root);
                    }
                }
                break;

                case "/add":
                    if(isAjax(request)) {
                        validate(ReviewValidator.validateReview(request));

                        if(reviewService.fetch(getSessionAccount(session).getId(), Integer.parseInt(request.getParameter("filmId"))).isPresent())
                            throw new InvalidRequestException("Hai già pubblicato una recensione.", List.of("Hai già pubblicato una recensione."), HttpServletResponse.SC_BAD_REQUEST);

                        String  title = request.getParameter("reviewWriteTitle"),
                                description = request.getParameter("reviewWriteDescription");
                        int     stars = Integer.parseInt(request.getParameter("reviewWriteStars")),
                                filmId = Integer.parseInt(request.getParameter("filmId"));

                        Review review = new Review(getSessionAccount(session), new Film(filmId), description, title, LocalDate.now(), LocalTime.now(), stars);
                        reviewService.insert(review);

                        JSONObject alert = new JSONObject();
                        alert.put("alert", new Alert(List.of("Recensione pubblicata con successo."), "success").toJson());
                        sendJson(response, alert);
                    }
                    break;

                case "/remove":
                    if(isAjax(request)) {
                        JSONObject result = new JSONObject();
                        int accountId = Integer.parseInt(request.getParameter("accountId"));
                        if(reviewService.delete(accountId))
                            result.put("result", "La recensione dell'Account "+accountId+" è stata cancellata con successo.");
                        else
                            result.put("result", "A causa di un errore interno, non è stato possibile cancellare la recensione dell'Account "+  accountId);

                        sendJson(response, result);
                    }
                    break;
            }
        } catch (SQLException ex) {
            log(ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            if(isAjax(request))
                e.handleAjax(response);
            else
                e.handle(request, response);
        }
    }
}
