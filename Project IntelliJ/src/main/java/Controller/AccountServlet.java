package Controller;

import Controller.api.Controller;
import Controller.api.ErrorHandler;
import Model.api.InvalidRequestException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AccountServlet", value = "/account/*")
public class AccountServlet extends Controller implements ErrorHandler {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String path = getPath(request);
            System.out.println(path);
            switch(path) {
                case "/signin":
                    request.getRequestDispatcher(view("site/account/signin")).forward(request, response);
                    break;

                case "/signup":
                    request.getRequestDispatcher(view("site/account/signup")).forward(request, response);
                    break;

                default:
                    notFound();
            }
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            e.handle(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
