package be.technobel.corder.bl.impl;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.models.enums.Status;
import be.technobel.corder.dal.repositories.AddressRepository;
import be.technobel.corder.dal.repositories.ParticipationRepository;
import be.technobel.corder.pl.config.exceptions.DuplicateParticipationException;
import be.technobel.corder.pl.models.forms.ParticipationForm;
import be.technobel.corder.pl.models.forms.SatisfactionForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        //TODO: vérifier le mail
        String address = formatAddress(participation);

        List<String> addresses = findAll().stream()
                .map(this::formatAddress)
                .toList();

        if (addresses.contains(address)) {
            throw new DuplicateParticipationException("Ce participant a déjà joué !");
        }
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
    public Page<Participation> findAll(Pageable pageable) {
        return participationRepository.findAll(pageable);
    }

    @Override
    public List<Participation> findAll(Sort sort) {
        return participationRepository.findAll(sort);
    }

    @Override
    public Participation findById(Long id) {
        return participationRepository.findById(id).orElseThrow((EntityNotFoundException::new));
    }

    @Override
    public Participation update(Long id, ParticipationForm participation) {
        //TODO: tout vérifier avant de odifier si on a le temps
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
    public Participation updateSatisfaction(SatisfactionForm satisfactionForm) {
        Participation entity = findById(satisfactionForm.id());
        entity.setSatisfaction(satisfactionForm.satisfaction());
        entity.setSatisfactionComment(satisfactionForm.satisfactionComment());
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
    public Boolean validate(Long id) {
        Participation entity = findById(id);
        entity.setStatus(Status.VALIDATED);
        entity.setValidatedDate(LocalDateTime.now());
        participationRepository.save(entity);
        return true;
    }

    @Override
    public Boolean ship(Long id) {
        Participation entity = findById(id);
        entity.setStatus(Status.SHIPPED);
        participationRepository.save(entity);
        return true;
    }

    @Override
    public Boolean denied(Long id) {
        Participation entity = findById(id);
        entity.setStatus(Status.DENIED);
        participationRepository.save(entity);
        return true;
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

    @Override
    @Transactional
    public List<Participation> getLastsValidated(int nbr) {
        return participationRepository.getLastValidated(nbr);
    }

    @Override
    @Transactional
    public List<Participation> getLastsNonValidated(int nbr) {
        return participationRepository.getLastNonValidated(nbr);
    }
}
