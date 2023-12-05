package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.Participation;
import jakarta.mail.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query("select p from Participation p where status = 'VALIDATED' order by validatedDate desc limit :nbr")
    List<Participation> getLastValidated(@Param("nbr") int nbr);
    @Query("select p from Participation p where status = 'PENDING' order by participationDate desc limit :nbr")
    List<Participation> getLastNonValidated(@Param("nbr") int nbr);

}
