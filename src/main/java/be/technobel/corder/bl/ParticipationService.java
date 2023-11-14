package be.technobel.corder.bl;

import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.pl.models.forms.ParticipationForm;

import java.util.List;

public interface ParticipationService {
    void create(ParticipationForm participation);
    List<Participation> findAll();
    Participation findById(Long id);
    void update(Long id, ParticipationForm participation);
    void delete(Long id);
}
