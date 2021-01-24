package com.cain.service;

import com.cain.entity.User;

public interface UserService {
    // User signup
    void register(User user);
    // User signin
    User login(User user);
}
