package com.miu.onlinemarket.controller;

import com.miu.onlinemarket.domain.*;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import com.miu.onlinemarket.service.BuyerService;
import com.miu.onlinemarket.service.ItemService;
import com.miu.onlinemarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miu.onlinemarket.service.ReviewService;

import java.security.Principal;
import java.util.List;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private ProductService productService;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private ItemService itemService;

	@GetMapping("/approveReview")
	public String approveReview(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
		Review review = reviewService.findReviewById(id);
		review.setReviewStatus(true);
		reviewService.save(review);
		redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/home";
	}

	@GetMapping("/addReview")
	public String saveReview(@RequestParam("itemId") Long id,@RequestParam("review") String comment, @RequestParam("productId") Long productId,Principal principal, RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
		Review review = new Review();
		Buyer buyer = buyerService.findByUsername(principal.getName());
		review.setReview(comment);
		review.setBuyer(buyer);
		reviewService.save(review);
		Product product = productService.findById(productId);
		product.addReview(review);
		productService.save(product);
		Item item=itemService.findItem(id);
		item.setReviewStatus(true);
		itemService.save(item);
		redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/orders";
	}
}
