package utils;

import model.bean.Account;
import org.json.JSONObject;
import utils.validator.RequestValidator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Questa classe offre funzionalità di utilità per le Servlet.
 */
public class Controller extends HttpServlet implements ErrorHandler{

    /**
     * Questo tag permette la connessione al database di nome "cinema"
     */
    @Resource(name = "jdbc/cinema")
    protected static DataSource source;

    /**
     * Implementa la funzionalità che permette di smistare la path.
     * @param req rappresenta l'oggetto della request
     * @return una stringa contenente il pathInfo
     */
    public String getPath(HttpServletRequest req) {
        if(req.getPathInfo() != null)
            return req.getPathInfo();
        else
            return "/";
    }

    /**
     * Implementa la funzionalità che permette di scrivere solo parte dell'URL a cui indirizzare la forward.
     * @param viewPath rappresenta il percorso dell'URL
     * @return una stringa contenente la basePath, la viewPath e l'engine.
     */
    public String view(String viewPath) {
        String basePath = getServletContext().getInitParameter("basePath");
        String engine = getServletContext().getInitParameter("engine");
        return basePath + viewPath + engine;
    }

    /**
     * Implementa la funzionalità che permette di ottenere l'URL per tornare alla pagina precedente.
     * @param request rappresenta l'oggetto della request
     * @return una stringa contenente la servletPath e il pathInfo
     */
    protected String back(HttpServletRequest request) {
        return request.getServletPath() + request.getPathInfo();
    }

    /**
     * Implementa la funzionalità che permette di effettuare la validazione dei parametri.
     * @param validator rappresenta l'insieme dei parametri
     * @throws InvalidRequestException quando viene richiamata restituisce il tipo di errore nella console
     */
    public void validate(RequestValidator validator) throws InvalidRequestException {
        if(validator.hasErrors()) {

            throw new InvalidRequestException("Validation Error", validator.getErrors(),
                    HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected String getUploadPath() {
        return System.getenv("CATALINA_IMAGES");
    }

    /**
     * Implementa la funzionalità che permette di ottenere l'oggetto account salvato in sessione.
     * @param session rappresenta l'oggetto della sessione
     * @return l'oggetto dell'account nella sessione
     */
    public Account getSessionAccount(HttpSession session) {
        return (Account) session.getAttribute("accountSession");
    }

    /**
     * Implementa la funzionalità che converte la pagina passata come parametro nell'URL in un Integer.
     * @param request rappresenta l'oggetto della request
     * @return un intero che rappresenta il numero della pagina corrente
     */
    protected int parsePage(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("page"));
    }

    /**
     * Implementa la funzionalità che permette di verificare se la richiesta è di tipo Ajax.
     * @param request rappresenta l'oggetto della request
     * @return true se è una richiesta di tipo Ajax, false altrimenti
     */
    public static boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    /**
     * Implementa la funzionalità che permette di scrivere un oggetto JSON nella response.
     * @param response rappresenta l'oggetto della response
     * @param object rappresenta l'oggetto JSON
     * @throws IOException
     */
    public void sendJson(HttpServletResponse response, JSONObject object) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(object.toString());
        writer.flush();
    }

    public ArrayList<String> getParamsArrayList(HttpServletRequest request, String params) {
        String[] paramsArray = request.getParameterValues(params);
        return new ArrayList<>(List.of(paramsArray));
    }

    public static LocalDate getLocalDateFromString(HttpServletRequest request, String param) {
        String dateString = request.getParameter(param);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateTimeFormatter = dateTimeFormatter.withLocale(Locale.ITALIAN);

        return LocalDate.parse(dateString, dateTimeFormatter);
    }

    public static LocalTime getLocalTimeFromString(HttpServletRequest request, String param) {
        String timeString = request.getParameter(param);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("H:mm");

        return LocalTime.parse(timeString, dateTimeFormatter);
    }

    public String getCryptedPassword(String pswrd) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashedPwd = digest.digest(pswrd.getBytes(StandardCharsets.UTF_8));
        StringBuilder builder = new StringBuilder();
        for(byte bit : hashedPwd)
            builder.append(String.format("%02x", bit));
        return builder.toString();
    }
}
