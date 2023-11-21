package be.technobel.corder.dal.repositories;

import be.technobel.corder.dal.models.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation,Long> {
    List<Participation> findByValidated(boolean validated);
    List<Participation> findByShipped(boolean shipped);
    @Query("SELECT COUNT(p) FROM Participation  p WHERE p.productType = 'Insecticide'")
    Long findbyProductInsec();

    @Query("SELECT COUNT(p) FROM Participation  p WHERE p.productType = 'Herbicide'")
    Long findbyProductHerbi();


    @Query("SELECT COUNT(p) FROM Participation  p WHERE p.productType = 'Fongicide'")
    Long findbyProductFong();




}
