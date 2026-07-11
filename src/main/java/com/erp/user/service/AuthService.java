package com.erp.user.service;

import com.erp.user.AppUser;
import com.erp.user.JwtProvider;
import com.erp.user.dto.LoginRequest;
import com.erp.user.dto.SignupRequest;
import com.erp.user.dto.TokenResponse;
import com.erp.user.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(AppUserRepository appUserRepository,
                       PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public void signup(SignupRequest request) {
        if (appUserRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다: " + request.getUsername());
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        AppUser user = new AppUser(request.getUsername(), encodedPassword, request.getName(), request.getRole());
        appUserRepository.save(user);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        AppUser user = appUserRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        String token = jwtProvider.createToken(user.getUsername(), user.getRole().name());
        return new TokenResponse(token);
    }
}