package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.ParticipationService;
import be.technobel.corder.dal.models.Participation;

import be.technobel.corder.dal.models.enums.SortParticipation;
import be.technobel.corder.dal.models.enums.Status;
import be.technobel.corder.pl.config.exceptions.DuplicateParticipationException;
import be.technobel.corder.pl.models.dtos.ParticipationDTO;
import be.technobel.corder.pl.models.dtos.ParticipationIdDTO;
import be.technobel.corder.pl.models.dtos.ParticipationNoBlobDTO;
import be.technobel.corder.pl.models.dtos.SatisfactionCommentDTO;
import be.technobel.corder.pl.models.forms.ParticipationForm;
import be.technobel.corder.pl.models.forms.SatisfactionForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
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

    @PostMapping("/addPhoto")
    public ResponseEntity<ParticipationDTO> addPhoto(@RequestParam("id") Long id, @RequestParam("file") MultipartFile photo) {
        try {
            Participation participation = participationService.addPhoto(photo, id);
            return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/getPhoto")
    public ResponseEntity<?> getPhoto(@RequestParam("id") Long id) {
        Participation participation = participationService.findById(id);
        if (participation != null && participation.getBlob() != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(participation.getPictureType()));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(participation.getPictureName()).build());
            return new ResponseEntity<>(participation.getBlob(), headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
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
    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ParticipationDTO> update(@PathVariable Long id, @RequestBody ParticipationForm participationForm) {
      Participation participation =  participationService.update(id, participationForm);
      return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
    }
    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/findById/{id}")
    public ResponseEntity<ParticipationDTO> findById(@PathVariable Long id) {
        Participation participation = participationService.findById(id);
        if (participation != null) {
            return ResponseEntity.ok(ParticipationDTO.fromEntity(participation));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/all")
    public ResponseEntity<List<ParticipationDTO>> findAll() {
        List<Participation> participations = participationService.findAll();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/Page")
    public ResponseEntity<Page<ParticipationNoBlobDTO>> findAllByPage(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "20") int size) {
        return ResponseEntity.ok(participationService.findAll(PageRequest.of(page, size, Sort.by("participationDate").descending())).map(ParticipationNoBlobDTO::fromEntity));
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/PageByStatus")
    public ResponseEntity<Page<ParticipationNoBlobDTO>> findAllPageByStatus(@RequestParam("status") String status, @RequestParam("sort") String sort, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "20") int size) {
        if (SortParticipation.valueOf(sort.toUpperCase()).equals(SortParticipation.DATEDESC)) {
            return ResponseEntity.ok(participationService.findAllByStatus(Status.valueOf(status.toUpperCase()) ,PageRequest.of(page, size, Sort.by("participationDate").descending())).map(ParticipationNoBlobDTO::fromEntity));
        } else if (SortParticipation.valueOf(sort.toUpperCase()).equals(SortParticipation.DATEASC)) {
            return ResponseEntity.ok(participationService.findAllByStatus(Status.valueOf(status.toUpperCase()) ,PageRequest.of(page, size, Sort.by("participationDate").ascending())).map(ParticipationNoBlobDTO::fromEntity));
        } else {
            return ResponseEntity.ok(participationService.findAllByStatus(Status.valueOf(status.toUpperCase()) ,PageRequest.of(page, size, Sort.by("participantLastName").ascending())).map(ParticipationNoBlobDTO::fromEntity));
        }
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
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
    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/allPending")
    public ResponseEntity<List<ParticipationDTO>> findNonValidated() {
        List<Participation> participations = participationService.findPending();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/denied/{id}")
    public ResponseEntity<Boolean> denied(@PathVariable Long id) {
        boolean result = participationService.denied(id);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/countHerbicide")
    public ResponseEntity<Long> findHerbicide (){
      return  ResponseEntity.ok(participationService.countHerbicide());
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/countInsecticide")
    public ResponseEntity<Long> findInsecticide(){
        return  ResponseEntity.ok(participationService.countInsecticide());

    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/countFongicide")
    public ResponseEntity<Long> findFongicide (){
        return  ResponseEntity.ok(participationService.countFongicide());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/findAllOtherProductType")
    public ResponseEntity<List<String>> findAllOtherProductType() {
        return ResponseEntity.ok(participationService.findAllOtherProductType());
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/nbrparticipations")
    public ResponseEntity<Long> nbrParticipation(){
        return ResponseEntity.ok(participationService.countParticipation());
    }

    @PreAuthorize("hasRole('ADMIN') || hasRole('LOGISTIC')")
    @GetMapping("/getLasts3Validated")
    public ResponseEntity<List<ParticipationDTO>> getLasts3Validated() {
        List<Participation> participations = participationService.getLasts3Validated();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getLasts3NonValidated")
    public ResponseEntity<List<ParticipationDTO>> getLasts3NonValidated() {
        List<Participation> participations = participationService.getLasts3NonValidated();
        List<ParticipationDTO> dtos = participations.stream()
                .map(ParticipationDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getComments")
    public ResponseEntity<List<SatisfactionCommentDTO>> getComments() {
        List<SatisfactionCommentDTO> satisfactionCommentDTOList = participationService.findAll(Sort.by(Sort.Direction.DESC,"participationDate"))
                .stream()
                .map(SatisfactionCommentDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(satisfactionCommentDTOList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/CountNote")
    public ResponseEntity<Long> countNote(@RequestParam("note") int note) {
        return ResponseEntity.ok(participationService.countBySatisfaction(note));
    }
}
