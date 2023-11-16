package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Participation;

import be.technobel.corder.pl.models.dtos.ParticipationDTO;
import be.technobel.corder.pl.models.forms.ParticipationForm;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/participation")
public class ParticipationController {

    private final ParticipationService participationService;


    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping("/create")
    public ResponseEntity<ParticipationDTO> create (@RequestBody ParticipationForm form) {
       Participation participation = participationService.create(form);
        return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ParticipationDTO> update(@PathVariable Long id, @RequestBody ParticipationForm participationForm) {
      Participation participation =  participationService.update(id, participationForm);
      return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
    }
    @GetMapping("/findById/{id}")
    public ResponseEntity<ParticipationDTO> findById(@PathVariable Long id) {
        Participation participation = participationService.findById(id);
        if (participation != null) {
            return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<ParticipationDTO>> findAll() {
        List<Participation> participations = participationService.findAll();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/allValidated")
    public ResponseEntity<List<ParticipationDTO>> findValidated() {
        List<Participation> participations = participationService.findValidated();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/allNonValidated")
    public ResponseEntity<List<ParticipationDTO>> findNonValidated() {
        List<Participation> participations = participationService.findNonValidated();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/allShipped")
    public ResponseEntity<List<ParticipationDTO>> findShipped() {
        List<Participation> participations = participationService.findShipped();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/allNonShipped")
    public ResponseEntity<List<ParticipationDTO>> findNonShipped() {
        List<Participation> participations = participationService.findNonShipped();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        participationService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/validate/{id}")
    public ResponseEntity<Boolean> validate(@PathVariable Long id) {
        boolean result = participationService.validate(id);
        return ResponseEntity.ok(result);
    }
    @PatchMapping("/ship/{id}")
    public ResponseEntity<Boolean> ship(@PathVariable Long id) {
        boolean result = participationService.ship(id);
        return ResponseEntity.ok(result);
    }


}
