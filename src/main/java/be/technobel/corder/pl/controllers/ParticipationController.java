package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.pl.models.forms.ParticipationForm;

import org.springframework.web.bind.annotation.*;

@RestController
public class ParticipationController {

    private final ParticipationService participationService;


    public ParticipationController(ParticipationService participationService) {
        this.participationService = participationService;
    }

    @PostMapping("/participation/create")
    public void create (@RequestBody ParticipationForm form) {

        participationService.create(form);

    }
    @PutMapping("/participation/update/{id}")
    public void update(@PathVariable Long id, @RequestBody ParticipationForm participationForm) {
        participationService.update(id, participationForm);
    }
}
