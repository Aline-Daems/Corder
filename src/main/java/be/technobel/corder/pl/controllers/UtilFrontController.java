package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.UtilFrontService;
import be.technobel.corder.dal.models.UtilFront;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UtilFrontController {

    private final UtilFrontService utilFrontService;

    public UtilFrontController(UtilFrontService utilFrontService) {
        this.utilFrontService = utilFrontService;
    }

    @PostMapping("/utilfront/create")
    public void create (@RequestBody UtilFront form) {

        utilFrontService.create(form);

    }


}
