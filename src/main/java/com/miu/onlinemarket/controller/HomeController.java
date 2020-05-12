package com.miu.onlinemarket.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.SearchMessage;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.Status;
import com.miu.onlinemarket.service.BuyerService;
import com.miu.onlinemarket.service.ProductService;
import com.miu.onlinemarket.service.ReviewService;
import com.miu.onlinemarket.service.SellerService;
import com.miu.onlinemarket.service.UserService;


@Controller
@SessionAttributes("cartCount")
public class HomeController {

	@Autowired
	private ProductService productService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private BuyerService buyerService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;


	@GetMapping("/home")
	public ModelAndView getAllProducts(Model model, Principal principal, HttpSession session) throws ResourceNotFoundException {
		ModelAndView modelAndView = new ModelAndView();
		session.setAttribute("username", "Admin");
		if (userService.hasRole("ROLE_BUYER")) {
			Buyer buyer = buyerService.findByUsername(principal.getName());
			Optional<Order> order = buyer.getOrders().stream()
										   .filter(ord -> ord.getStatus() == Status.PREPARED)
										   .findFirst();
			model.addAttribute("cartCount", order.orElse(new Order()).getItems().size());
			model.addAttribute("productList", productService.findAll());
			List<Seller> sellers = sellerService.findSellersByBuyerId(buyer.getUserId());
			model.addAttribute("sellerList",sellers);
			session.setAttribute("username", buyer.getFirstName() + " " + buyer.getLastName());
		} else if (userService.hasRole("ROLE_SELLER")) {
			Seller seller = sellerService.findSeller(principal.getName());
			model.addAttribute("productList", seller.getProducts());
			model.addAttribute("seller",sellerService.findSeller(principal.getName()));
			session.setAttribute("username", seller.getFirstName() + " " + seller.getLastName());
		}
		modelAndView.addObject("searchMessage", new SearchMessage());


		
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
		String status = (String) model.asMap().get("status");
		model.addAttribute("tab", tab);
		model.addAttribute("status", status);
		modelAndView.setViewName("home");
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
	public String getProductByName(@ModelAttribute SearchMessage searchMessage, Principal principal, Model model) throws ResourceNotFoundException {
		if (userService.hasRole("ROLE_BUYER")) {
			model.addAttribute("productList", productService.searchByName(searchMessage.getSearch()));
		} else if (userService.hasRole("ROLE_SELLER")) {
			Seller seller = sellerService.findSeller(principal.getName());
			List<Product> productList = sellerService.searchByName(searchMessage.getSearch(), seller.getUserId());
			model.addAttribute("productList", productList);
			model.addAttribute("seller",sellerService.findSeller(principal.getName()));
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
