package be.technobel.corder.pl.models.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginForm {
    @NotNull(message = "Login cannot be null")
    @Size(min = 3, max = 30, message = "Login must be between 3 and 30 characters")
    private final String login;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
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
