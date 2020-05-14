package com.miu.onlinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.onlinemarket.domain.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
