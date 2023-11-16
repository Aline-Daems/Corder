package be.technobel.corder.pl.models.dtos;

import be.technobel.corder.dal.models.enums.Role;

public class AuthDTO {

    private String login;
    private String token;
    private Role role;

    public AuthDTO(String login, String token, Role role) {
        this.login = login;
        this.token = token;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
