package UniCinemaTest.Signup.controller;

import Authentication.service.AuthenticationServiceMethods;
import ReviewManager.service.ReviewServiceMethods;
import Signup.controller.SignupServlet;
import Signup.service.SignupServiceMethods;
import model.bean.Account;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
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
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignupServletTest {

    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock private HttpSession session;
    @Mock private Account account;
    @Mock private RequestValidator validator;
    @Mock private PrintWriter printWriter;
    @Mock private SignupServiceMethods signupServiceMethods;
    @Mock private AccountValidator accountValidator;

    @Spy private SignupServlet signupServlet;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        when(signupServlet.getServletConfig()).thenReturn(servletConfig);
        when(signupServlet.getServletContext()).thenReturn(servletContext);

        when(servletContext.getInitParameter("basePath")).thenReturn("/views/");
        when(servletContext.getInitParameter("engine")).thenReturn(".jsp");

        when(request.getSession(false)).thenReturn(session);
        when(request.getSession()).thenReturn(session);

        when(session.getAttribute("accountSession")).thenReturn(account);

        when(signupServlet.getSessionAccount(session)).thenReturn(account);

        when(response.getWriter()).thenReturn(printWriter);

        signupServlet.setSignupService(signupServiceMethods);
        signupServlet.setAccountValidator(accountValidator);
    }

    @Test
    public void doGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/views/site/account/signup.jsp")).thenReturn(requestDispatcher);

        signupServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void doPost() throws SQLException, ServletException, IOException {
        when(request.getAttribute("back")).thenReturn("/views/site/account/signup.jsp");
        when(accountValidator.validateSignup(request, true)).thenReturn(validator);
        when(request.getParameter("email")).thenReturn("gerardo@gmail.com");
        when(request.getParameter("password")).thenReturn("Ciao1234");
        when(request.getParameter("firstname")).thenReturn("Gerardo");
        when(request.getParameter("lastname")).thenReturn("Leone");
        when(signupServiceMethods.signup(any(Account.class))).thenReturn(true);

        signupServlet.doPost(request, response);

        verify(response).sendRedirect(servletContext.getContextPath()+"/pages");
    }

    @Test
    public void doPostSignupFalse() throws SQLException, ServletException, IOException {
        String backPath = "/views/site/account/signup.jsp";
        when(request.getAttribute("back")).thenReturn(backPath);
        when(request.getRequestDispatcher(backPath)).thenReturn(requestDispatcher);

        when(accountValidator.validateSignup(request, true)).thenReturn(validator);
        when(request.getParameter("email")).thenReturn("gerardo@gmail.com");
        when(request.getParameter("password")).thenReturn("Ciao1234");
        when(request.getParameter("firstname")).thenReturn("Gerardo");
        when(request.getParameter("lastname")).thenReturn("Leone");
        when(signupServiceMethods.signup(any(Account.class))).thenReturn(false);

        signupServlet.doPost(request, response);

        verify(requestDispatcher).forward(request, response);
    }
}
