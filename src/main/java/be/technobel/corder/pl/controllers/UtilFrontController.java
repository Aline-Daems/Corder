package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.UtilFrontService;
import be.technobel.corder.dal.models.UtilFront;
import be.technobel.corder.pl.models.forms.UtilFrontForm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilfront")
public class UtilFrontController {

    private final UtilFrontService utilFrontService;

    public UtilFrontController(UtilFrontService utilFrontService) {
        this.utilFrontService = utilFrontService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<UtilFront> create (@RequestBody UtilFrontForm form) {
        return ResponseEntity.ok(utilFrontService.create(form.toEntity()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UtilFront>> getAll () {
        List<UtilFront> utilFronts = utilFrontService.findAll();
        return ResponseEntity.ok(utilFronts);
    }
}
