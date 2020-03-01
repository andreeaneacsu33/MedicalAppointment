package com.project.service;

import com.project.model.User;
import com.project.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<User> findUsers();
    User save(UserDTO user);
    User findUser(String username);
}
