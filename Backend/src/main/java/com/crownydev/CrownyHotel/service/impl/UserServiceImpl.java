package com.crownydev.CrownyHotel.service.impl;

import com.crownydev.CrownyHotel.dto.LoginRequest;
import com.crownydev.CrownyHotel.dto.Response;
import com.crownydev.CrownyHotel.dto.UserDTO;
import com.crownydev.CrownyHotel.entity.User;
import com.crownydev.CrownyHotel.exception.OurException;
import com.crownydev.CrownyHotel.repo.UserRepository;
import com.crownydev.CrownyHotel.service.interfac.IUserService;
import com.crownydev.CrownyHotel.utils.JWTUtils;
import com.crownydev.CrownyHotel.utils.Utils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    // *** FIX: Using modern Constructor Injection ***
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Response register(User user) {
        Response response = new Response();
        try {
            if (user.getRole() == null || user.getRole().isBlank()) {
                user.setRole("USER");
            }
            if (userRepository.existsByEmail(user.getEmail())) {
                throw new OurException("User with email " + user.getEmail() + " already exists");
            }
            // Encode the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            response.setStatusCode(200);
            response.setMessage("User Registered Successfully");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An error occurred during user registration: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("User not found"));

            // *** FIX: The 'user' object now correctly implements UserDetails ***
            var token = jwtUtils.generateToken(user);

            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("Login Successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("An error occurred during login: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<User> userList = userRepository.findAll();

            // *** FIX: Correctly map the list of entities to a list of DTOs ***
            List<UserDTO> userDTOList = userList.stream()
                    .map(Utils::mapUserEntityToUserDTO)
                    .collect(Collectors.toList());

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUserList(userDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(Long userId) {
        Response response = new Response();
        try {
            userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));
            userRepository.deleteById(userId);
            response.setStatusCode(200);
            response.setMessage("User deleted successfully");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error deleting user: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(Long userId) {
        Response response = new Response();
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting user by ID: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("Successful");
            response.setUser(userDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting user info: " + e.getMessage());
        }
        return response;
    }
    // Add this entire method to your UserServiceImpl.java class.
// You can place it after the getMyInfo() method.

    @Override
    public Response updateUser(Long userId, UserDTO userDTO) {
        Response response = new Response();
        try {
            // Find the existing user in the database or throw an exception
            User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new OurException("User not found with id: " + userId));

            // Update the user's details from the data transfer object (DTO)
            // We only allow changing the name and phone number for security reasons.
            // Email/password changes should have their own dedicated, more secure processes.
            existingUser.setName(userDTO.getName());
            existingUser.setPhoneNumber(userDTO.getPhoneNumber());

            // Save the updated user entity back to the database
            User updatedUser = userRepository.save(existingUser);

            // Map the updated entity back to a DTO to return in the response
            UserDTO updatedUserDTO = Utils.mapUserEntityToUserDTO(updatedUser);

            response.setStatusCode(200);
            response.setMessage("User updated successfully");
            response.setUser(updatedUserDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error updating user: " + e.getMessage());
        }
        return response;
    }
}