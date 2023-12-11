package be.technobel.corder.pl.models.forms;

public class LoginForm {

    private final String login;
    private final String password;

    public LoginForm(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }


}
