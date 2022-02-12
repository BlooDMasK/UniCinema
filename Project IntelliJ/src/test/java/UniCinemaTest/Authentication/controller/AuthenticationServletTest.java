package UniCinemaTest.Authentication.controller;

import Authentication.controller.AuthenticationServlet;
import Authentication.service.AuthenticationServiceMethods;
import ReviewManager.service.ReviewServiceMethods;
import model.bean.Account;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import utils.InvalidRequestException;
import utils.validator.AccountValidator;
import utils.validator.RequestValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class AuthenticationServletTest {

    /*
    Corrisponde a:
    authenticationServlet = new AuthenticationServlet();
    authenticationServlet = Mockito.spy(authenticationServlet);
     */
    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private HttpSession session;
    @Mock private Account account;
    @Mock private AuthenticationServiceMethods authenticationServiceMethods;
    @Mock private ReviewServiceMethods reviewServiceMethods;
    @Mock private RequestValidator validator;
    @Mock private AccountValidator accountValidator;
    @Mock private PrintWriter printWriter;
    @Mock private JSONObject jsonObject;


    @Spy private AuthenticationServlet authenticationServlet;

    @Before
    public void setUp() throws IOException {

        MockitoAnnotations.initMocks(this);

        when(authenticationServlet.getServletConfig()).thenReturn(servletConfig);
        when(authenticationServlet.getServletContext()).thenReturn(servletContext);

        when(servletContext.getInitParameter("basePath")).thenReturn("/views/");
        when(servletContext.getInitParameter("engine")).thenReturn(".jsp");

        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("accountSession")).thenReturn(account);

        when(authenticationServlet.getSessionAccount(session)).thenReturn(account);

        when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        when(response.getWriter()).thenReturn(printWriter);

        authenticationServlet.setJsonObject(jsonObject);
        authenticationServlet.setReviewService(reviewServiceMethods);
        authenticationServlet.setAccountValidator(accountValidator);
        authenticationServlet.setAuthenticationService(authenticationServiceMethods);
    }

    @Test
    public void doGetSignin() throws ServletException, IOException {

        when(authenticationServlet.getPath(request)).thenReturn("/signin");
        when(request.getRequestDispatcher("/views/site/account/signin.jsp")).thenReturn(requestDispatcher);

        authenticationServlet.doGet(request, response);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doGetValueIsLogoutTest() throws ServletException, IOException, InvalidRequestException {
        when(authenticationServlet.getPath(request)).thenReturn("/logout");
        /*
        Authenticate è un metodo default e mockito non permette di richiamarlo.
        Quando spio un oggetto reale e voglio che il metodo non faccio nulla, posso usare doNothing.

        - Se non uso doNothing e faccio il mock di un oggetto, il metodo reale non viene chiamato.
        - Se non uso doNothing e faccio lo spy di un oggetto, il metodo reale viene chiamato.
         */
        doNothing().when(authenticationServlet).authenticate(session);

        authenticationServlet.doGet(request,response);

        assertNotEquals(null, session);                                   //verifica che session sia diverso da null
        assertNotEquals(null, session.getAttribute("accountSession")); //verifica account session diverso da null
        verify(authenticationServlet).authenticate(session);
        verify(session).removeAttribute("accountSession");
        verify(session).invalidate();
        verify(response).sendRedirect(servletContext.getContextPath() +"/pages");
    }

    @Test
    public void doGetValueProfile() throws InvalidRequestException, ServletException, IOException, SQLException {
        when(authenticationServlet.getPath(request)).thenReturn("/profile");
        when(request.getRequestDispatcher("/views/site/account/profile.jsp")).thenReturn(requestDispatcher);

        doNothing().when(authenticationServlet).authenticate(session);

        when(reviewServiceMethods.countByAccountId(anyInt())).thenReturn(1);

        authenticationServlet.doGet(request,response);

        assertNotEquals(null, session);                                   //verifica che session sia diverso da null
        assertNotEquals(null, session.getAttribute("accountSession")); //verifica account session diverso da null

        verify(request).setAttribute("account", account);
        verify(reviewServiceMethods).countByAccountId(account.getId());

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPostValueEdit() throws InvalidRequestException, SQLException, ServletException, IOException {
        when(authenticationServlet.getPath(request)).thenReturn("/edit");

        when(request.getParameter("firstname")).thenReturn("Gerardo");
        when(request.getParameter("lastname")).thenReturn("Leone");
        when(request.getParameter("email")).thenReturn("g.leone@gmail.com");
        when(request.getParameter("password")).thenReturn("Password1234");
        when(accountValidator.validateSignup(request, false)).thenReturn(validator);
        doNothing().when(authenticationServlet).validate(validator);
        when(authenticationServiceMethods.edit(account)).thenReturn(true);

        authenticationServlet.doPost(request, response);

        verify(session).setAttribute("accountSession", account);
        verify(authenticationServlet).sendJson(response, jsonObject);
    }

    @Test
    public void doPostValueSignin() throws SQLException, ServletException, IOException, NoSuchAlgorithmException {
        when(authenticationServlet.getPath(request)).thenReturn("/signin");

        when(accountValidator.validateSignin(request)).thenReturn(validator);
        String email = "g.leone@gmail.com",
               password = "password";
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(authenticationServiceMethods.signin(email, authenticationServlet.getCryptedPassword(password))).thenReturn(account);
        authenticationServlet.doPost(request, response);

        verify(session).setAttribute("accountSession", account);
        verify(response).sendRedirect(servletContext.getContextPath()+"/pages");
    }
}
