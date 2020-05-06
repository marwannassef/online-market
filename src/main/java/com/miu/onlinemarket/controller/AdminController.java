package com.miu.onlinemarket.controller;


import com.miu.onlinemarket.domain.Review;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.service.ReviewService;
import com.miu.onlinemarket.service.SellerService;
import com.miu.onlinemarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/seller")
    public String getAllUnapprovedSellers(Model model) {
            model.addAttribute("sellerList", sellerService.findUnApprovedSeller());

        return "approve-seller";
    }

    @GetMapping("/approveSeller")
    public RedirectView approveSeller(@RequestParam("id")Long id,Model model) {

      Seller seller = sellerService.findSellerById(id).orElse(null);
      seller.setApproved(true);
      sellerService.save(seller);
        return new RedirectView("seller");
    }

    @GetMapping("/review")
    public String getAllUnApprovedReviews(Model model) {
        model.addAttribute("reviewList", reviewService.findUnApprovedReview());

        return "approve-review";
    }

    @GetMapping("/approveReview")
    public RedirectView approveReview(@RequestParam("id")Long id,Model model) {

        Review review = reviewService.findReviewById(id).orElse(null);
        review.setReviewStatus(true);
        reviewService.save(review);
        return new RedirectView("review");
    }
}
