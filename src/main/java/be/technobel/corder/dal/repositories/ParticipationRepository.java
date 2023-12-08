package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.models.enums.Status;
import jakarta.mail.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    @Query("SELECT p FROM Participation  p WHERE  p.status = 'VALIDATED'")
    List<Participation> findByValidated();
    @Query("SELECT p FROM Participation  p WHERE p.status = 'SHIPPED'")
    List<Participation> findByShipped();
    @Query("SELECT p FROM Participation  p WHERE p.status = 'PENDING'")
    List<Participation> findByPending();
    @Query("SELECT p FROM Participation  p WHERE p.status = 'DENIED'")
    List<Participation> findByDenied();

    //TODO: arranger un peu ça et peut etre en haut aussi
    @Query("SELECT COUNT(p) FROM Participation  p WHERE p.productType = 'insecticide'")
    Long findbyProductInsec();

    @Query("SELECT COUNT(p) FROM Participation  p WHERE p.productType = 'herbicide'")
    Long findbyProductHerbi();

    @Query("SELECT COUNT(p) FROM Participation  p WHERE p.productType = 'fongicide'")
    Long findbyProductFong();

    @Query("select distinct p.productType from Participation p where p.productType not in ('insecticide', 'herbicide', 'fongicide')")
    List<String> findAllOtherProductType();

    @Query("SELECT COUNT (p) from  Participation p ")
    Long findNbrParticipation();
    @Query("select p from Participation p where p.status = 'VALIDATED' order by p.validatedDate desc limit 3")
    List<Participation> getLast3Validated();
    @Query("select p from Participation p where p.status = 'PENDING' order by p.participationDate desc limit 3")
    List<Participation> getLast3NonValidated();

    Page<Participation> findAllByStatus(Status status, Pageable pageable);

    Long countBySatisfaction(int satisfaction);

    @Query("select count (p) from Participation p where p.participantAddress.postCode between :min and :max")
    int countParticipationByProvince(int min, int max);

    @Query("select count (p) from Participation p where p.participationDate between :start and :end")
    int countParticipationsBetweenDates(LocalDate start, LocalDate end);
}
