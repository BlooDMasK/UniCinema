package utils.validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

public class FilmValidator {
    public RequestValidator validateFilm(HttpServletRequest request) throws ServletException, IOException {
        RequestValidator requestValidator = new RequestValidator(request);
        requestValidator.assertMatch("title", Pattern.compile("^.{1,50}$"), "Il titolo deve essere di 3-50 caratteri.", true);
        requestValidator.assertIntRange("length", 1, 600, "La durata deve essere di minimo 1 minuto massimo 600 minuti.", true);
        requestValidator.assertIntRange("genre", 1, 15, "Il genere deve essere compreso tra 1 e 15.", true);
        requestValidator.assertMatch("plot", Pattern.compile("^.{10,1000}"), "La trama deve essere di 10-1000 caratteri.", true);
        requestValidator.assertFile("cover", "Devi inserire una cover (16:9).");
        requestValidator.assertFile("poster", "Devi inserire una locandina (verticale).");
        requestValidator.assertMatchArray("ActorsFirstname", Pattern.compile("^.{3,30}$"), "Il nome deve essere compreso tra 3-30 caratteri.");
        requestValidator.assertMatchArray("ActorsLastname", Pattern.compile("^.{3,30}$"), "Il cognome deve essere compreso tra 3-30 caratteri.");
        requestValidator.assertMatchArray("DirectorsFirstname", Pattern.compile("^.{3,30}$"), "Il nome deve essere compreso tra 3-30 caratteri.");
        requestValidator.assertMatchArray("DirectorsLastname", Pattern.compile("^.{3,30}$"), "Il cognome deve essere compreso tra 3-30 caratteri.");
        requestValidator.assertMatchArray("ProductionFirstname", Pattern.compile("^.{3,30}$"), "Il nome deve essere compreso tra 3-30 caratteri.");
        requestValidator.assertMatchArray("ProductionLastname", Pattern.compile("^.{3,30}$"), "Il cognome deve essere compreso tra 3-30 caratteri.");
        requestValidator.assertMatchArray("HouseProductionName", Pattern.compile("^.{3,50}$"), "Il nome deve essere compreso tra 3-50 caratteri.");

        return requestValidator;
    }
}
