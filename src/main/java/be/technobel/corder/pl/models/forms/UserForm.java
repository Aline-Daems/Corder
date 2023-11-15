package be.technobel.corder.pl.models.forms;

import be.technobel.corder.dal.models.User;

public record UserForm(
        String firstName,
        String lastName,
        String login,
        String password,
        String email
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
