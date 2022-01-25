package utils.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Questa classe implementa funzionalità di validazione dei parametri di un form riguardanti la Recensione.
 */
public class ReviewValidator {

    /**
     * Implementa la funzionalità di validazione dela recensione
     * @param request è l'oggetto contenente i parametri
     * @return il validator
     */
    public static RequestValidator validateReview(HttpServletRequest request) {
        RequestValidator requestValidator = new RequestValidator(request);
        requestValidator.assertMatch("reviewWriteTitle", Pattern.compile("^[A-Za-z':;.,àèìòù0-9?! ]{3,20}$"), "Il titolo non è valido", true);
        requestValidator.assertMatch("reviewWriteDescription", Pattern.compile("^[A-Za-z':;.,àèìòù0-9?! ]{5,500}$"), "La descrizione non è valida", true);
        requestValidator.assertIntRange("reviewWriteStars", 1, 5, "La valutazione della recensione deve essere minimo di 1 stella e massimo di 5", true);

        return requestValidator;
    }

}
