package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Review;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;

import java.util.List;

public interface ReviewService {

    List<Review> findUnApprovedReview();
    public Review save(Review review);
    Review findReviewById (Long id) throws ResourceNotFoundException;
}
