package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Participation;

import be.technobel.corder.pl.config.exceptions.DuplicateParticipationException;
import be.technobel.corder.pl.models.dtos.ParticipationDTO;
import be.technobel.corder.pl.models.dtos.ParticipationNoBlobDTO;
import be.technobel.corder.pl.models.forms.ParticipationForm;
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
          return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
      }catch (DuplicateParticipationException e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
      }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ParticipationDTO> update(@PathVariable Long id, @RequestBody ParticipationForm participationForm) {
      Participation participation =  participationService.update(id, participationForm);
      return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findById/{id}")
    public ResponseEntity<ParticipationDTO> findById(@PathVariable Long id) {
        Participation participation = participationService.findById(id);
        if (participation != null) {
            return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<ParticipationDTO>> findAll() {
        List<Participation> participations = participationService.findAll();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allNoBlob")
    public ResponseEntity<List<ParticipationNoBlobDTO>> findAllNoBlob() {
        List<Participation> participations = participationService.findAll();
        List<ParticipationNoBlobDTO> dtos = participations.stream()
                .map(ParticipationNoBlobDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allValidated")
    public ResponseEntity<List<ParticipationDTO>> findValidated() {
        List<Participation> participations = participationService.findValidated();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allValidatedNoBlob")
    public ResponseEntity<List<ParticipationNoBlobDTO>> findValidatedNoBlob() {
        List<Participation> participations = participationService.findValidated();
        List<ParticipationNoBlobDTO> dtos = participations.stream()
                .map(ParticipationNoBlobDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allPending")
    public ResponseEntity<List<ParticipationDTO>> findNonValidated() {
        List<Participation> participations = participationService.findPending();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allPendingNoBlob")
    public ResponseEntity<List<ParticipationNoBlobDTO>> findNonValidatedNoBlob() {
        List<Participation> participations = participationService.findPending();
        List<ParticipationNoBlobDTO> dtos = participations.stream()
                .map(ParticipationNoBlobDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allShipped")
    public ResponseEntity<List<ParticipationDTO>> findShipped() {
        List<Participation> participations = participationService.findShipped();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allShippedNoBlob")
    public ResponseEntity<List<ParticipationNoBlobDTO>> findShippedNoBlob() {
        List<Participation> participations = participationService.findShipped();
        List<ParticipationNoBlobDTO> dtos = participations.stream()
                .map(ParticipationNoBlobDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allDenied")
    public ResponseEntity<List<ParticipationDTO>> findDenied() {
        List<Participation> participations = participationService.findDenied();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allDeniedNoBlob")
    public ResponseEntity<List<ParticipationNoBlobDTO>> findDeniedNoBlob() {
        List<Participation> participations = participationService.findDenied();
        List<ParticipationNoBlobDTO> dtos = participations.stream()
                .map(ParticipationNoBlobDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        participationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/validate/{id}")
    public ResponseEntity<Boolean> validate(@PathVariable Long id) {
        boolean result = participationService.validate(id);
        return ResponseEntity.ok(result);
    }
    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @PatchMapping("/ship/{id}")
    public ResponseEntity<Boolean> ship(@PathVariable Long id) {
        boolean result = participationService.ship(id);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @PatchMapping("/denied/{id}")
    public ResponseEntity<Boolean> denied(@PathVariable Long id) {
        boolean result = participationService.denied(id);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/herbicide")
    public Long findHerbicide (){
      return  participationService.countHerbicide();

    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/insecticide")
    public Long findInsecticide(){
        return  participationService.countInsecticide();

    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/fongicide")
    public Long findFongicide (){
        return  participationService.countFongicide();

    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/nbrparticipations")
    public Long nbrParticipation(){
        return  participationService.countParticipation();
    }
}
