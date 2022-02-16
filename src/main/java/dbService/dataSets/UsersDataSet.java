package dbService.dataSets;


public class UsersDataSet {

    private final long id;
    private final String login;
    private final String email;
    private final String password;


    public UsersDataSet(long id, String login, String email, String password) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UsersDataSet(String login, String email, String password) {
        this.id = -1;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UsersDataSet{" +
                "id=" + id +
                ", name='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + email + '\'' +
                '}';
    }
}
