package com.base.backendcore.service.impl;

import com.base.backendcore.model.Role;
import com.base.backendcore.model.User;
import com.base.backendcore.model.enums.RoleNameEnum;
import com.base.backendcore.repository.RoleRepository;
import com.base.backendcore.repository.UserRepository;
import com.base.backendcore.service.UserService;
import com.base.backendcore.util.exeption.AppException;
import com.base.backendcore.util.payload.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public void saveUser(SignUpRequest signUpRequest) {
        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                signUpRequest.getPassword());
        user.setRoles(signUpRequest.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<RoleNameEnum> roles = new HashSet<>();
        signUpRequest.getRoles().forEach(role -> { roles.add(role.getName());});
        Set<Role> userRoles = roleRepository.finfByNames(roles);

        if(userRoles == null || userRoles.isEmpty()) {
            new AppException("User Role not set.");
        }
        user.setRoles(userRoles);

        User result = userRepository.save(user);
    }
}
