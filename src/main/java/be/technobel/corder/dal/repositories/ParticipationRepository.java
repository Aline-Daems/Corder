package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
}
