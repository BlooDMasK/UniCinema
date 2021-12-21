package Controller.account;

import Controller.api.Controller;
import Controller.api.ErrorHandler;
import Model.account.Account;
import Model.account.AccountDAO;
import Model.account.AccountSession;
import Model.api.Alert;
import Model.api.InvalidRequestException;
import Model.review.ReviewDAO;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static Controller.api.RequestValidator.isNull;

@WebServlet(name = "AccountServlet", value = "/account/*")
public class AccountServlet extends Controller implements ErrorHandler {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String path = getPath(request);
            HttpSession session = request.getSession();
            AccountDAO accountDAO = new AccountDAO();
            switch(path) {
                case "/signin":
                    request.getRequestDispatcher(view("site/account/signin")).forward(request, response);
                    break;

                case "/signup":
                    request.getRequestDispatcher(view("site/account/signup")).forward(request, response);
                    break;

                case "/profile":
                    Optional<Account> account = accountDAO.fetch(((AccountSession) session.getAttribute("accountSession")).getEmail());
                    if(account.isPresent()) {
                        request.setAttribute("account", account.get());

                        int reviewCount = new ReviewDAO().countByAccountId(account.get().getId());
                        request.setAttribute("reviewCount", reviewCount);

                        request.getRequestDispatcher(view("site/account/profile")).forward(request, response);
                    } else
                        internalError();
                    break;

                case "/logout":
                    session = request.getSession(false);
                    authenticate(session);

                    //TODO Cart reset

                    session.removeAttribute("accountSession");
                    session.invalidate();
                    response.sendRedirect(getServletContext().getContextPath()+"/pages");
                    break;

                default:
                    notFound();
            }
        } catch (SQLException /*| NoSuchAlgorithmException */ ex) {
            log(ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            e.handle(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String path = getPath(request);
            AccountDAO accountDAO = new AccountDAO();
            HttpSession session = request.getSession();
            switch(path) {
                case "/signup":
                    request.setAttribute("back", view("site/account/signup"));
                    validate(AccountValidator.validateSignup(request));
                    Account tmpAccountSignup = new Account();
                    tmpAccountSignup.setEmail(request.getParameter("email"));
                    tmpAccountSignup.setPswrd(request.getParameter("password"));
                    tmpAccountSignup.setFirstname(request.getParameter("firstname"));
                    tmpAccountSignup.setLastname(request.getParameter("lastname"));
                    tmpAccountSignup.setAdministrator(false);
                    if(accountDAO.insert(tmpAccountSignup)) {
                        AccountSession accountSession = new AccountSession(tmpAccountSignup);
                        session.setAttribute("accountSession", accountSession);

                        //TODO Cart
                        //TODO cartSession

                        //TODO redirect page di N secondi prima di andare in homepage
                        response.sendRedirect(getServletContext().getContextPath()+"/pages");
                    } else {
                        request.setAttribute("alert", new Alert(List.of("Errore nell'inserimento dei dati"), "danger"));
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                        throw new InvalidRequestException("Errore nell'inserimento dei dati",
                                List.of("Errore nell'inserimento dei dati"), HttpServletResponse.SC_BAD_REQUEST);
                    }
                    break;

                case "/edit":
                    if(isAjax(request)) {
                        String firstname = request.getParameter("firstname"),
                                lastname = request.getParameter("lastname"),
                                email = request.getParameter("email"),
                                password = request.getParameter("password");

                        if(firstname.isEmpty() && lastname.isEmpty() && email.isEmpty() && password.isEmpty()) {

                            throw new InvalidRequestException("Devi compilare almeno un campo",
                                    List.of("Devi compilare almeno un campo"), HttpServletResponse.SC_BAD_REQUEST);

                        } else { //Se compila almeno un campo
                            validate(AccountValidator.validateSignup(request));
                            AccountSession accountSession = (AccountSession) session.getAttribute("accountSession");
                            Optional<Account> tmpAccountEdit = accountDAO.fetch(accountSession.getEmail());
                            if(tmpAccountEdit.isPresent()) {

                                System.out.println("value: " + tmpAccountEdit.get().getFirstname());

                                if(!isNull(firstname))
                                    tmpAccountEdit.get().setFirstname(firstname);

                                if(!isNull(lastname))
                                    tmpAccountEdit.get().setLastname(lastname);

                                if(!isNull(email))
                                    tmpAccountEdit.get().setEmail(email);

                                if(!isNull(password))
                                    tmpAccountEdit.get().setPswrd(password);

                                System.out.println("parameter: " + password + " | value: " + tmpAccountEdit.get().getPswrd());

                                accountDAO.update(tmpAccountEdit.get());
                                session.setAttribute("accountSession", new AccountSession(tmpAccountEdit.get()));

                                JSONObject alert = new JSONObject();

                                alert.put("alert", new Alert(List.of("Account aggiornato con successo"), "success").toJson());
                                sendJson(response, alert);

                            } else
                                notFound("Account non trovato nel database");
                        }
                    }
                    break;

                case "/signin":
                    request.setAttribute("back", view("site/account/signin"));
                    validate(AccountValidator.validateSignin(request));
                    Account tmpAccountSignin = new Account();
                    tmpAccountSignin.setEmail(request.getParameter("email"));
                    tmpAccountSignin.setPswrd(request.getParameter("password"));
                    Optional<Account> optAccountSignin = accountDAO.find(tmpAccountSignin.getEmail(), tmpAccountSignin.getPswrd(), false);
                    if(optAccountSignin.isPresent()) {
                        session.setAttribute("accountSession", new AccountSession(optAccountSignin.get()));

                        //TODO Cart
                        //TODO cartSession

                        //TODO redirect page di N secondi prima di andare in homepage
                        response.sendRedirect(getServletContext().getContextPath()+"/pages");
                    } else {
                        request.setAttribute("alert", new Alert(List.of("Credenziali non valide"), "danger"));
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                        throw new InvalidRequestException("Credenziali non valide",
                                List.of("Credenziali non valide"), HttpServletResponse.SC_BAD_REQUEST);
                    }
                    break;
                default:
                    notAllowed();
            }
        } catch (SQLException | NoSuchAlgorithmException ex) {
            log(ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            if(isAjax(request))
                e.handleAjax(response);
            else
                e.handle(request, response);
        }
    }
}
