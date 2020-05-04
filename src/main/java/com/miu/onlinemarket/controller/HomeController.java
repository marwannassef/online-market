package com.miu.onlinemarket.controller;

import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.service.BuyerService;
import com.miu.onlinemarket.service.ProductService;
import com.miu.onlinemarket.service.SellerService;
import com.miu.onlinemarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@SessionAttributes("user")
public class HomeController {

	@Autowired
	private ProductService productService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private UserService userService;

	@GetMapping("/home")
	public String getAllProducts(Model model, Principal principal) {

		if(userService.hasRole("ROLE_BUYER")){
			model.addAttribute("productList", productService.findAll());
			model.addAttribute("buyer",buyerService.findBuyer(principal.getName()));
		}else if(userService.hasRole("ROLE_SELLER")){
			model.addAttribute("productList", sellerService.findSeller(principal.getName()).getProducts());
			model.addAttribute("buyer",sellerService.findSeller(principal.getName()));
		}

		return "home";
	}

//	@GetMapping("/search")
//	public String getProductByName(Model model,
//								   @ModelAttribute("user") User user,
//								   BindingResult result) {
//
//		if(userService.hasRole("ROLE_BUYER")){
//			model.addAttribute("productList", productService.searchByName());
//		}
//		return "home";
//	}
}
