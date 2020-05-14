package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;

public interface UserService {

	User authenticate(User user);

	User save(User user);

	public boolean hasRole(String role);

	public User findByUsername(String username) throws ResourceNotFoundException;

	public User update(User user) throws ResourceNotFoundException;

	User findUser(String username);

}
