package com.crownydev.CrownyHotel.service.interfac;

import com.crownydev.CrownyHotel.dto.LoginRequest;
import com.crownydev.CrownyHotel.dto.Response;
import com.crownydev.CrownyHotel.dto.UserDTO;
import com.crownydev.CrownyHotel.entity.User;

public interface IUserService {

    Response register(User user);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response deleteUser(Long userId);

    Response getUserById(Long userId);

    Response getMyInfo(String email);

    Response updateUser(Long userId, UserDTO userDTO);
}