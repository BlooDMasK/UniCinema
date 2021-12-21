package Controller.account;

import Controller.api.RequestValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AccountValidator {
    static RequestValidator validateSignin(HttpServletRequest request) {
        RequestValidator validator = new RequestValidator(request);
        validator.assertEmail("email", "L'email deve essere in un formato valido");
        return validator;
    }

    public static RequestValidator validateSignup(HttpServletRequest request) throws SQLException {
        RequestValidator validator = new RequestValidator(request);
        validator.assertMatch("firstname", Pattern.compile("^[\\w\\s]{3,25}$"), "Il nome deve avere lunghezza tra 3 e 25 caratteri");
        validator.assertMatch("lastname", Pattern.compile("^[\\w\\s]{3,25}$"), "Il cognome deve avere lunghezza tra 3 e 25 caratteri");
        validator.assertPassword("password", "La password deve essere in un formato valido");
        validator.assertEmail("email", "L'email deve essere in un formato valido");
        validator.assertExistingEmail("email", "L'email è già associata ad un altro account");
        return validator;
    }
}
