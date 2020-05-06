package com.miu.onlinemarket.repository;

import com.miu.onlinemarket.domain.Review;
import com.miu.onlinemarket.domain.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select s from Review s where s.reviewStatus = false")
    List<Review> findUnApproved();
}
