package com.miu.onlinemarket.controller;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.SearchMessage;
import com.miu.onlinemarket.service.BuyerService;
import com.miu.onlinemarket.service.ProductService;
import com.miu.onlinemarket.service.SellerService;
import com.miu.onlinemarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;


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
	public ModelAndView getAllProducts(Model model, Principal principal, HttpSession session) {
	ModelAndView modelAndView = new ModelAndView();
		if(userService.hasRole("ROLE_BUYER")){
			model.addAttribute("productList", productService.findAll());
			session.setAttribute("buyer",buyerService.findByUsername(principal.getName()));

		}else if(userService.hasRole("ROLE_SELLER")){
			model.addAttribute("productList", sellerService.findSeller(principal.getName()).getProducts());
			session.setAttribute("sellerId",sellerService.findSeller(principal.getName()).getUserId());
			session.setAttribute("seller",sellerService.findSeller(principal.getName()));
		}
		modelAndView.addObject("searchMessage", new SearchMessage());
		modelAndView.setViewName("home");
		return modelAndView;
	}

	@GetMapping("/search")
	public String getProductByName(@ModelAttribute SearchMessage searchMessage
			, Model model,HttpSession session) {
		if(userService.hasRole("ROLE_BUYER")){
			model.addAttribute("productList", productService.searchByName(searchMessage.getSearch()));
		}else if(userService.hasRole("ROLE_SELLER")){
			long id = (long) session.getAttribute("sellerId");
			List<Product> productList = sellerService.searchByName(searchMessage.getSearch(), id);
			model.addAttribute("productList", productList);
		}
		return "home";
	}

	@RequestMapping(value = { "/addProduct" })
	public String addProduct(Model model) {
		model.addAttribute("product", new Product());
		return "addProduct";
	}

}
