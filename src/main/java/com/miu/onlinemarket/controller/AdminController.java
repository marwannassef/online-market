package com.miu.onlinemarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miu.onlinemarket.domain.Review;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.service.ReviewService;
import com.miu.onlinemarket.service.SellerService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private SellerService sellerService;

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/approveSeller")
	public String approveSeller(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("tab", "2");
		Seller seller = sellerService.findSellerById(id).orElse(null);
		seller.setApproved(true);
		sellerService.update(seller);
		return "redirect:/home";
	}

	@GetMapping("/approveReview")
	public String approveReview(@RequestParam("id") Long id, Model model) {
		Review review = reviewService.findReviewById(id).orElse(null);
		review.setReviewStatus(true);
		reviewService.save(review);
		return "redirect:/home";
	}

}
