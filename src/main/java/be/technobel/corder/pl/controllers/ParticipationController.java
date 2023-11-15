package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Participation;
import be.technobel.corder.pl.models.forms.ParticipationForm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParticipationController {

    private final ParticipationService participationService;


    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping("/participation/create")
    public ResponseEntity<Participation> create (@RequestBody ParticipationForm form) {
       Participation participation = participationService.create(form);
        return ResponseEntity.ok(participation);
    }
    @PutMapping("/participation/update/{id}")
    public ResponseEntity<Participation> update(@PathVariable Long id, @RequestBody ParticipationForm participationForm) {
      Participation participation =  participationService.update(id, participationForm);
      return ResponseEntity.ok(participation);
    }
}
