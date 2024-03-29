package be.technobel.corder.pl.controllers;

import be.technobel.corder.bl.UserService;
import be.technobel.corder.dal.models.User;
import be.technobel.corder.pl.config.exceptions.DuplicateParticipationException;
import be.technobel.corder.pl.models.dtos.AuthDTO;
import be.technobel.corder.pl.models.dtos.UserDTO;
import be.technobel.corder.pl.models.forms.ChangePasswordForm;
import be.technobel.corder.pl.models.forms.LoginForm;
import be.technobel.corder.pl.models.forms.UserForm;
import jakarta.validation.Valid;
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

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid UserForm form) {
        try {
            userService.create(form);
            return ResponseEntity.ok("User created");
        } catch (DuplicateParticipationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginForm form) {
        return ResponseEntity.ok(userService.login(form));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/changePassword/{login}")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordForm form, @PathVariable String login) {

        User user = userService.findByLogin(login);

        if (user != null) {
            UserDTO dto = new UserDTO();


            dto.setPassword(form.getOldPassword());

            int check = userService.changeUserPassword(dto, form.getNewPassword(), login);

            if (check == 1) {

                return ResponseEntity.status(HttpStatus.OK).body("Mot de passe modifié");

            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERREUR");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERREUR");
    }
}
