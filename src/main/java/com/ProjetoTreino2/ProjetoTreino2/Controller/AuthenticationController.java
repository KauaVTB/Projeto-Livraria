package com.ProjetoTreino2.ProjetoTreino2.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProjetoTreino2.ProjetoTreino2.dto.AuthenticationDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.LoginResponseDTO;
import com.ProjetoTreino2.ProjetoTreino2.dto.RegisterDTO;
import com.ProjetoTreino2.ProjetoTreino2.Repository.UserRepository;
import com.ProjetoTreino2.ProjetoTreino2.Services.TokenService;
import com.ProjetoTreino2.ProjetoTreino2.Entities.User;

import jakarta.validation.Valid;
import lombok.experimental.var;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final UserRepository userRepository;

    public AuthenticationController(UserRepository userRepository, TokenService tokenService,
            AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data) {
        if (this.userRepository.findByLogin(data.login()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = passwordEncoder.encode(data.password());
        User user = new User(data.login(), encryptedPassword, data.role());
        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }

}
