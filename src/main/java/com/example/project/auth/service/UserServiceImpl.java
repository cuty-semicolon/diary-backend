package com.example.project.auth.service;


import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project.auth.UserService;
import com.example.project.auth.eo.ERole;
import com.example.project.auth.model.Account;
import com.example.project.auth.model.Role;
import com.example.project.auth.repository.RoleRepository;
import com.example.project.auth.repository.AccountRepository;

@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    ////////////////////////////////////////////////////////////////////////////////
    //< public functions (override)

    /**
     * Find a user information by email
     */
    @Override
    public Account getUserByEmail(String email) throws Exception {
        return accountRepository.findByEmail(email);
    }

    /**
     * Find a user information by user name
     */
    @Override
    public Account getUserByUsername(String username) throws Exception {
        return accountRepository.findByUsername(username);
    }

    /**
     * Save the user information
     */
    @Override
    public Account setUser(Account user) throws Exception {
        //< encoding the password
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        //< set the active flag
        user.setIsActive(true);
        //< set the user role
        Role userRole = null;
        if(user.getUsername().equals("admin")) {
            userRole = roleRepository.findByRole(ERole.ADMIN.getValue());
        }
        else if(user.getUsername().equals("user")) {
            userRole = roleRepository.findByRole(ERole.MANAGER.getValue());
        }
        else {
            userRole = roleRepository.findByRole(ERole.GUEST.getValue());
        }

        //< set the user roles
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));

        //< save the user information and return result
        return accountRepository.save(user);
    }
}