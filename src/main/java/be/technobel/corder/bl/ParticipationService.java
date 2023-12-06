package be.technobel.corder.bl;

import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.pl.models.forms.ParticipationForm;
import be.technobel.corder.pl.models.forms.SatisfactionForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ParticipationService {
    Participation create(ParticipationForm participation);
    List<Participation> findAll();
    Page<Participation> findAll(Pageable pageable);
    List<Participation> findAll(Sort sort);
    Participation findById(Long id);
    Participation update(Long id, ParticipationForm participation);
    Participation updateSatisfaction(SatisfactionForm satisfactionForm);
    void delete(Long id);
    List<Participation> findValidated();

    List<Participation> findShipped();
    Boolean validate(Long id);
    Boolean ship(Long id);
    Boolean denied(Long id);

    List<Participation> findDenied();

    List<Participation> findPending();

    Long countInsecticide();

    Long countHerbicide();

    Long countFongicide();

    Long countParticipation();

    List<String> findAllOtherProductType();

    List<Participation> getLastsValidated(int nbr);
    List<Participation> getLastsNonValidated(int nbr);

    Participation addPhoto(MultipartFile photo, Long id);
}
