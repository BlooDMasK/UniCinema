package utils.validator;


import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.regex.Pattern;

/**
 * Questa classe implementa funzionalità di validazione dei parametri di un form riguardanti l'Account.
 */
public class AccountValidator {

    /**
     * Implementa la funzionalità di validazione del login
     * @param request è l'oggetto contenente i parametri
     * @return il validator
     */
    public RequestValidator validateSignin(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.assertEmail("email", "L'email non è valida", true);
        return validator;
    }

    /**
     * Implementa la funzionalità di validazione della registrazione
     * @param request è l'oggetto contenente i parametri
     * @return il validator
     */
    public RequestValidator validateSignup(HttpServletRequest request, boolean requiredParams) throws SQLException {
        RequestValidator validator = new RequestValidator(request);

        validator.assertMatch("firstname", Pattern.compile("^[A-Za-zàèìòù]{3,25}$"), "Il nome non è valido", requiredParams);

        validator.assertMatch("lastname", Pattern.compile("^[A-Za-zàèìòù' ]{3,25}$"), "Il cognome non è valido", requiredParams);

        validator.assertPassword("password", "La password non è valida", requiredParams);

        validator.assertEmail("email", "L'email non è valida", requiredParams);
        validator.assertExistingEmail("email", "L'email è già associata ad un altro account");

        return validator;
    }
}
