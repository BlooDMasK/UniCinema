package UniCinemaTest.Signup.service;

import Signup.service.SignupServiceMethods;
import model.bean.Account;
import model.dao.account.AccountDAOMethods;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class SignupServiceMethodsTest {

    @Mock
    private AccountDAOMethods accountDAOMethods;

    private SignupServiceMethods signupServiceMethods;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);

        signupServiceMethods = new SignupServiceMethods();
        signupServiceMethods.setAccountDAO(accountDAOMethods);
    }

    @Test
    public void signup() throws SQLException {
        Account account = new Account();
        when(accountDAOMethods.insert(account)).thenReturn(true);
        assertTrue(signupServiceMethods.signup(account));
    }
}
