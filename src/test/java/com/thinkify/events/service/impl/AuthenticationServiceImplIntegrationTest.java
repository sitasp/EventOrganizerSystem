package com.thinkify.events.service.impl;

import com.thinkify.events.EventsApplication;
import com.thinkify.events.config.ApplicationSecurityConfig;
import com.thinkify.events.config.impl.JWTServiceImpl;
import com.thinkify.events.entity.User;
import com.thinkify.events.exception.UserNotFoundException;
import com.thinkify.events.model.object.Role;
import com.thinkify.events.model.request.AuthenticationRequest;
import com.thinkify.events.model.response.AuthenticationResponse;
import com.thinkify.events.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Import({ApplicationSecurityConfig.class})
@SpringBootTest(classes = EventsApplication.class)
public class AuthenticationServiceImplIntegrationTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder; // Assuming you have a password encoder bean

    @Autowired
    private JWTServiceImpl jwtService;

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    public void setUp() {
        // Initialize test data in the database
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword(passwordEncoder.encode("test"));
        user.setFirstName("Test");
        user.setLastName("User");
        user.setMobileNumber("1234567890");
        user.setRole(Role.USER);

        userRepo.save(user);
    }

    @Test
    public void testAuthenticate() throws UserNotFoundException {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("test");

        // Act
        AuthenticationResponse response = authenticationService.authenticate(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getToken());

        // Validate token (optional)
        String jwtToken = response.getToken();
        String userNameFromJwt = jwtService.extractUserName(jwtToken);
        assertEquals(request.getEmail(), userNameFromJwt);
    }
}
