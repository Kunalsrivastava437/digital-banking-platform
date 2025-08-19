// com.bank.userservice.service.UserService
package com.bank.userservice.service;

import com.bank.userservice.model.User;
import com.bank.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String role) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(password)); // <-- important
        u.setRole(role);
        return userRepository.save(u);
    }
}
