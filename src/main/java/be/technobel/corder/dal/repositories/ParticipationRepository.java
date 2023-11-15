package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    List<Participation> findByValidated(boolean validated);
    List<Participation> findByShipped(boolean shipped);
}
