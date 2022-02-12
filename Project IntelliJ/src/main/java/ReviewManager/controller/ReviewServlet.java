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

import static java.lang.Math.round;

@WebServlet(name = "ReviewServlet", value = "/review/*")
public class ReviewServlet extends Controller implements ErrorHandler {

    ReviewService reviewService;
    FilmService filmService;
    ReviewValidator reviewValidator;
    JSONObject jsonObject;

    public ReviewServlet() {
        reviewService = new ReviewServiceMethods();
        filmService = new FilmServiceMethods();
        reviewValidator = new ReviewValidator();
        jsonObject = new JSONObject();
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public void setFilmService(FilmService filmService) {
        this.filmService = filmService;
    }

    public void setReviewValidator(ReviewValidator reviewValidator) {
        this.reviewValidator = reviewValidator;
    }

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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
                    Film film = filmService.fetch(filmId);
                    if (film != null) {
                        Paginator paginator = new Paginator(parsePage(request), 5);
                        int size = reviewService.countAll(film);

                        ArrayList<Review> reviewList = reviewService.fetchAll(film, paginator);

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

                        jsonObject.put("reviewList", list);
                        jsonObject.put("pages", paginator.getPages(size));

                        /*
                                Review Stats
                         */
                        ArrayList<Review> reviewListTotal = reviewService.fetchAll(film.getId());

                        DecimalFormat df = new DecimalFormat("#.#");
                        double avg = reviewService.averageStars(reviewListTotal, 0);
                        jsonObject.put("reviewAverage", df.format(avg));
                        jsonObject.put("reviewAverageRounded", round(avg));

                        for (int i = 1; i < 6; i++)
                            jsonObject.put("reviewPercentage" + i, df.format(reviewService.percentageStars(reviewListTotal, i)).replace(",", ".") + "%");

                        jsonObject.put("reviewCount", reviewService.countAll(film));
                        sendJson(response, jsonObject);
                    }
                }
                break;

                case "/add":
                    if(isAjax(request)) {
                        validate(reviewValidator.validateReview(request));

                        if(reviewService.fetch(getSessionAccount(session).getId(), Integer.parseInt(request.getParameter("filmId"))) != null)
                            throw new InvalidRequestException("Hai già pubblicato una recensione.", List.of("Hai già pubblicato una recensione."), HttpServletResponse.SC_BAD_REQUEST);

                        String  title = request.getParameter("reviewWriteTitle"),
                                description = request.getParameter("reviewWriteDescription");
                        int     stars = Integer.parseInt(request.getParameter("reviewWriteStars")),
                                filmId = Integer.parseInt(request.getParameter("filmId"));

                        Review review = new Review(getSessionAccount(session), new Film(filmId), description, title, LocalDate.now(), LocalTime.now(), stars);
                        reviewService.insert(review);

                        jsonObject.put("alert", new Alert(List.of("Recensione pubblicata con successo."), "success").toJson());
                        sendJson(response, jsonObject);
                    }
                    break;

                case "/remove":
                    if(isAjax(request)) {
                        int accountId = Integer.parseInt(request.getParameter("accountId"));
                        if(reviewService.delete(accountId))
                            jsonObject.put("result", "La recensione dell'Account "+accountId+" è stata cancellata con successo.");
                        else
                            jsonObject.put("result", "A causa di un errore interno, non è stato possibile cancellare la recensione dell'Account "+  accountId);

                        sendJson(response, jsonObject);
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
