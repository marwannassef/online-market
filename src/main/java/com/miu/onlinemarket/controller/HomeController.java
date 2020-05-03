package com.miu.onlinemarket.controller;

import com.miu.onlinemarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

	@Autowired
	private ProductService productService;

	@GetMapping("/home")
	@PreAuthorize("hasAuthority('ROLE_BUYER')")
	public String getAllProducts(Model model) {
		model.addAttribute("productList", productService.findAll());

		return "home";
	}

}
