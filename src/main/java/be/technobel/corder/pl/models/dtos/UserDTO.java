package be.technobel.corder.pl.models.dtos;

import be.technobel.corder.dal.models.User;
import be.technobel.corder.dal.models.enums.Role;

public record UserDTO(
        Long id,
        String firstName,
        String lastName,
        String login,
        String password,
        Role role,
        String email
) {
    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getLogin(), user.getPassword(), user.getRole(), user.getEmail());
    }
}
