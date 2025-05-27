package com.seiji.dslist.controllers;

import com.seiji.dslist.dto.LoginDTO;
import com.seiji.dslist.dto.RegisterDTO;
import com.seiji.dslist.entities.User;
import com.seiji.dslist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository to handle user data

    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok("User logged in successfully: " + auth.getName());
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO) {
        if (this.userRepository.findByUsername(registerDTO.username()) != null) return ResponseEntity.badRequest().body("Username already exists");
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User user = new User(registerDTO.username(), encryptedPassword, registerDTO.role());

        this.userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
