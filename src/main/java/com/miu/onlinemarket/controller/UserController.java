package com.miu.onlinemarket.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.miu.onlinemarket.domain.Role;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = { "/login", "/" })
	public String login(Model model) {
		return "login";
	}

	@RequestMapping(value = "/home")
	public String home(Model model) {
		return "home";
	}

	@RequestMapping(value = "/signup")
	public String signUp(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "saveuser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
		System.out.println(bindingResult.toString());
		if (!bindingResult.hasErrors()) { // validation errors
			if (user.getPassword().equals(user.getPasswordCheck())) { // check password match
				String pwd = user.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);

				User newUser = new User();
				newUser.setPassword(hashPwd);
				newUser.setEmail(user.getUsername());
				List<Role> roles = new ArrayList<Role>();
				roles.add(new Role("USER"));
				newUser.setRoles(roles);
				if (userRepository.findByUsername(user.getUsername()) == null) {
					userRepository.save(newUser);
				} else {
					bindingResult.rejectValue("username", "error.userexists", "Username already exists");
					return "signup";
				}
			} else {
				bindingResult.rejectValue("passwordCheck", "error.pwdmatch", "Passwords does not match");
				return "signup";
			}
		} else {
			return "signup";
		}
		return "signup";
	}

}