package com.thinkify.events.controller;

import com.thinkify.events.exception.UserNotFoundException;
import com.thinkify.events.model.request.AuthenticationRequest;
import com.thinkify.events.model.response.AuthenticationResponse;
import com.thinkify.events.model.request.RegisterRequest;
import com.thinkify.events.model.response.BaseResponse;
import com.thinkify.events.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        AuthenticationResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<BaseResponse> authenticate(@RequestBody AuthenticationRequest request){
        BaseResponse response = null;
        try {
            response = authService.authenticate(request);
        } catch (UserNotFoundException e) {
            response = new BaseResponse("User not found");
        }
        return ResponseEntity.ok(response);
    }
}
