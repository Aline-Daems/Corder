package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
    Optional<User> findById(Long id);

    void deleteById(Long id);
    @Transactional
    @Modifying
    @Query("UPDATE User SET password = :password")
    void changeUserPassword( @Param("password") String password);
}
