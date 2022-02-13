package accounts;

/**
 * A class for storing user information.
 */
public class UserProfile {
    private final String login;
    private final String pass;
    private final String email;

    /**
     * Constructor.
     * @param login user login
     * @param pass user password
     * @param email user e-mail
     */
    public UserProfile(String login, String pass, String email) {
        this.login = login;
        this.pass = pass;
        this.email = email;
    }

    /**
     * Constructor.
     * @param login user login
     */
    public UserProfile(String login) {
        this.login = login;
        this.pass = login;
        this.email = login;
    }

    /**
     * Returns the user's login.
     * @return user login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Returns the user's password.
     * @return user password
     */
    public String getPass() {
        return pass;
    }

    /**
     * Returns the user's e-mail.
     * @return user e-mail
     */
    public String getEmail() {
        return email;
    }
}
