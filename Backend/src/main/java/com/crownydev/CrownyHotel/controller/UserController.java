// PASTE THIS FULL CODE INTO YOUR UserController.java FILE

package com.crownydev.CrownyHotel.controller;

import com.crownydev.CrownyHotel.dto.Response;
import com.crownydev.CrownyHotel.dto.UserDTO; // *** ADD THIS IMPORT ***
import com.crownydev.CrownyHotel.service.interfac.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // *** ADD THIS IMPORT ***
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    // ... (keep all your existing @GetMapping and @DeleteMapping methods) ...

    @GetMapping("/all")
    public ResponseEntity<Response> getAllUsers() {
        Response response = userService.getAllUsers();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Response> getUserById(@PathVariable Long userId) {
        Response response = userService.getUserById(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long userId) {
        Response response = userService.deleteUser(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/my-info")
    public ResponseEntity<Response> getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Response response = userService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // *** THIS IS THE NEW, SECURE UPDATE ENDPOINT ***
    @PutMapping("/update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or #userId == principal.id")
    public ResponseEntity<Response> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        Response response = userService.updateUser(userId, userDTO);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}