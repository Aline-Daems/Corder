package be.technobel.corder.pl.controller;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.pl.models.ParticipationForm;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
