package be.technobel.corder.bl.impl;

import be.technobel.corder.bl.UserCheckService;
import be.technobel.corder.dal.models.User;
import be.technobel.corder.dal.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCheckServiceImpl implements UserCheckService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCheckServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean isTruePassword(Long id, String password) {

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("id non trouvée"));

        if (user != null) {
            boolean check = passwordEncoder.matches(password, user.getPassword());

            return check;
        } else {
            return false;
        }


    }
}
