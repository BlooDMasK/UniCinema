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
    public static RequestValidator validateSignin(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.assertEmail("email", "L'email deve essere in un formato valido", true);
        return validator;
    }

    /**
     * Implementa la funzionalità di validazione della registrazione
     * @param request è l'oggetto contenente i parametri
     * @return il validator
     */
    public static RequestValidator validateSignup(HttpServletRequest request, boolean requiredParams) throws SQLException {
        RequestValidator validator = new RequestValidator(request);
        validator.assertMatch("firstname", Pattern.compile("^[\\w\\s]{3,25}$"), "Il nome deve avere lunghezza tra 3 e 25 caratteri", requiredParams);
        validator.assertMatch("lastname", Pattern.compile("^[\\w\\s]{3,25}$"), "Il cognome deve avere lunghezza tra 3 e 25 caratteri", requiredParams);
        validator.assertPassword("password", "La password deve essere in un formato valido", requiredParams);
        validator.assertEmail("email", "L'email deve essere in un formato valido", requiredParams);
        validator.assertExistingEmail("email", "L'email è già associata ad un altro account");
        return validator;
    }
}
