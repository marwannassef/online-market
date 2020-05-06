package com.miu.onlinemarket.service.impl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.repository.UserRepository;
import com.miu.onlinemarket.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User authenticate(User user) {
        return userRepository.findByUsername(user.getUsername());
    }

    @Override
    public User save(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
    	User oldUser = userRepository.findByUsername(user.getUsername());
    	user.setRoles(oldUser.getRoles());
        return userRepository.save(user);
    }


    @Override
    public User findByUsername(String username) {
    	User user = userRepository.findByUsername(username);
    	if (user.getPhoto() != null && user.getPhoto().length != 0)
    		user.setPhotoBase64(Base64.getEncoder().encodeToString(user.getPhoto()));
        return user;
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
    }

    @Override
    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }
}
