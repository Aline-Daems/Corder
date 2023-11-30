package be.technobel.corder.bl;

import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.pl.models.forms.ParticipationForm;

import java.util.List;

public interface ParticipationService {
    Participation create(ParticipationForm participation);
    List<Participation> findAll();
    Participation findById(Long id);
    Participation update(Long id, ParticipationForm participation);
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

    List<Participation> getLastsValidated(int nbr);
    List<Participation> getLastsNonValidated(int nbr);
}
