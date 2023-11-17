package be.technobel.corder.bl.impl;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.repositories.AddressRepository;
import be.technobel.corder.dal.repositories.ParticipationRepository;
import be.technobel.corder.pl.models.forms.ParticipationForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final AddressRepository addressRepository;
    private final EmailService emailService;

    public ParticipationServiceImpl(ParticipationRepository participationRepository, AddressRepository addressRepository, EmailService emailService) {
        this.participationRepository = participationRepository;
        this.addressRepository = addressRepository;
        this.emailService = emailService;
    }

    @Override
    public Participation create(ParticipationForm participation) {
       return participationRepository.save(participation.toEntity());
    }

    @Override
    public List<Participation> findAll() {
        return participationRepository.findAll();
    }

    @Override
    public Participation findById(Long id) {
        return participationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Participation update(Long id, ParticipationForm participation) {
        Participation entity = findById(id);
        entity.setParticipantFirstName(participation.firstName());
        entity.setParticipantLastName(participation.lastName());
        entity.setParticipantEmail(participation.email());
        Address address = new Address();
        address.setStreet(participation.street());
        address.setCity(participation.city());
        address.setPostCode(participation.postCode());
        addressRepository.save(address);
        entity.setParticipantAddress(address);
        entity.setPictureName(participation.pictureName());
        entity.setPictureType(participation.pictureType());
        entity.setBlob(participation.blob());

       return participationRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        participationRepository.deleteById(id);
    }

    @Override
    public List<Participation> findValidated() {
        return participationRepository.findByValidated(true);
    }

    @Override
    public List<Participation> findNonValidated() {
        return participationRepository.findByValidated(false);
    }

    @Override
    public List<Participation> findShipped() {
        return participationRepository.findByShipped(true);
    }

    @Override
    public List<Participation> findNonShipped() {
        return participationRepository.findByShipped(false);
    }

    @Override
    public boolean validate(Long id) {
        Participation entity = findById(id);
        entity.setValidated(true);
        participationRepository.save(entity);
        emailService.sendMail(findById(id).getParticipantEmail(), "Participation validée !", "Bravo, votre participation a été validée !");
        return true;
    }

    @Override
    public boolean ship(Long id) {
        Participation entity = findById(id);
        entity.setShipped(true);
        participationRepository.save(entity);
        return true;
    }
}
