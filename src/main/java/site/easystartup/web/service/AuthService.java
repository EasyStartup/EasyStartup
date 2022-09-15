package site.easystartup.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import site.easystartup.web.domain.exception.UserExistException;
import site.easystartup.web.domain.model.Role;
import site.easystartup.web.domain.model.User;
import site.easystartup.web.domain.request.SignupRequest;
import site.easystartup.web.repo.UserRepo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User createUser(SignupRequest signupRequest) {
        boolean exists = false;
        try {
            if (userRepo.findUserByUsername(signupRequest.getUsername()).isPresent()) {
                exists = true;
            }
        } catch (UsernameNotFoundException e) {}


        if(exists) throw new UserExistException("An account with this username already exists.");
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        return userRepo.save(user);
    }
}
