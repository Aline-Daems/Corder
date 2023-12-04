package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.UserService;

import be.technobel.corder.dal.models.User;
import be.technobel.corder.pl.config.exceptions.DuplicateParticipationException;
import be.technobel.corder.pl.models.dtos.AuthDTO;
import be.technobel.corder.pl.models.dtos.UserDTO;
import be.technobel.corder.pl.models.forms.ChangePasswordForm;
import be.technobel.corder.pl.models.forms.LoginForm;
import be.technobel.corder.pl.models.forms.UserForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
  //  @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserForm form) {
        try {
            User user = userService.create(form);
            return  ResponseEntity.ok(UserDTO.fromEntity(user));
        } catch (DuplicateParticipationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    //@PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public AuthDTO login(@RequestBody LoginForm form) {
        return userService.login(form);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/changePassword/{id}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordForm form, @PathVariable Long id) throws Exception {

        User user = userService.findById(id);

        if (user != null) {
            UserDTO dto = new UserDTO();


            dto.setPassword(form.getOldPassword());

            int check = userService.changeUserPassword(dto, form.getNewPassword(), id);

            if (check == 1) {

                return ResponseEntity.status(HttpStatus.OK).body("Mot de passe modifié");

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERREUR");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERREUR");
    }
}
