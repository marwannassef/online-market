package com.miu.onlinemarket.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.miu.onlinemarket.domain.PaymentMethod;
import com.miu.onlinemarket.service.BuyerService;

@Controller
public class PaymentController {

	@Autowired
	private BuyerService buyerService;

	@GetMapping("/addPayment")
	public String addPayment(@ModelAttribute("paymentMethod") PaymentMethod paymentMethod) {
		return "paymentMethod";
	}

	@PostMapping("/addPayment")
	public String addPayment(@Valid @ModelAttribute("paymentMethod") PaymentMethod paymentMethod, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		buyerService.updatePayment(userId, paymentMethod);

		return "redirect:/home";
	}

}
