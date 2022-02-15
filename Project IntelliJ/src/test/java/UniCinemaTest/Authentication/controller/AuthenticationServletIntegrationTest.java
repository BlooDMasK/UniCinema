package UniCinemaTest.Authentication.controller;

import Authentication.controller.AuthenticationServlet;
import Authentication.service.AuthenticationService;
import Authentication.service.AuthenticationServiceMethods;
import model.bean.Account;
import model.dao.account.AccountDAO;
import model.dao.account.AccountDAOMethods;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthenticationServletIntegrationTest {

    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private PrintWriter printWriter;

    @Spy private AuthenticationServlet authenticationServlet;
    private AuthenticationService authenticationService;
    private AccountValidator accountValidator;

    @Before
    public void setUp() throws SQLException, IOException {

        MockitoAnnotations.initMocks(this);

        when(authenticationServlet.getServletConfig()).thenReturn(servletConfig);
        when(authenticationServlet.getServletContext()).thenReturn(servletContext);

        when(servletContext.getInitParameter("basePath")).thenReturn("/views/");
        when(servletContext.getInitParameter("engine")).thenReturn(".jsp");

        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);

        when(request.getHeader("X-Requested-With")).thenReturn("XMLHttpRequest");
        when(response.getWriter()).thenReturn(printWriter);

        authenticationService = new AuthenticationServiceMethods();
        accountValidator = new AccountValidator();
    }

    @Test
    public void doPostSignin() throws SQLException, ServletException, IOException, NoSuchAlgorithmException, InvalidRequestException {
        when(authenticationServlet.getPath(request)).thenReturn("/signin");

        String email = "gleone2000@gmail.com";
        String password = "Ciao1234";
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);

        Account account = authenticationService.signin(email, authenticationServlet.getCryptedPassword(password));
        assertNotNull(account);

        RequestValidator requestValidator = accountValidator.validateSignin(request);
        assertFalse(requestValidator.hasErrors());

        authenticationServlet.doPost(request, response);

        assertEquals(request.getParameter("email"), "gleone2000@gmail.com");
        assertEquals(request.getParameter("password"), "Ciao1234");
        verify(session).setAttribute("accountSession", account);
        verify(authenticationServlet).validate(any(RequestValidator.class));
    }
}
