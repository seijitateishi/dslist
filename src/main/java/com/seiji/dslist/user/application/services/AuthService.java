package com.seiji.dslist.user.application.services;

import com.seiji.dslist.user.application.dto.LoginDTO;
import com.seiji.dslist.user.application.dto.LoginResponseDTO;
import com.seiji.dslist.user.application.dto.RegisterDTO;
import com.seiji.dslist.user.domain.User;
import com.seiji.dslist.user.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public LoginResponseDTO login(LoginDTO loginDTO) {
        UserDetails user = this.loadUserByUsername(loginDTO.username());
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!new BCryptPasswordEncoder().matches(loginDTO.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String token = tokenService.generateToken((User) user);
        return new LoginResponseDTO(token);
    }

    public void register(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.username()) != null) {
            throw new RuntimeException("User already exists");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        User user = new User(registerDTO.username(), encryptedPassword, registerDTO.role());
        userRepository.save(user);
    }
} 