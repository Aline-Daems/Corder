package be.technobel.corder.bl.impl;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.models.enums.Products;
import be.technobel.corder.dal.repositories.AddressRepository;
import be.technobel.corder.dal.repositories.ParticipationRepository;
import be.technobel.corder.pl.config.exceptions.DuplicateParticipationException;
import be.technobel.corder.pl.models.forms.ParticipationForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    private final AddressRepository addressRepository;
    private final EmailServiceImpl emailService;

    public ParticipationServiceImpl(ParticipationRepository participationRepository, AddressRepository addressRepository, EmailServiceImpl emailService) {
        this.participationRepository = participationRepository;
        this.addressRepository = addressRepository;
        this.emailService = emailService;
    }

    private void isUniqueParticipant(Participation participation) {
        String name = formatName(participation);
        String address = formatAddress(participation);

        List<String> names = findAll().stream()
                .map(this::formatName)
                .toList();

        List<String> addresses = findAll().stream()
                .map(this::formatAddress)
                .toList();

        if (names.contains(name) || addresses.contains(address)) {
            throw new DuplicateParticipationException("Ce participant a déjà joué !");
        }
    }

    private String formatName(Participation participation) {
        return (participation.getParticipantFirstName() + participation.getParticipantLastName()).trim().toLowerCase();
    }

    private String formatAddress(Participation participation) {
        Address Address = participation.getParticipantAddress();
        return (Address.getStreet() + Address.getCity() + Address.getPostCode()).trim().toLowerCase();
    }
    @Override
    public Participation create(ParticipationForm participation) {
      try{
          isUniqueParticipant(participation.toEntity());
          return participationRepository.save(participation.toEntity());

      }catch (DuplicateParticipationException e){
          throw new DuplicateParticipationException("Ce participant a déjà joué !");
      }
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

        entity.setStatus(participation.status());
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
    @Transactional
    public List<Participation> findValidated() {
        return participationRepository.findByValidated();
    }



    @Override
    @Transactional
    public List<Participation> findShipped() {
        return participationRepository.findByShipped();
    }


    @Override
    @Transactional
    public List<Participation> findPending() {
        return participationRepository.findByPending();
    }

    @Override
    @Transactional
    public List<Participation> findDenied(){
        return  participationRepository.findByDenied();
    }
    @Override
    public Long countInsecticide() {
       return participationRepository.findbyProductInsec();
    }

    @Override
    public Long countHerbicide() {
        return participationRepository.findbyProductHerbi();
    }

    @Override
    public Long countFongicide() {
        return participationRepository.findbyProductFong();
    }

    @Override
    public Long countParticipation() {
        return participationRepository.findNbrParticipation();
    }
}
