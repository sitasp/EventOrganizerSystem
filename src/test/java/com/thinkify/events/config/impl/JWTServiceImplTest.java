package com.thinkify.events.config.impl;

import com.thinkify.events.config.CustomUserDetails;
import com.thinkify.events.entity.User;
import com.thinkify.events.model.object.Role;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JWTServiceImplTest {

    private static final String TESTING_SECRET = "fp0nVzHwp1P1W4cAjgaRCnxC7W1ZGxpXPSWfQDFuiOA=";
    private User user;
    private UserDetails userDetails;


    @InjectMocks
    private JWTServiceImpl jwtService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtService = spy(new JWTServiceImpl());
    }

    @Test
    void extract_user_name_from_jwt() {
        String email = user.getEmail();
        String jwt = jwtService.generateToken(userDetails);
        String userNameFromJwt = jwtService.extractUserName(jwt);
        assertEquals(email, userNameFromJwt);
    }

    @Test
    void isTokenValid() {
        String jwt = jwtService.generateToken(userDetails);
        boolean res = jwtService.isTokenValid(jwt, user.getEmail());
        assertEquals(true, res);
    }

    @BeforeEach
    public void initEach(){
        byte[] keyBytes = Decoders.BASE64.decode(TESTING_SECRET);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        when(jwtService.getSigningKey()).thenReturn(key);
    }

    @BeforeAll
    public void init(){
        user = User.builder()
                .email("t@t.com")
                .id(1L)
                .firstName("t")
                .lastName("t")
                .mobileNumber("123")
                .password("123")
                .role(Role.USER)
                .build();
        userDetails = new CustomUserDetails(user);
    }
}