package com.miu.onlinemarket.service;

import java.util.List;

import com.miu.onlinemarket.domain.Review;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;

public interface ReviewService {

    List<Review> findUnApprovedReview();
    public Review save(Review review);
    Review findReviewById (Long id) throws ResourceNotFoundException;
}
