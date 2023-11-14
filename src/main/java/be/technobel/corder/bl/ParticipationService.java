package be.technobel.corder.bl;

import be.technobel.corder.dal.models.Participation;
import jakarta.servlet.http.Part;

import java.util.List;

public interface ParticipationService {
    void create(Participation participation);
    List<Participation> findAll();
    Participation findById(Long id);
    void update(Long id, Participation participation);
    void delete(Long id);
}
