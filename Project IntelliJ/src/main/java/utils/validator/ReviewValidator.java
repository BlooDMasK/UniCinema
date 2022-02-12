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
    public RequestValidator validateReview(HttpServletRequest request) {
        RequestValidator requestValidator = new RequestValidator(request);
        requestValidator.assertMatch("reviewWriteTitle", Pattern.compile("^.{1,50}$"), "Il titolo deve essere di 1-50 caratteri.", true);
        requestValidator.assertMatch("reviewWriteDescription", Pattern.compile("^.{1,500}$"), "La recensione deve essere di 1-500 caratteri.", true);
        requestValidator.assertIntRange("reviewWriteStars", 1, 5, "La valutazione della recensione deve essere minimo di 1 stella e massimo di 5.", true);

        return requestValidator;
    }

}
