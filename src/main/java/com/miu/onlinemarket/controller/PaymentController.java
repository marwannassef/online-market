package com.miu.onlinemarket.controller;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.miu.onlinemarket.service.BuyerService;

@Controller
public class PaymentController {

	@Autowired
	private BuyerService buyerService;

	@GetMapping("/addPayment")
	public String showPayment(Model model, Principal principal) throws ResourceNotFoundException {
		PaymentMethod paymentMethod = buyerService.findByUsername(principal.getName()).getPaymentMethod();
		model.addAttribute("paymentMethod", paymentMethod == null ? new PaymentMethod() : paymentMethod);
		String status = (String) model.asMap().get("status");
		model.addAttribute("status", status);
		return "paymentMethod";
	}

	@PostMapping("/addPayment")
	public String addPayment(@Valid @ModelAttribute("paymentMethod") PaymentMethod paymentMethod, Principal principal,
			RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
		Long userId = buyerService.findByUsername(principal.getName()).getUserId();
		buyerService.updatePayment(userId, paymentMethod);
		redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/home";
	}

	@GetMapping("/checkout")
	public String checkout(Model model, Principal principal) throws ResourceNotFoundException {
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Optional<Order> order = buyer.getOrders().stream().filter(ord -> ord.getStatus() == Status.PREPARED)
				.findFirst();
		model.addAttribute("order", order.orElse(new Order()));
		model.addAttribute("buyer", buyer);
		model.addAttribute("checkout", new Checkout());
		return "checkout";
	}

}
