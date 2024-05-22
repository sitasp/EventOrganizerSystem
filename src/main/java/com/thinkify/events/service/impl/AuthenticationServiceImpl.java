package com.thinkify.events.service.impl;

import com.thinkify.events.config.CustomUserDetails;
import com.thinkify.events.config.JWTService;
import com.thinkify.events.entity.User;
import com.thinkify.events.exception.UserNotFoundException;
import com.thinkify.events.model.object.Role;
import com.thinkify.events.model.request.AuthenticationRequest;
import com.thinkify.events.model.response.AuthenticationResponse;
import com.thinkify.events.model.request.RegisterRequest;
import com.thinkify.events.repo.UserRepo;
import com.thinkify.events.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = createUser(request);
        userRepo.save(user);
        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundException {
        LOGGER.info("authentication request #1- " + request);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword() // Use the raw password here
                    )
            );
        } catch (AuthenticationException e) {
            LOGGER.error("Authentication failed for user: " + request.getEmail(), e);
            throw new UserNotFoundException();
        }
        LOGGER.info("authentication request #2- " + request);
        User user = userRepo.getUserByEmail(request.getEmail());
        if(Objects.isNull(user))
            throw new UserNotFoundException();

        String jwtToken = jwtService.generateToken(new CustomUserDetails(user));
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public User createUser(RegisterRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMobileNumber(request.getMobileNumber());
        user.setRole(Role.USER);
        return user;
    }
}
