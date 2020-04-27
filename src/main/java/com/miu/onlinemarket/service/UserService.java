package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(Long id);

    User save(User user);

    void addAdminUser(User user);

    User update(User user, Long id);

    void delete(Long id);

}
