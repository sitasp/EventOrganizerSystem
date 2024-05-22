package com.thinkify.events.config;

import com.thinkify.events.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

public interface JWTService {
    boolean isTokenValid(String jwt, String userName);
    String extractUserName(String jwt);
    String generateToken(UserDetails userDetails);
}
