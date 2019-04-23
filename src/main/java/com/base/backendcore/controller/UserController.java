package com.base.backendcore.controller;

import com.base.backendcore.model.Role;
import com.base.backendcore.model.User;
import com.base.backendcore.model.enums.RoleNameEnum;
import com.base.backendcore.repository.UserRepository;
import com.base.backendcore.service.UserService;
import com.base.backendcore.util.exeption.AppException;
import com.base.backendcore.util.payload.ApiResponse;
import com.base.backendcore.util.payload.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }

        userService.saveUser(signUpRequest);
        // Creating user's account
       /* User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleNameEnum.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);*/



        return new ResponseEntity(new ApiResponse(true, "User registered successfully"), HttpStatus.OK);
    }
}
