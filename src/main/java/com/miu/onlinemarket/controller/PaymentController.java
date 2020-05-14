package com.miu.onlinemarket.controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Checkout;
import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.PaymentMethod;
import com.miu.onlinemarket.domain.Status;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import com.miu.onlinemarket.service.AddressService;
import com.miu.onlinemarket.service.BuyerService;

@Controller
public class PaymentController {

	@Autowired
	private BuyerService buyerService;

	@Autowired
	private AddressService addressService;

	@GetMapping("/addPayment")
	public String showPayment(Model model, Principal principal) throws ResourceNotFoundException {
		PaymentMethod paymentMethod = buyerService.findByUsername(principal.getName()).getPaymentMethod();
		model.addAttribute("paymentMethod", paymentMethod == null ? new PaymentMethod() : paymentMethod);
		String status = (String) model.asMap().get("status");
		model.addAttribute("status", status);
		return "paymentMethod";
	}

	@PostMapping("/addPayment")
	public String addPayment(@Valid @ModelAttribute("paymentMethod") PaymentMethod paymentMethod,
			BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes, Model model)
			throws Exception {
		if (bindingResult.hasErrors()) {
			model.addAttribute("status", "failed");
			return "paymentMethod";
		}
		Long userId = buyerService.findByUsername(principal.getName()).getUserId();
		buyerService.updatePayment(userId, paymentMethod);
		redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/home";
	}

	@GetMapping("/checkout")
	public String checkout(Model model, Principal principal) throws Exception {
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Optional<Order> order = buyer.getOrders().stream().filter(ord -> ord.getStatus() == Status.PREPARED)
				.findFirst();
		model.addAttribute("order", order.orElse(new Order()));
		model.addAttribute("buyer", buyer);
		Optional<String> country = addressService.loadCountries().stream()
				.filter(c -> c.getId() == buyer.getAddress().getCountry()).findFirst().map(c -> c.getName());
		Optional<String> state = addressService.loadStates(buyer.getAddress().getCountry()).stream()
				.filter(c -> c.getId() == buyer.getAddress().getState()).findFirst().map(c -> c.getName());
		Optional<String> city = addressService.loadCities(buyer.getAddress().getState()).stream()
				.filter(c -> c.getId() == buyer.getAddress().getCity()).findFirst().map(c -> c.getName());
		model.addAttribute("country", country.orElse(""));
		model.addAttribute("state", state.orElse(""));
		model.addAttribute("city", city.orElse(""));
		model.addAttribute("checkout", new Checkout());
		model.addAttribute("discount", false);
		return "checkout";
	}

}
