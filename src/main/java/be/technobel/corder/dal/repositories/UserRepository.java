package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
    Optional<User> findById(Long id);

    void deleteById(Long id);
}
