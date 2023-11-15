package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Participation;

import be.technobel.corder.pl.models.dtos.ParticipationDTO;
import be.technobel.corder.pl.models.forms.ParticipationForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
