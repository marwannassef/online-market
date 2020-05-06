package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;

public interface UserService {

    User authenticate(User user);

    User save(User user);

    public boolean hasRole(String role);

    User findUser(String username);

}
