package be.technobel.corder.bl;

import be.technobel.corder.dal.models.Participation;
import jakarta.servlet.http.Part;

import java.util.List;

public interface ParticipationService {
    Participation create(Participation participation);
    List<Participation> findAll();
    Participation findById(Long id);
    Participation update(Long id, Participation participation);
    Participation delete(Long id);
}
