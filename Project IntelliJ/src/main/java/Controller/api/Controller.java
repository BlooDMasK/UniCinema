package Controller.api;

import Model.account.AccountSession;
import Model.api.InvalidRequestException;
import Model.cart.Cart;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class Controller extends HttpServlet implements ErrorHandler{

    @Resource(name = "jdbc/cinema")
    protected static DataSource source;

    protected String getPath(HttpServletRequest req) {
        if(req.getPathInfo() != null)
            return req.getPathInfo();
        else
            return "/";
    }

    protected String view(String viewPath) {
        String basePath = getServletContext().getInitParameter("basePath");
        String engine = getServletContext().getInitParameter("engine");
        return basePath + viewPath + engine;
    }

    protected String back(HttpServletRequest request) {
        return request.getServletPath() + request.getPathInfo();
    }

    protected void validate(RequestValidator validator) throws InvalidRequestException {
        if(validator.hasErrors()) {
            for(String msg : validator.getErrors())
                System.out.println(msg);

            throw new InvalidRequestException("Validation Error", validator.getErrors(),
                    HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected String getUploadPath() {
        return System.getenv("CATALINA_HOME") + File.separator + "uploads" + File.separator;
    }

    protected Cart getSessionCart(HttpSession session) {
        return (Cart) session.getAttribute("cartSession");
    }

    protected AccountSession getSessionAccount(HttpSession session) {
        return (AccountSession) session.getAttribute("accountSession");
    }

    protected int parsePage(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("page"));
    }

    protected boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    protected void sendJson(HttpServletResponse response, JSONObject object) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(object.toString());
        writer.flush();
    }
}
