package utils;

import lombok.Data;
import lombok.Generated;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Questa classe rappresenta l'eccezione lanciata quando avviene un errore (vedi anche {@link ErrorHandler} e {@link utils.validator.RequestValidator}.
 */
@Generated
@Data
public class InvalidRequestException extends Exception {

    /**
     * rappresenta la lista degli errori.
     */
    private final List<String> errors;

    /**
     * rappresenta il codice d'errore (status code).
     */
    private final int errorCode;

    /**
     * Costruttore dell'eccezione
     * @param msg rappresenta il messaggio da mostrare quando viene lanciata l'eccezione
     * @param errors rappresenta la lista degli errori
     * @param errorCode rappresenta il codice d'errore (status code)
     */
    public InvalidRequestException(String msg, List<String> errors, int errorCode) {
        super(msg);
        this.errors = errors;
        this.errorCode = errorCode;
    }

    /**
     * Implementa la funzionalità che permette di smistare il comportamento dell'eccezione in base allo status code.
     * @param request rappresenta l'oggetto della request
     * @param response rappresenta l'oggetto della response
     * @throws IOException
     * @throws ServletException
     */
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        switch (errorCode) {
            case HttpServletResponse.SC_BAD_REQUEST:
                request.setAttribute("alert", new Alert(errors, "danger"));
                String reloadPath = (String) request.getAttribute("reload");
                response.setStatus(errorCode);
                if(reloadPath != null && !reloadPath.isEmpty() && !reloadPath.isBlank()) {
                    response.sendRedirect(reloadPath);
                }else {
                    String backPath = (String) request.getAttribute("back");
                    request.getRequestDispatcher(backPath).forward(request, response);
                }
                break;
            default:
                response.sendError(errorCode, errors.get(0));
        }
    }

    /**
     * Implementa la funzionalità che permette di scrivere un Alert sotto forma di JSON all'interno della response.
     * Questo metodo viene usato esclusivamente per le richieste di tipo Ajax.
     * @param response rappresenta l'oggetto della response
     * @throws IOException
     */
    public void handleAjax(HttpServletResponse response) throws IOException {
        JSONObject alert = new JSONObject();

        alert.put("alert", new Alert(errors, "danger").toJson());
        new Controller().sendJson(response, alert);
    }
}
