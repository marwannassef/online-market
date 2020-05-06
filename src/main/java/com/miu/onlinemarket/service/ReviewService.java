package com.miu.onlinemarket.service;

import com.miu.onlinemarket.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> findUnApprovedReview();
    public Review save(Review review);
    Optional<Review> findReviewById (Long id);
}
