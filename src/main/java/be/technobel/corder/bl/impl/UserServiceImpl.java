package be.technobel.corder.bl.impl;

import be.technobel.corder.bl.UserCheckService;
import be.technobel.corder.bl.UserService;
import be.technobel.corder.dal.models.User;
import be.technobel.corder.dal.models.enums.Role;
import be.technobel.corder.dal.repositories.UserRepository;
import be.technobel.corder.pl.config.JWTProvider;
import be.technobel.corder.pl.config.exceptions.DuplicateParticipationException;
import be.technobel.corder.pl.models.dtos.AuthDTO;
import be.technobel.corder.pl.models.dtos.UserDTO;
import be.technobel.corder.pl.models.forms.LoginForm;
import be.technobel.corder.pl.models.forms.UserForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserCheckService userCheckService;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JWTProvider jwtProvider, PasswordEncoder passwordEncoder, UserCheckService userCheckService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.userCheckService = userCheckService;
    }


    @Override
    public User create(UserForm user) {

        try {
            if (user == null)
                throw new IllegalArgumentException("Ne peut pas être null");

            User entity = new User();

            entity.setFirstName(user.firstName());
            entity.setLastName(user.lastName());
            entity.setLogin(user.login());
            entity.setPassword(passwordEncoder.encode(user.password()));
            entity.setEmail(user.email());
            entity.setRole(Role.ADMIN);
            return userRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateParticipationException("Ce login ou cet email est déjà enregistré");
        }


    }

    @Override
    public User update(Long id, User user) {
        User user1 = findById(id);
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setLogin(user.getLogin());
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setRole(user.getRole());
        user1.setEmail(user.getEmail());
        return userRepository.save(user1);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
        return user;
    }

    @Override
    public AuthDTO login(LoginForm form) {

        if (form == null) {
            throw new IllegalArgumentException("LoginForm cannot be null");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.getLogin(), form.getPassword()));

        User user = userRepository.findByLogin(form.getLogin()).orElseThrow(() -> new NotFoundException("User not found"));

        String token = jwtProvider.generateToken(user.getUsername(), user.getRole());

        return new AuthDTO(user.getLogin(), token, user.getRole());
    }

    @Override
    public int changeUserPassword(UserDTO dto, String newPassword, String login) {


        String userPassword = dto.getPassword();

        boolean checkPassword = userCheckService.isTruePassword(userRepository.findByLogin(login).orElseThrow(EntityNotFoundException::new).getId(), userPassword);

        if (checkPassword) {

            userRepository.changeUserPassword(passwordEncoder.encode(newPassword), login);

            return 1;
        }

        return 0;
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(EntityNotFoundException::new);
    }


}
