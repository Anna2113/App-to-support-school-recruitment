package com.example.aplikacja.appuser;

import com.example.aplikacja.appuser.UserRepository;
import com.example.aplikacja.appuser.AppUser;
import com.example.aplikacja.student.entity.Student;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            " user with email %s not found";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public Optional<AppUser> findEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public String signUpUser(AppUser appUser) {
        boolean userExists = userRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        userRepository.save(appUser);


        return "registered";
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public int diableAppUser(String email) {
        return userRepository.disableAppUser(email);
    }

    public Optional<AppUser> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<AppUser> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public AppUser updateAccount(AppUser appUser) {
        AppUser userToUpdate = findUserById(appUser.getId()).orElse(null);
        if (userToUpdate != null) {
            userToUpdate.setAppUserRole(new ArrayList<>());
            userToUpdate.getAppUserRole().addAll(appUser.getAppUserRole());
            return userRepository.save(userToUpdate);
        } else {
            return appUser;
        }
    }

}
