package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.UtilFrontService;
import be.technobel.corder.dal.models.UtilFront;
import be.technobel.corder.pl.models.forms.UtilFrontForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilfront")
public class UtilFrontController {

    private final UtilFrontService utilFrontService;

    public UtilFrontController(UtilFrontService utilFrontService) {
        this.utilFrontService = utilFrontService;
    }

    @PostMapping("/create")
    public ResponseEntity<UtilFront> create (@RequestBody UtilFrontForm form) {
        return ResponseEntity.ok(utilFrontService.create(form.toEntity()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UtilFront>> getAll () {
        List<UtilFront> utilFronts = utilFrontService.findAll();
        return ResponseEntity.ok(utilFronts);
    }
}
