package com.miu.onlinemarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.miu.onlinemarket.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	@Query("select s from Review s where s.reviewStatus = false")
	List<Review> findUnApproved();
}
