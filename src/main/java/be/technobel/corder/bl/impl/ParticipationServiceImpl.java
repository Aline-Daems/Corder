package be.technobel.corder.bl.impl;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Address;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.dal.models.enums.Status;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    private final ParticipationRepository participationRepository;
    //TODO: remettre en place les mails ??
    private final EmailServiceImpl emailService;

    public ParticipationServiceImpl(ParticipationRepository participationRepository, EmailServiceImpl emailService) {
        this.participationRepository = participationRepository;
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
        //TODO: tout vérifier avant de modifier si on a le temps
        Participation entity = findById(id);
        entity.setParticipantFirstName(participation.firstName());
        entity.setParticipantLastName(participation.lastName());
        entity.setParticipantEmail(participation.email());
        Address address = new Address();
        address.setStreet(participation.street());
        address.setCity(participation.city());
        address.setPostCode(participation.postCode());
        entity.setParticipantAddress(address);

        entity.setStatus(participation.status());
        entity.setParticipantAddress(address);

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
    public List<String> findAllOtherProductType() {
        return participationRepository.findAllOtherProductType();
    }

    @Override
    @Transactional
    public List<Participation> getLasts3Validated() {
        return participationRepository.getLast3Validated();
    }

    @Override
    @Transactional
    public List<Participation> getLasts3NonValidated() {
        return participationRepository.getLast3NonValidated();
    }

    @Override
    public Participation addPhoto(MultipartFile photo, Long id) {
        try {
            Participation entity = findById(id);
            entity.setBlob(photo.getBytes());
            entity.setPictureName(photo.getOriginalFilename());
            entity.setPictureType(photo.getContentType());
            return participationRepository.save(entity);
        } catch (IOException e) {
            throw new RuntimeException("Failed to add photo to participation with id: " + id, e);
        }
    }

    @Override
    public Page<Participation> findAllByStatus(Status status, Pageable pageable) {
        return participationRepository.findAllByStatus(status, pageable);
    }

    @Override
    public Long countBySatisfaction(int satisfaction) {
        return participationRepository.countBySatisfaction(satisfaction);
    }

    @Override
    public Map<String, Integer> countParticipationByProvince() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Brabant wallon", participationRepository.countParticipationByProvince(1300, 1499));
        map.put("Liège", participationRepository.countParticipationByProvince(4000, 4999));
        map.put("Namur", participationRepository.countParticipationByProvince(5000, 5680));
        map.put("Hainaut", participationRepository.countParticipationByProvince(6000, 6599) + participationRepository.countParticipationByProvince(7000, 7999));
        map.put("Luxembourg", participationRepository.countParticipationByProvince(6600, 6999));
        return map;
    }

    @Override
    public Map<String, Integer> countParticipationsFor7Days(LocalDate start) {
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i <= 6; i++) {
            LocalDate currentDate = start.minusDays(i);
            Integer count = participationRepository.countParticipationsBetweenDates(currentDate, currentDate);
            map.put(currentDate.getDayOfWeek().toString(), count);
        }
        return map;
    }

    @Override
    public Map<String, Integer> countParticipationsFor5LastMonths() {
        LocalDate start = LocalDate.now();
        Map<String, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < 5; i++) {
            LocalDate currentDate = start.minusMonths(i);
            YearMonth currentMonth = YearMonth.from(currentDate);
            Integer count = participationRepository.countParticipationsBetweenDates(
                    currentDate.withDayOfMonth(1),
                    currentDate.withDayOfMonth(currentDate.lengthOfMonth()));
            map.put(currentMonth.getMonth().toString(), count);
        }
        return map;
    }
}
