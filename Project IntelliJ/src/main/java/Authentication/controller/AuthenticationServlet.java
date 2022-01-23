package Authentication.controller;

import Authentication.service.AuthenticationService;
import Authentication.service.AuthenticationServiceMethods;
import ReviewManager.service.ReviewService;
import ReviewManager.service.ReviewServiceMethods;
import model.bean.Account;
import org.json.JSONObject;
import utils.*;
import utils.validator.AccountValidator;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import static utils.validator.RequestValidator.isNull;

@WebServlet(name = "AuthenticationServlet", value = "/account/*")
public class AuthenticationServlet extends Controller implements ErrorHandler {

    AuthenticationService authenticationService;
    ReviewService reviewService;
    AccountValidator accountValidator;

    public void setAccountValidator(AccountValidator accountValidator) {
        this.accountValidator = accountValidator;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setReviewService(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    public AuthenticationServlet() {
        authenticationService = new AuthenticationServiceMethods();
        reviewService = new ReviewServiceMethods();
        accountValidator = new AccountValidator();
    }

    /**
     * Implementa le funzionalità svolte durante una chiamata di tipo GET
     * @param request oggetto rappresentante la chiamata Http request
     * @param response oggetto rappresentante la chiamata Http response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            /**
             * Rappresenta il path che permette di smistare le funzionalità.
             */
            String path = getPath(request);

            /**
             * Rappresenta la sessione attuale {@link HttpSession}.
             */
            HttpSession session = request.getSession();

            switch (path) {

                /**
                 * Indirizza l'utente alla pagina di login.
                 */
                case "/signin":
                    request.getRequestDispatcher(view("site/account/signin")).forward(request, response);
                    break;

                /**
                 * Implementa la funzionalità di visualizzazione del profilo.
                 */
                case "/profile":
                    authenticate(session);

                    Account account = getSessionAccount(session);

                    request.setAttribute("account", account);

                    int reviewCount = reviewService.countByAccountId(account.getId());
                    request.setAttribute("reviewCount", reviewCount);

                    request.getRequestDispatcher(view("site/account/profile")).forward(request, response);
                    break;

                /**
                 * Implementa la funzionalità di logout
                 */
                case "/logout":
                    session = request.getSession(false);
                    authenticate(session);

                    session.removeAttribute("accountSession");
                    session.invalidate();
                    response.sendRedirect(getServletContext().getContextPath()+"/pages");
                    break;

                default:
                    notFound();
            }
        } catch (SQLException ex) {
            log(ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        } catch (InvalidRequestException e) {
            log(e.getMessage());
            e.handle(request, response);
        }
    }

    /**
     * Implementa le funzionalità svolte durante una chiamata di tipo POST
     * @param request oggetto rappresentante la chiamata Http request
     * @param response oggetto rappresentante la chiamata Http response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            /**
             * Rappresenta il path che permette di smistare le funzionalità.
             */
            String path = getPath(request);

            /**
             * Rappresenta la sessione attuale {@link HttpSession}.
             */
            HttpSession session = request.getSession();
            switch (path) {

                /**
                 * Implementa la funzionalità di modifica dell'Account.
                 */
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
                            validate(accountValidator.validateSignup(request, false));
                            Account accountSession = getSessionAccount(session);
                            if(accountSession != null) {
                                if(!isNull(firstname))
                                    accountSession.setFirstname(firstname);

                                if(!isNull(lastname))
                                    accountSession.setLastname(lastname);

                                if(!isNull(email))
                                    accountSession.setEmail(email);

                                if(!isNull(password))
                                    accountSession.setPswrd(password);

                                if(authenticationService.edit(accountSession)) {
                                    session.setAttribute("accountSession", accountSession);

                                    JSONObject alert = new JSONObject();

                                    alert.put("alert", new Alert(List.of("Account aggiornato con successo"), "success").toJson());
                                    sendJson(response, alert);
                                } else
                                    internalError();
                            } else
                                notFound("Non è stato trovato l'account di sessione.");
                        }
                    }
                    break;

                /**
                 * Implementa la funzionalità di login di un Utente Registrato.
                 */
                case "/signin":
                    request.setAttribute("back", view("site/account/signin"));
                    validate(accountValidator.validateSignin(request));
                    String email = request.getParameter("email"),
                           pswrd = request.getParameter("password");
                    Account optAccountSignin = authenticationService.signin(email, pswrd);
                    if(optAccountSignin != null) {
                        session.setAttribute("accountSession", optAccountSignin);
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
