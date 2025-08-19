package com.crownydev.CrownyHotel.service.impl;

import com.crownydev.CrownyHotel.dto.Response;
import com.crownydev.CrownyHotel.entity.User;
import com.crownydev.CrownyHotel.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    // This test was already passing, no changes needed.
    @Test
    void testRegister_WhenUserDoesNotExist_ShouldRegisterSuccessfully() {
        // ARRANGE
        User userToRegister = new User();
        userToRegister.setEmail("newuser@test.com");
        userToRegister.setPassword("password123");

        when(userRepository.existsByEmail("newuser@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(userToRegister);

        // ACT
        Response response = userService.register(userToRegister);

        // ASSERT
        assertEquals(200, response.getStatusCode());
        assertEquals("User Registered Successfully", response.getMessage());
        assertThat(response.getUser()).isNotNull();
    }

    @Test
    void testRegister_WhenUserAlreadyExists_ShouldReturnErrorResponse() { // Method name updated for clarity
        // ARRANGE: Set up for a user that already exists.
        User existingUser = new User();
        existingUser.setEmail("existing@test.com");
        existingUser.setPassword("password123");

        when(userRepository.existsByEmail("existing@test.com")).thenReturn(true);

        // ACT: Call the method and get the response object.
        Response response = userService.register(existingUser);

        // ASSERT: Check the details of the returned response object.
        assertEquals(400, response.getStatusCode());
        assertEquals("User with email existing@test.com already exists", response.getMessage());
        assertThat(response.getUser()).isNull(); // Ensure no user data is returned on failure.
    }
}