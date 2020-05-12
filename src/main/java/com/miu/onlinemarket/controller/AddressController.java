package com.miu.onlinemarket.controller;

import java.security.Principal;

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
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import com.miu.onlinemarket.service.BuyerService;

@Controller
public class AddressController {

	@Autowired
	private BuyerService buyerService;

	@GetMapping("/addAddress")
	public String showAddress(Model model, Principal principal) throws ResourceNotFoundException {
		Address address = buyerService.findByUsername(principal.getName()).getAddress();
		model.addAttribute("address", address == null ? new Address() : address);
		return "address";
	}

	@PostMapping({ "/addAddress" })
	public String addAddress(@Valid @ModelAttribute("address") Address address, BindingResult bindingResult,
			Principal principal) throws ResourceNotFoundException {
		Long userId = buyerService.findByUsername(principal.getName()).getUserId();
		buyerService.updateAddress(userId, address);
		return "redirect:/addAddress";
	}

}
