package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
