package com.transportpark.model.service;

import com.transportpark.model.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User registration(User user);

    User findById(Long id);

    User getCurrentUser();
}
