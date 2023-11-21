package be.technobel.corder.pl.models.dtos;

import be.technobel.corder.dal.models.User;
import be.technobel.corder.dal.models.enums.Role;

public class UserDTO {
    Long id;
    String firstName;
    String lastName;
    String login;
    String password;
    Role role;
    String email;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String login, String password, Role role, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), user.getRole(), user.getEmail());
    }



}

