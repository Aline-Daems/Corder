package be.technobel.corder.bl.impl;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.repositories.ParticipationRepository;
import be.technobel.corder.pl.models.forms.ParticipationForm;
import jakarta.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;


    public ParticipationServiceImpl(ParticipationRepository participationRepository) {
        this.participationRepository = participationRepository;
    }


    @Override
    public void create(ParticipationForm participation) {
        Participation entity = new Participation();
        entity.setParticipationDate(participation.getParticipationDate());
        entity.setParticipantFirstName(participation.getParticipantFirstName());
        entity.setParticipantLastName(participation.getParticipantLastName());
        entity.setParticipantEmail(participation.getParticipantEmail());
        entity.setShipped(participation.isShipped());
        entity.setValidated(participation.isValidated());
        entity.setPictureName(participation.getPictureName());
        entity.setPictureType(participation.getPictureType());

        participationRepository.save(entity);

    }

    @Override
    public List<Participation> findAll() {
        List<Participation> participations = participationRepository.findAll();
        return  participations;
    }

    @Override
    public Participation findById(Long id) {
        return participationRepository.findById(id).orElseThrow(()-> new NotFoundException("Participation non trouvée"));
    }

    @Override
    public void update(Long id, ParticipationForm participation) {
        Participation entity = participationRepository.findById(id).orElseThrow(()-> new NotFoundException("Participation non trouvée"));
        entity.setParticipationDate(participation.getParticipationDate());
        entity.setParticipantFirstName(participation.getParticipantFirstName());
        entity.setParticipantLastName(participation.getParticipantLastName());
        entity.setParticipantEmail(participation.getParticipantEmail());

        participationRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        participationRepository.deleteById(id);
    }
}
