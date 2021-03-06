package UniCinemaTest.Authentication.service;

import Authentication.service.AuthenticationServiceMethods;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import model.bean.Account;
import model.dao.account.AccountDAOMethods;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static junit.framework.TestCase.assertEquals;
import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class AuthenticationServiceMethodsTest {

    @Mock private AccountDAOMethods accountDAOMethods;

    private AuthenticationServiceMethods authenticationServiceMethods;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        authenticationServiceMethods = new AuthenticationServiceMethods();

        authenticationServiceMethods.setAccountDAO(accountDAOMethods);
    }

    @Test
    @Parameters(value = "utente@gmail.com, Utente1234")
    public void signin(String email, String pswrd) throws SQLException, NoSuchAlgorithmException {

        Account account = new Account(email,pswrd,false);

        when(accountDAOMethods.find(email,pswrd,false)).thenReturn(account);
        assertEquals(account, authenticationServiceMethods.signin(email,pswrd));
    }

    @Test
    @Parameters(value = "utente@gmail.com")
    public void fetch(String email) throws SQLException{

        Account account = new Account();
        account.setEmail(email);
        when(accountDAOMethods.fetch(email)).thenReturn(account);
        assertEquals(account, authenticationServiceMethods.fetch(email));

    }

    @Test
    @Parameters(method = "provideAccount")
    public void edit(Account account) throws SQLException{

        when(accountDAOMethods.update(account)).thenReturn(true);
        assertEquals(authenticationServiceMethods.edit(account), true);
    }

    private Object[] provideAccount() throws NoSuchAlgorithmException {
        return $(new Account("utente@gmail.com",
                "nomeUtente",
                "cognomeUtente",
                "Utente1234",
                4,
                false));
    }

    /*
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        authenticationServiceMethods = new AuthenticationServiceMethods();

        authenticationServiceMethods.setAccountDAO(accountDAO);
    }


    @ParameterizedTest
    @MethodSource("provideAccountInfo")
    public void signin(String email, String pswrd) throws SQLException, NoSuchAlgorithmException {

        Account account = new Account(email,pswrd,false);

       when(accountDAO.find(email,pswrd,false)).thenReturn(account);
       assertEquals(account, authenticationServiceMethods.signin(email,pswrd));
    }



    @ParameterizedTest
    @ValueSource(strings = {"utente@gmail.com"})
    public void fetch(String email) throws SQLException{

        Account account = new Account();
        account.setEmail(email);
        when(accountDAO.fetch(email)).thenReturn(account);
        assertEquals(account, authenticationServiceMethods.fetch(email));

    }

    @ParameterizedTest
    @MethodSource("provideAccount")
    public void edit(Account account) throws SQLException{

        when(accountDAO.update(account)).thenReturn(true);
        assertEquals(authenticationServiceMethods.edit(account), true);
    }


    private static Stream<Arguments> provideAccount() throws NoSuchAlgorithmException {
        return Stream.of(Arguments.of(new Account("utente@gmail.com",
                "nomeUtente",
                "cognomeUtente",
                "Utente1234",
                4,
                false)));
    }


    private static Stream<Arguments> provideAccountInfo() throws NoSuchAlgorithmException {
        return Stream.of(
                Arguments.of("utente@gmail.com", "Utente1234"));
    }*/
}
