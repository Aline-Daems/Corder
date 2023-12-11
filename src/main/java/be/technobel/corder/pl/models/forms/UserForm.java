package be.technobel.corder.pl.models.forms;

import be.technobel.corder.dal.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserForm(
        @NotBlank(message = "First name cannot be blank") String firstName,

        @NotBlank(message = "Last name cannot be blank") String lastName,

        @NotBlank(message = "Login cannot be blank") String login,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long") String password,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid") String email
) {
    public User toEntity() {
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public String login() {
        return login;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String email() {
        return email;
    }
}
