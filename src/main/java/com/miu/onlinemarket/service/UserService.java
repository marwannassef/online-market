package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.User;

public interface UserService {

    User authenticate(User user);

    User save(User user);

    public boolean hasRole(String role);
    
    public User findByUsername(String username);
    
    public User update(User user);

    User findUser(String username);

}
