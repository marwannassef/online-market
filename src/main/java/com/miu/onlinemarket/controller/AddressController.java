package com.miu.onlinemarket.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miu.onlinemarket.domain.Address;
import com.miu.onlinemarket.service.BuyerService;

@Controller
public class AddressController {

	@Autowired
	private BuyerService buyerService;

	@GetMapping("/addAddress")
	public String addAddress(@ModelAttribute("address") Address address, Model model) {
		model.addAttribute("address", address);
		return "address";
	}

	@PostMapping({ "/addAddress" })
	public String addAddress(@Valid @ModelAttribute("address") Address address, BindingResult bindingResult,
			HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		buyerService.updateAddress(userId, address);
		System.out.println(address.getCity());
		return "redirect:/home";
	}

}
