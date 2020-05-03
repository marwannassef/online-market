package com.miu.onlinemarket.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.repository.UserRepository;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = { "/login", "/" })
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/signup")
	public String signUp(Model model) {
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "redirect:/signup";
		}
		MultipartFile image = user.getImage();
		if (image != null && !image.isEmpty()) {
			try {
				user.setPhoto(image.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "redirect:/login";
	}

}