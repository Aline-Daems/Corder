package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    @Query("SELECT p FROM Participation  p WHERE  p.status = 'valid'")
    List<Participation> findByValidated();

    @Query("SELECT p FROM Participation  p WHERE p.status = 'shipped'")
    List<Participation> findByShipped();

    @Query("SELECT p FROM Participation  p WHERE p.status = 'pending'")
    List<Participation> findByPending();
    @Query("SELECT p FROM Participation  p WHERE p.status = 'denied'")
    List<Participation> findByDenied();

    @Query("SELECT COUNT(p) FROM Participation  p WHERE p.productType = 'Insecticide'")
    Long findbyProductInsec();

    @Query("SELECT COUNT(p) FROM Participation  p WHERE p.productType = 'Herbicide'")
    Long findbyProductHerbi();

    @Query("SELECT COUNT(p) FROM Participation  p WHERE p.productType = 'Fongicide'")
    Long findbyProductFong();

    @Query("SELECT COUNT (p) from  Participation p ")
    Long findNbrParticipation();


}
