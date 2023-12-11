package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.models.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    @Query("SELECT p FROM Participation p WHERE p.status = :status")
    List<Participation> findByStatus(@Param("status") String status);
    @Query("SELECT COUNT(p) FROM Participation p WHERE p.productType = :productType")
    Long countByProduct(String productType);

    @Query("select distinct p.productType from Participation p where p.productType not in ('insecticide', 'herbicide', 'fongicide')")
    List<String> findAllOtherProductType();

    @Query("select count (p) from Participation p where p.productType not in ('insecticide', 'herbicide', 'fongicide')")
    Long countAllOtherProductType();

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

    int countParticipationsBySatisfactionCommentContainingIgnoreCase(String satisfactionComment);

    @Query("SELECT count(p) FROM Participation p WHERE p.satisfactionComment NOT LIKE %:comment1% AND p.satisfactionComment NOT LIKE %:comment2% AND p.satisfactionComment NOT LIKE %:comment3% AND p.satisfactionComment NOT LIKE %:comment4%")
    int countParticipationsBySatisfactionCommentsNotContaining(@Param("comment1") String comment1,
                                                               @Param("comment2") String comment2,
                                                               @Param("comment3") String comment3,
                                                               @Param("comment4") String comment4);

    @Query("SELECT p.satisfactionComment FROM Participation p WHERE p.satisfactionComment NOT LIKE %:comment1% AND p.satisfactionComment NOT LIKE %:comment2% AND p.satisfactionComment NOT LIKE %:comment3% AND p.satisfactionComment NOT LIKE %:comment4%")
    List<String> findAllParticipationsBySatisfactionCommentsNotContaining(@Param("comment1") String comment1,
                                                                          @Param("comment2") String comment2,
                                                                          @Param("comment3") String comment3,
                                                                          @Param("comment4") String comment4);
}
