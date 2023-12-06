package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Participation;

import be.technobel.corder.pl.config.exceptions.DuplicateParticipationException;
import be.technobel.corder.pl.models.dtos.ParticipationDTO;
import be.technobel.corder.pl.models.dtos.ParticipationIdDTO;
import be.technobel.corder.pl.models.dtos.ParticipationNoBlobDTO;
import be.technobel.corder.pl.models.dtos.SatisfactionCommentDTO;
import be.technobel.corder.pl.models.forms.ParticipationForm;
import be.technobel.corder.pl.models.forms.SatisfactionForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participation")
public class ParticipationController {

    private final ParticipationService participationService;


    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create (@RequestBody ParticipationForm form) {
      try {
          Participation participation = participationService.create(form);
          return ResponseEntity.ok(ParticipationIdDTO.fromEntity(participation));
      }catch (DuplicateParticipationException e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
    }
    @PostMapping("/createSatisfaction")
    public ResponseEntity<?> createSatisfaction (@RequestBody SatisfactionForm form) {
        try {
            Participation participation = participationService.updateSatisfaction(form);
            return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
        }catch (DuplicateParticipationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ParticipationDTO> update(@PathVariable Long id, @RequestBody ParticipationForm participationForm) {
      Participation participation =  participationService.update(id, participationForm);
      return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findById/{id}")
    public ResponseEntity<ParticipationDTO> findById(@PathVariable Long id) {
        Participation participation = participationService.findById(id);
        if (participation != null) {
            return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<ParticipationDTO>> findAll() {
        List<Participation> participations = participationService.findAll();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allByPage")
    public ResponseEntity<Page<ParticipationNoBlobDTO>> findAllByPage(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(participationService.findAll(PageRequest.of(page, size, Sort.by("participationDate").descending())).map(ParticipationNoBlobDTO::fromEntity));
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allNoBlob")
    public ResponseEntity<List<ParticipationNoBlobDTO>> findAllNoBlob() {
        List<Participation> participations = participationService.findAll();
        List<ParticipationNoBlobDTO> dtos = participations.stream()
                .map(ParticipationNoBlobDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allValidated")
    public ResponseEntity<List<ParticipationDTO>> findValidated() {
        List<Participation> participations = participationService.findValidated();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allValidatedNoBlob")
    public ResponseEntity<List<ParticipationNoBlobDTO>> findValidatedNoBlob() {
        List<Participation> participations = participationService.findValidated();
        List<ParticipationNoBlobDTO> dtos = participations.stream()
                .map(ParticipationNoBlobDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allPending")
    public ResponseEntity<List<ParticipationDTO>> findNonValidated() {
        List<Participation> participations = participationService.findPending();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allPendingNoBlob")
    public ResponseEntity<List<ParticipationNoBlobDTO>> findNonValidatedNoBlob() {
        List<Participation> participations = participationService.findPending();
        List<ParticipationNoBlobDTO> dtos = participations.stream()
                .map(ParticipationNoBlobDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allShipped")
    public ResponseEntity<List<ParticipationDTO>> findShipped() {
        List<Participation> participations = participationService.findShipped();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allShippedNoBlob")
    public ResponseEntity<List<ParticipationNoBlobDTO>> findShippedNoBlob() {
        List<Participation> participations = participationService.findShipped();
        List<ParticipationNoBlobDTO> dtos = participations.stream()
                .map(ParticipationNoBlobDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allDenied")
    public ResponseEntity<List<ParticipationDTO>> findDenied() {
        List<Participation> participations = participationService.findDenied();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allDeniedNoBlob")
    public ResponseEntity<List<ParticipationNoBlobDTO>> findDeniedNoBlob() {
        List<Participation> participations = participationService.findDenied();
        List<ParticipationNoBlobDTO> dtos = participations.stream()
                .map(ParticipationNoBlobDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        participationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/validate/{id}")
    public ResponseEntity<Boolean> validate(@PathVariable Long id) {
        boolean result = participationService.validate(id);
        return ResponseEntity.ok(result);
    }
    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @PatchMapping("/ship/{id}")
    public ResponseEntity<Boolean> ship(@PathVariable Long id) {
        boolean result = participationService.ship(id);
        return ResponseEntity.ok(result);
    }

    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @PatchMapping("/denied/{id}")
    public ResponseEntity<Boolean> denied(@PathVariable Long id) {
        boolean result = participationService.denied(id);
        return ResponseEntity.ok(result);
    }

    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/herbicide")
    public ResponseEntity<Long> findHerbicide (){
      return  ResponseEntity.ok(participationService.countHerbicide());
    }

    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/insecticide")
    public ResponseEntity<Long> findInsecticide(){
        return  ResponseEntity.ok(participationService.countInsecticide());

    }

    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/fongicide")
    public ResponseEntity<Long> findFongicide (){
        return  ResponseEntity.ok(participationService.countFongicide());
    }

    @GetMapping("/findAllOtherProductType")
    public ResponseEntity<List<String>> findAllOtherProductType() {
        return ResponseEntity.ok(participationService.findAllOtherProductType());
    }

    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/nbrparticipations")
    public ResponseEntity<Long> nbrParticipation(){
        return ResponseEntity.ok(participationService.countParticipation());
    }

    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/getLastsValidated/{nbr}")
    public ResponseEntity<List<ParticipationDTO>> getLastsValidated(@PathVariable int nbr) {
        List<Participation> participations = participationService.getLastsValidated(nbr);
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    //@PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/getLastsNonValidated/{nbr}")
    public ResponseEntity<List<ParticipationDTO>> getLastsNonValidated(@PathVariable int nbr) {
        List<Participation> participations = participationService.getLastsNonValidated(nbr);
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("getComments")
    public ResponseEntity<List<SatisfactionCommentDTO>> getComments() {
        List<SatisfactionCommentDTO> satisfactionCommentDTOList = participationService.findAll(Sort.by(Sort.Direction.DESC,"participationDate"))
                .stream()
                .map(SatisfactionCommentDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(satisfactionCommentDTOList);
    }



}
