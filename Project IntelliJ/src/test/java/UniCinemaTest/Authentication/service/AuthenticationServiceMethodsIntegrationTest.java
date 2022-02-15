package UniCinemaTest.Authentication.service;

import Authentication.service.AuthenticationService;
import Authentication.service.AuthenticationServiceMethods;
import model.bean.Account;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class AuthenticationServiceMethodsIntegrationTest {

    private AuthenticationService authenticationService;

    @Before
    public void setUp() throws SQLException {
        authenticationService = new AuthenticationServiceMethods();
    }

    @Test
    public void fetchAccount() throws SQLException {
        Account account = authenticationService.fetch("gleone2000@gmail.com");
        assertNotNull(account);
    }

}
