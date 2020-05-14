package com.miu.onlinemarket.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miu.onlinemarket.domain.Address;
import com.miu.onlinemarket.domain.City;
import com.miu.onlinemarket.domain.State;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import com.miu.onlinemarket.service.AddressService;
import com.miu.onlinemarket.service.BuyerService;

@Controller
public class AddressController {

	@Autowired
	private BuyerService buyerService;

	@Autowired
	private AddressService addressService;

	@GetMapping("/addAddress")
	public String showAddress(Model model, Principal principal) throws Exception {
		Address address = buyerService.findByUsername(principal.getName()).getAddress();
		model.addAttribute("address", address == null ? new Address() : address);
		String status = (String) model.asMap().get("status");
		model.addAttribute("status", status);
		model.addAttribute("countries", addressService.loadCountries());
		return "address";
	}

	@PostMapping({ "/addAddress" })
	public String addAddress(@Valid @ModelAttribute("address") Address address, BindingResult bindingResult,
							 Principal principal, RedirectAttributes redirectAttributes,Model model) throws Exception {
		if(bindingResult.hasErrors()) {
			model.addAttribute("status", "failed");
			model.addAttribute("countries", addressService.loadCountries());
			return "address";
		}
		Long userId = buyerService.findByUsername(principal.getName()).getUserId();
		buyerService.updateAddress(userId, address);
		redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/states")
	@ResponseBody
	public List<State> getStates(@RequestParam("id") Long id) throws Exception {
		return addressService.loadStates(id);
	}
	
	@RequestMapping(value = "/cities")
	@ResponseBody
	public List<City> getCities(@RequestParam("id") Long id) throws Exception {
		return addressService.loadCities(id);
	}

}
