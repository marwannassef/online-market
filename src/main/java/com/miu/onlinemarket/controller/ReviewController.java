package com.miu.onlinemarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miu.onlinemarket.domain.Review;
import com.miu.onlinemarket.service.ReviewService;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/approveReview")
	public String approveReview(@RequestParam("id") Long id, Model model) {
		Review review = reviewService.findReviewById(id).orElse(null);
		review.setReviewStatus(true);
		reviewService.save(review);
		return "redirect:/home";
	}

}
