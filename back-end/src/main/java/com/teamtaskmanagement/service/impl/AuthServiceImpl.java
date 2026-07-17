package com.teamtaskmanagement.service.impl;

import com.teamtaskmanagement.dto.request.LoginRequest;
import com.teamtaskmanagement.dto.request.LogoutRequest;
import com.teamtaskmanagement.dto.response.LoginResponse;
import com.teamtaskmanagement.dto.response.UserProfileResponse;
import com.teamtaskmanagement.entity.RefreshToken;
import com.teamtaskmanagement.entity.User;
import com.teamtaskmanagement.exception.UnauthorizedException;
import com.teamtaskmanagement.mapper.UserMapper;
import com.teamtaskmanagement.repository.RefreshTokenRepository;
import com.teamtaskmanagement.repository.UserRepository;
import com.teamtaskmanagement.security.jwt.JwtTokenProvider;
import com.teamtaskmanagement.security.service.CustomUserPrincipal;
import com.teamtaskmanagement.service.AuthService;
import com.teamtaskmanagement.service.CurrentUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final CurrentUserService currentUserService;
    private final long refreshExpiration;

    public AuthServiceImpl(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository,
                           PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
                           CurrentUserService currentUserService,
                           @Value("${jwt.refresh-token-expiration}") long refreshExpiration) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.currentUserService = currentUserService;
        this.refreshExpiration = refreshExpiration;
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
        if (!Boolean.TRUE.equals(user.getActive()) || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadCredentialsException("Invalid email or password");
        }
        String access = jwtTokenProvider.generateAccessToken(new CustomUserPrincipal(user));
        RefreshToken refresh = new RefreshToken();
        refresh.setUser(user);
        refresh.setToken(UUID.randomUUID().toString() + UUID.randomUUID());
        refresh.setCreatedAt(LocalDateTime.now());
        refresh.setExpiresAt(LocalDateTime.now().plusNanos(refreshExpiration * 1_000_000));
        refresh.setRevoked(false);
        refreshTokenRepository.save(refresh);
        return new LoginResponse(access, refresh.getToken(), UserMapper.toProfile(user));
    }

    public UserProfileResponse me() {
        User user = userRepository.findByEmail(currentUserService.getCurrentUserEmail())
                .orElseThrow(() -> new UnauthorizedException("Unauthorized"));
        return UserMapper.toProfile(user);
    }

    @Transactional
    public void logout(LogoutRequest request) {
        Integer currentUserId = currentUserService.getCurrentUserId();
        RefreshToken token = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));
        if (!token.getUser().getUserId().equals(currentUserId)) throw new UnauthorizedException("Invalid refresh token");
        token.setRevoked(true);
        token.setRevokedAt(LocalDateTime.now());
        refreshTokenRepository.save(token);
    }
}
