package com.example.API.Gateway.service;

import com.example.API.Gateway.dto.RegisterRequestDTO;
import com.example.API.Gateway.entity.User;
import com.example.API.Gateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Mono<User> register(RegisterRequestDTO request) {
        return userRepository.findByEmail(request.getEmail())
                .flatMap(existingUser ->
                        // ✅ If email already exists, return error
                        Mono.<User>error(new RuntimeException("Email already in use")))
                .switchIfEmpty(
                        // ✅ If email is free, save new user
                        Mono.defer(() -> {
                            User newUser = User.builder()
                                    .name(request.getName())
                                    .email(request.getEmail())
                                    .password(passwordEncoder.encode(request.getPassword()))
                                    .role("USER")
                                    .build();
                            return userRepository.save(newUser);
                        })
                );
    }

}
