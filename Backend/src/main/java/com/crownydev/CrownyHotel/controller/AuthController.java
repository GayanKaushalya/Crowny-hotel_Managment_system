package com.crownydev.CrownyHotel.controller;

import com.crownydev.CrownyHotel.dto.LoginRequest;
import com.crownydev.CrownyHotel.dto.Response;
import com.crownydev.CrownyHotel.entity.User;
import com.crownydev.CrownyHotel.service.interfac.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IUserService userService;

    public AuthController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody User registrationRequest) {
        Response response = userService.register(registrationRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest) {
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}