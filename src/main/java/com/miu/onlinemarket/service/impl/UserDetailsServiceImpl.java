package com.miu.onlinemarket.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository repository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User curruser = repository.findByUsername(username);
		UserDetails user = new org.springframework.security.core.userdetails.User(username, curruser.getPassword(),
				true, true, true, true,
				AuthorityUtils.createAuthorityList(curruser.getRoles().iterator().next().getName()));
		return user;
	}

}