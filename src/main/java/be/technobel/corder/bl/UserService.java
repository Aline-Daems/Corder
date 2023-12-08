package be.technobel.corder.bl;

import be.technobel.corder.dal.models.User;
import be.technobel.corder.pl.models.dtos.AuthDTO;
import be.technobel.corder.pl.models.dtos.UserDTO;
import be.technobel.corder.pl.models.forms.LoginForm;
import be.technobel.corder.pl.models.forms.UserForm;


import java.util.List;

public interface UserService {
    User create(UserForm user);
    User update(Long id, User user);
    User findById(Long id);
    List<User> findAll();
    User delete(Long id);
    AuthDTO login (LoginForm form);

    int changeUserPassword(UserDTO dto, String newPassword, String login);

    User findByLogin(String login);
}
