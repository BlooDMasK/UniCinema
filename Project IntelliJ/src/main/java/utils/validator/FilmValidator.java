package utils.validator;

import lombok.Generated;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Questa classe implementa la funzionalità di validazione dei parametri di un form riguardanti il Film.
 */
@Generated
public class FilmValidator {
    /**
     * Implementa la funzionalità di validazione del film.
     * @param request è l'oggetto contenente i parametri
     * @return il validator
     * @throws ServletException
     * @throws IOException
     */
    public RequestValidator validateFilm(HttpServletRequest request) throws ServletException, IOException {
        RequestValidator requestValidator = new RequestValidator(request);
        requestValidator.assertMatch("title", Pattern.compile("^.[^{}\\[\\];_]{6,50}$"), "Il titolo non è valido.", true);
        requestValidator.assertIntRange("length", 10, 999, "La durata deve essere di minimo 10, massimo 999 minuti.", true);
        requestValidator.assertIntRange("genre", 1, 15, "Il genere deve essere compreso tra 1 e 15.", true);
        requestValidator.assertMatch("plot", Pattern.compile("^.[^\\[\\]{}@=_?]{10,1000}$"), "La trama non è valida.", true);
        requestValidator.assertFile("cover", "Devi inserire una cover (16:9).");
        requestValidator.assertFile("poster", "Devi inserire una locandina (verticale).");
        requestValidator.assertMatchArray("ActorsFirstname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il nome non è valido.");
        requestValidator.assertMatchArray("ActorsLastname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il cognome non è valido.");
        requestValidator.assertMatchArray("DirectorsFirstname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il nome non è valido.");
        requestValidator.assertMatchArray("DirectorsLastname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il cognome non è valido.");
        requestValidator.assertMatchArray("ProductionFirstname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il nome non è valido.");
        requestValidator.assertMatchArray("ProductionLastname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il cognome non è valido.");
        requestValidator.assertMatchArray("HouseProductionName", Pattern.compile("^[0-9A-Za-z-àèìòù ]{2,30}$"), "Il nome non è valido.");

        return requestValidator;
    }

    /**
     * Implementa la funzionalità di validazione per la modifica ai film.
     * @param request è l'oggetto contenente i parametri
     * @return il validator
     * @throws ServletException
     * @throws IOException
     */
    public RequestValidator validateUpdateFilm(HttpServletRequest request) throws ServletException, IOException {
        RequestValidator requestValidator = new RequestValidator(request);
        requestValidator.assertMatch("title", Pattern.compile("^.[^{}\\[\\];_]{6,50}$"), "Il titolo non è valido.", true);
        requestValidator.assertIntRange("length", 10, 999, "La durata deve essere di minimo 10, massimo 999 minuti.", true);
        requestValidator.assertIntRange("genre", 1, 15, "Il genere deve essere compreso tra 1 e 15.", true);
        requestValidator.assertMatch("plot", Pattern.compile("^.[^\\[\\]{}@=_?]{10,1000}$"), "La trama non è valida.", true);
        requestValidator.assertMatchArray("ActorsFirstname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il nome non è valido.");
        requestValidator.assertMatchArray("ActorsLastname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il cognome non è valido.");
        requestValidator.assertMatchArray("DirectorsFirstname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il nome non è valido.");
        requestValidator.assertMatchArray("DirectorsLastname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il cognome non è valido.");
        requestValidator.assertMatchArray("ProductionFirstname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il nome non è valido.");
        requestValidator.assertMatchArray("ProductionLastname", Pattern.compile("^[A-Za-z-,'àèìòù ]{2,30}$"), "Il cognome non è valido.");
        requestValidator.assertMatchArray("HouseProductionName", Pattern.compile("^[0-9A-Za-z-àèìòù ]{2,30}$"), "Il nome non è valido.");

        return requestValidator;
    }
}
