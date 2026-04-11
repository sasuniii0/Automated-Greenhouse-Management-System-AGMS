package com.example.API.Gateway.controller;

import com.example.API.Gateway.dto.ApiResponseDTO;
import com.example.API.Gateway.dto.LoginRequestDTO;
import com.example.API.Gateway.dto.RegisterRequestDTO;
import com.example.API.Gateway.service.AuthService;
import com.example.API.Gateway.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // ✅ Reactive version of AuthenticationManager
    private final ReactiveAuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @PostMapping("/login")
    public Mono<ResponseEntity<ApiResponseDTO>> login(@Valid @RequestBody LoginRequestDTO request) {
        return authManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()))
                .map(auth -> ResponseEntity.ok(
                        ApiResponseDTO.builder()
                                .status(200)
                                .message("Login successful")
                                .data(Map.of("token", jwtUtil.generateToken(request.getEmail())))
                                .build()
                ));
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<ApiResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO request){
        return authService.register(request)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED).body(
                        ApiResponseDTO.builder()
                                .status(201)
                                .message("User registered successfully")
                                .data(Map.of("email", user.getEmail(), "name", user.getName()))
                                .build()
                ))
                // ✅ Handle duplicate email error gracefully
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.status(HttpStatus.CONFLICT).body(
                                ApiResponseDTO.builder()
                                        .status(409)
                                        .message(e.getMessage())
                                        .data(null)
                                        .build()
                        )
                ));
    }
}