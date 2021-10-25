package Model.account;

public class AccountSession {
    private final String firstName, lastName;
    private final String email;
    private final boolean isAdmin;

    public AccountSession(Account account) {
        this.firstName = account.getFirstname();
        this.lastName = account.getLastname();
        this.email = account.getEmail();
        this.isAdmin = account.isAdministrator();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
