package com.miu.onlinemarket.service.impl;


import com.miu.onlinemarket.domain.Review;
import com.miu.onlinemarket.repository.ReviewRepository;
import com.miu.onlinemarket.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImp implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> findUnApprovedReview() {
        return reviewRepository.findUnApproved();
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> findReviewById(Long id) {
        return reviewRepository.findById(id);
    }
}
