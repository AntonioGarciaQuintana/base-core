package com.base.backendcore.service;

import com.base.backendcore.util.payload.SignUpRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void saveUser(SignUpRequest signUpRequest);
}
