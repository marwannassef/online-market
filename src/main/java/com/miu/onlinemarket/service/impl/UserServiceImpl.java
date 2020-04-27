package com.miu.onlinemarket.service.impl;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.miu.onlinemarket.domain.Role;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.repository.RoleRepository;
import com.miu.onlinemarket.repository.UserRepository;
import com.miu.onlinemarket.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
    	return null;
//    	return userRepository.findById(id);
//        return userRepository.findById(id)
//                .orElseThrow(() -> new RecordNotFoundException("User with id : " + id + " is not available"));
    }

    @Override
    public User save(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new RecordNotFoundException("User with id : " + id + " is not available"));
//        userRepository.delete(user);
    }

    @Override
    public User update(User user, Long id) {
        return userRepository.findById(id).map(userToUpdate -> {
            userToUpdate.setDateOfBirth(user.getDateOfBirth());
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setUsername(user.getUsername());
            userToUpdate.setFirstName(user.getFirstName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setPhoneNumber(user.getPhoneNumber());
            userToUpdate.setRoles(user.getRoles());
            userToUpdate.setPassword(encodePassword(user.getPassword()));
            return userRepository.save(userToUpdate);
        }).orElseGet(() -> {
            return userRepository.save(user);
        });
    }

    public void addAdminUser(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleRepo.findByName("ROLE_ADMIN"));
        user.setRoles(roles);
        userRepository.save(user);
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

}
