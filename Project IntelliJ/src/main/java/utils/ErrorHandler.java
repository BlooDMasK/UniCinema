package utils;

import lombok.Generated;
import model.bean.Account;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Questa interfaccia permette di smistare i comportamenti della servlet quando viene lanciata un'eccezione
 * <p>LEGENDA STATUS CODE</p>
 * <ul>
 *      <li>UNHAUTORIZED: 401</li>
 *      <li>FORBIDDEN: 403</li>
 *      <li>METHOD NOT ALLOWED: 405</li>
 *      <li>INTERNAL SERVER ERROR: 500</li>
 * </ul>
 */
@Generated
public interface ErrorHandler {

    /**
     * Implementa la funzionalità di verifica dei permessi di autenticazione.
     * @param session rappresenta l'oggetto della sessione
     * @throws InvalidRequestException
     */
    default void authenticate(HttpSession session) throws InvalidRequestException {
        if(session == null || session.getAttribute("accountSession") == null) {
            throw new InvalidRequestException("Errore di autenticazione", List.of("Non sei autenticato"),
                    HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    /**
     * Implementa la funzionalità di verifica dei permessi di autenticazione e di autorizzazione (se è admin).
     * @param session rappresenta l'oggetto della sessione
     * @throws InvalidRequestException
     */
    default void authorize(HttpSession session) throws InvalidRequestException {
        authenticate(session);
        Account accountSession = (Account) session.getAttribute("accountSession");
        if(!accountSession.isAdministrator()) {
            throw new InvalidRequestException("Errore di autorizzazione", List.of("Azione non consentita"),
                    HttpServletResponse.SC_FORBIDDEN);
        }
    }

    /**
     * Implementa la funzionalità che setta lo status INTERNAL SERVER ERROR, quando avviene un errore imprevisto.
     * @throws InvalidRequestException
     */
    default void internalError() throws InvalidRequestException {
        List<String> errors = List.of("Un errore imprevisto è accaduto, riprova più tardi");
        throw new InvalidRequestException("Errore interno", errors,
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    /**
     * Implementa la funzionalità che setta lo status INTERNAL SERVER ERROR, quando avviene un errore imprevisto.
     * @param error rappresenta il messaggio d'errore
     * @throws InvalidRequestException
     */
    default void internalError(String error) throws InvalidRequestException {
        List<String> errors = List.of(error);
        throw new InvalidRequestException("Errore interno", errors,
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }



    /**
     * Implementa la funzionalità che setta lo status INTERNAL SERVER ERROR, quando una risorsa non viene trovata.
     * @throws InvalidRequestException
     */
    default void notFound() throws InvalidRequestException {
        throw new InvalidRequestException("Risorsa non trovata", List.of("Risorsa non trovata"),
                HttpServletResponse.SC_NOT_FOUND);
    }

    /**
     * Implementa la funzionalità che setta lo status INTERNAL SERVER ERROR, quando una risorsa non viene trovata.
     * @param msg rappresenta il messaggio da mandare quando viene lanciata l'eccezione
     * @throws InvalidRequestException
     */
    default void notFound(String msg) throws InvalidRequestException {
        throw new InvalidRequestException(msg, List.of(msg),
                HttpServletResponse.SC_NOT_FOUND);
    }

    /**
     * Implementa la funzionalità che setta lo status METHOD NOT ALLOWED, quando un'operazione non è consentita
     * @throws InvalidRequestException
     */
    default void notAllowed() throws InvalidRequestException {
        throw new InvalidRequestException("Operazione non consentita", List.of("Operazione non permessa"),
                HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }
}
