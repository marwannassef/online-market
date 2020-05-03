package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
