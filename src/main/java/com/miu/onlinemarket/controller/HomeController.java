package com.miu.onlinemarket.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import com.miu.onlinemarket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Item;
import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.SearchMessage;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.Status;

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

	@Autowired
	private BuyerService buyerService;

	@GetMapping("/home")
	public ModelAndView getAllProducts(Model model, Principal principal) throws ResourceNotFoundException {
		ModelAndView modelAndView = new ModelAndView();
		if (userService.hasRole("ROLE_BUYER")) {
			model.addAttribute("productList", productService.findAll());
			Buyer buyer = buyerService.findByUsername(principal.getName());
			List<Seller> sellerList = sellerService.findSellersByBuyerId(buyer.getUserId());
			model.addAttribute("sellerList",sellerList);
		} else if (userService.hasRole("ROLE_SELLER")) {
			model.addAttribute("productList", sellerService.findSeller(principal.getName()).getProducts());
			model.addAttribute("seller", sellerService.findSeller(principal.getName()));
		}
		modelAndView.addObject("searchMessage", new SearchMessage());

		modelAndView.setViewName("home");
		
		String sellerName = (String) model.asMap().get("sellerName");
		if (sellerName != null) {
			model.addAttribute("sellerName", sellerName);
		}else{
			model.addAttribute("sellerName","Select seller");
		}
		List<Product> products = (List<Product>)model.getAttribute("products");
		if(products != null){
			model.addAttribute("productList",products);
		}

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

	@GetMapping({ "/searchBySeller" })
	public String searchBySeller(@RequestParam("id") Long id, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, Principal principal) throws ResourceNotFoundException {

		Seller seller = sellerService.findSellerById(id);
		List<Product> productList = sellerService.findSellerById(id).getProducts();
		redirectAttributes.addFlashAttribute("sellerName", seller.getFirstName() + " " + seller.getLastName());
		redirectAttributes.addFlashAttribute("products", productList);
		return "redirect:/home";
	}

}
