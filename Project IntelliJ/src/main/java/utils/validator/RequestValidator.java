package utils.validator;

import lombok.Getter;
import model.bean.Account;
import model.dao.AccountDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Classe che fornisce i parametri di validazione
 */
public class RequestValidator {

    /**
     * Rappresenta la lista degli errori da mostrare all'utente che non rispetta la validazione.
     */
    @Getter
    private final List<String> errors;

    /**
     * Rappresenta l'oggetto contenente i parametri da validare.
     */
    private final HttpServletRequest request;

    /**
     * Rappresenta il pattern per parametri formati solo da numeri interi.
     */
    private static final Pattern INT_PATTERN = Pattern.compile("^\\d+$");

    /**
     * Rappresenta il pattern per parametri formati solo da numeri reali.
     */
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^(-)?(0|[1-9]\\d+)\\.\\d+$");

    /**
     *
     * @param request rappresenta l'oggetto contenente i parametri da validare.
     */
    public RequestValidator(HttpServletRequest request) {
        this.errors = new ArrayList<>();
        this.request = request;
    }

    /**
     * Implementa la funzionalità di verifica degli errori.
     * @return true se ci sono errori, false altrimenti.
     */
    public boolean hasErrors() { return !errors.isEmpty(); }

    /**
     * Implementa la funzionalità di verifica delle condizioni.
     * @param condition rappresenta risultante delle condizioni
     * @param message di errore
     * @return true se la condizione è rispettata, false altrimenti
     */
    private boolean gatherError(boolean condition, String message) {
        if(condition)
            return true;

        errors.add(message);
        return false;
    }

    /**
     * Implementa la funzionalità che verifica se una data Stringa è vuota.
     * @param value rappresenta la stringa da verificare
     * @return true se è nulla, false altrimenti
     */
    public static boolean isNull(String value) {
        return value == null || value.isBlank();
    }

    /**
     * Implementa la funzionalità che verifica se una stringa rispetta un determinato pattern.
     * @param value rappresenta la stringa da verificare
     * @param regexp rappresenta il pattern da rispettare
     * @param msg rappresenta il messaggio d'errore
     * @param required rappresenta l'obbligatorietà del parametro.
     * @return true se il pattern è rispettato, false altrimenti
     */
    public boolean assertMatch(String value, Pattern regexp, String msg, boolean required) {
        String param = request.getParameter(value);
        boolean condition;
        if(required)
            condition = !isNull(param) && regexp.matcher(param).matches();
        else {
            if (!isNull(param)) //non vuoto
                condition = regexp.matcher(param).matches();
            else //vuoto
                condition = true; //non controllo la regex
        }

        return gatherError(condition, msg);
    }

    public boolean assertMatchArray(String value, Pattern regexp, String msg) {
        String[] array = request.getParameterValues(value);

        boolean condition = true;

        if(array.length == 0)
            condition = false;
        else
            for(String param : array)
                condition = condition && (!isNull(param) && regexp.matcher(param).matches());

        return gatherError(condition, msg);
    }

    /**
     * Implementa la funzionalità che verifica se una data Stringa rispetta il pattern degli Interi.
     * @param value rappresenta la stringa da verificare
     * @param msg rappresenta il messaggio d'errore
     * @return true se la stringa è formata da interi, false altrimenti
     */
    public boolean assertInt(String value, String msg, boolean required) {
        return assertMatch(value, INT_PATTERN, msg, required);
    }

    /**
     * Implementa la funzionalità che verifica se un certo valore rientra nel limite minimo e massimo degli interi specificati.
     * @param value rappresenta la stringa da verificare
     * @param msg rappresenta il messaggio d'errore
     * @return true se il valore rientra nei limiti, false altrimenti
     */
    public boolean assertIntRange(String value, int min, int max, String msg, boolean required) {
        String param = request.getParameter(value);
        boolean condition = false;

        if(assertInt(value, "Devi inserire un intero.", required)) {
            int integer = Integer.parseInt(param);

            condition = integer >= min && integer <= max;
        }

        return gatherError(condition, msg);
    }

    /**
     * Implementa la funzionalità che verifica se un file è stato effettivamente caricato.
     * @param value rappresenta la stringa da verificare
     * @param msg rappresenta il messaggio d'errore
     * @return true se il file è stato caricato, false altrimenti.
     * @throws ServletException
     * @throws IOException
     */
    public boolean assertFile(String value, String msg) throws ServletException, IOException {
        boolean condition = !(request.getPart(value).getSubmittedFileName().isEmpty());
        return gatherError(condition, msg);
    }

    /**
     * Implementa la funzionalità che verifica se una data Stringa rispetta il pattern dei Double.
     * @param value rappresenta la stringa da verificare
     * @param msg rappresenta il messaggio d'errore
     * @return true se la stringa è formata da numeri reali, false altrimenti
     */
    public boolean assertDouble(String value, String msg, boolean required) {
        return assertMatch(value, DOUBLE_PATTERN, msg, required);
    }

    /**
     * Implementa la funzionalità che verifica se una data Stringa rispetta il pattern delle Email.
     * @param value rappresenta la stringa da verificare
     * @param msg rappresenta il messaggio d'errore
     * @return true se la stringa segue il pattern, false altrimenti
     */
    public boolean assertEmail(String value, String msg, boolean required) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$");
        return assertMatch(value, pattern, msg, required);
    }


    /**
     * Implementa la funzionalità che verifica se una data Email è già registrata nel Database
     * @param value rappresenta la stringa da verificare
     * @param msg rappresenta il messaggio d'errore
     * @return true se l'email esiste già, false altrimenti
     * @throws SQLException
     */
    public boolean assertExistingEmail(String value, String msg) throws SQLException {
        Optional<Account> account = new AccountDAO().fetch(request.getParameter(value));

        return gatherError(account.isEmpty(), msg);
    }

    /**
     * Implementa la funzionalità che verifica se una data Stringa rispetta il pattern delle Password.
     * @param value rappresenta la stringa da verificare
     * @param msg rappresenta il messaggio d'errore
     * @return true se la stringa segue il pattern, false altrimenti
     */
    public boolean assertPassword(String value, String msg, boolean required) {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z;:=_+^#$@!%*?&àèìòù\\d]{8,32}$");
        return assertMatch(value, pattern, msg, required);
    }

    //TODO: da fare javadoc
    public boolean assertInts(String values, String msg) {
        String[] params = request.getParameterValues(values);
        boolean allInt = Arrays.stream(params).allMatch(param -> INT_PATTERN.matcher(param).matches());
        return gatherError(allInt, msg);
    }

    //TODO: da fare il javadoc
    public boolean assertSize(String first, String second, String msg) {
        String[] firstList = request.getParameterValues(first);
        String[] secondList = request.getParameterValues(second);
        return gatherError(firstList.length == secondList.length, msg);
    }
}
