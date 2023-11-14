package be.technobel.corder.bl;

import be.technobel.corder.dal.models.User;

import java.util.List;

public interface UserService {
    User create(User user);
    User update(Long id, User user);
    User findById(Long id);
    List<User> findAll();
    User delete(Long id);
}
