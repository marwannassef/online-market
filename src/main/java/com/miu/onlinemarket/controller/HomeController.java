package com.miu.onlinemarket.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.SearchMessage;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.service.ProductService;
import com.miu.onlinemarket.service.ReviewService;
import com.miu.onlinemarket.service.SellerService;
import com.miu.onlinemarket.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private ProductService productService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/home")
	public ModelAndView getAllProducts(Model model, Principal principal) throws ResourceNotFoundException {
		ModelAndView modelAndView = new ModelAndView();
		if (userService.hasRole("ROLE_BUYER")) {
			model.addAttribute("productList", productService.findAll());
		} else if (userService.hasRole("ROLE_SELLER")) {
			model.addAttribute("productList", sellerService.findSeller(principal.getName()).getProducts());
		}
		modelAndView.addObject("searchMessage", new SearchMessage());

		modelAndView.setViewName("home");

		String tab = (String) model.asMap().get("tab");
		model.addAttribute("tab", tab);
		return modelAndView;
	}

	@GetMapping("{tab}")
	public String tab(@PathVariable String tab, Model model) {
		if (tab.equalsIgnoreCase("approve-review")) {
			model.addAttribute("reviewList", reviewService.findUnApprovedReview());
		} else {
			model.addAttribute("sellerList", sellerService.findUnApprovedSeller());
		}
		return tab;
	}

	@GetMapping("/search")
	public String getProductByName(@ModelAttribute SearchMessage searchMessage, Principal principal, Model model,
			HttpSession session) throws ResourceNotFoundException {
		if (userService.hasRole("ROLE_BUYER")) {
			model.addAttribute("productList", productService.searchByName(searchMessage.getSearch()));
		} else if (userService.hasRole("ROLE_SELLER")) {
			Seller seller = sellerService.findSeller(principal.getName());
			List<Product> productList = sellerService.searchByName(searchMessage.getSearch(), seller.getUserId());
			model.addAttribute("productList", productList);
		}
		return "home";
	}

}
