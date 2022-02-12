package UniCinemaTest.Authentication.service;

import Authentication.service.AuthenticationServiceMethods;
import model.bean.Account;
import model.dao.AccountDAO;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class AuthenticationServiceMethodsTest {


    @Mock private AccountDAO accountDAO;

    private AuthenticationServiceMethods authenticationServiceMethods;

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
    }
}
