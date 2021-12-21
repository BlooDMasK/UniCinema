package Model.api;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static Controller.api.Controller.sendJson;

public class InvalidRequestException extends Exception {
    private final List<String> errors;
    private final int errorCode;

    public InvalidRequestException(String msg, List<String> errors, int errorCode) {
        super(msg);
        this.errors = errors;
        this.errorCode = errorCode;
    }

    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        switch (errorCode) {
            case HttpServletResponse.SC_BAD_REQUEST:
                request.setAttribute("alert", new Alert(errors, "danger"));
                String backPath = (String) request.getAttribute("back");
                response.setStatus(errorCode);
                request.getRequestDispatcher(backPath).forward(request, response);
                break;
            default:
                response.sendError(errorCode, errors.get(0));
        }
    }

    public void handleAjax(HttpServletResponse response) throws IOException, ServletException {
        JSONObject alert = new JSONObject();

        alert.put("alert", new Alert(errors, "danger").toJson());
        sendJson(response, alert);
    }

    public List<String> getErrors() { return errors; }
}
