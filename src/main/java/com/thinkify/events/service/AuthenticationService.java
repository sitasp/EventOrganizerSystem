package com.thinkify.events.service;

import com.thinkify.events.entity.User;
import com.thinkify.events.exception.UserNotFoundException;
import com.thinkify.events.model.request.AuthenticationRequest;
import com.thinkify.events.model.response.AuthenticationResponse;
import com.thinkify.events.model.request.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request) throws UserNotFoundException;
    User createUser(RegisterRequest request);
}
