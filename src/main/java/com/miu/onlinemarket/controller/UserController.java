package com.miu.onlinemarket.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.service.BuyerService;
import com.miu.onlinemarket.service.SellerService;

@Controller
@SessionAttributes("type")
public class UserController {

	@Autowired
	SellerService sellerService;

	@Autowired
	BuyerService buyerService;

	@RequestMapping(value = { "/login", "/" })
	public String login(SessionStatus status) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(auth.getPrincipal());
		if (!auth.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
			return "redirect:/home";
		}

		status.setComplete();
		return "login";
	}

	@RequestMapping(value = "/signup/{type}")
	public String signUp(@PathVariable String type, Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("type", type);
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		String type = model.getAttribute("type").toString();
		if (bindingResult.hasErrors()) {
			return "redirect:/signup/" + type;
		}
		MultipartFile image = user.getImage();
		if (image != null && !image.isEmpty()) {
			try {
				user.setPhoto(image.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (type.equalsIgnoreCase("seller")) {
			Seller seller = new Seller(user, false, null, null);
			sellerService.save(seller);
		} else {
			Buyer buyer = new Buyer(user, null, null, null);
			buyerService.save(buyer);
		}
		return "redirect:/login";
	}

}