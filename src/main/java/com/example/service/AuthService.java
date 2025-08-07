package com.example.service;

import com.example.dto.AuthRequest;
import com.example.dto.AuthResponse;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthResponse login(AuthRequest request) {
        var user = repo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    public String register(AuthRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();
        repo.save(user);
        return "User registered successfully";
    }
}
