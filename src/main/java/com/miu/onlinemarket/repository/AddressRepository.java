package com.miu.onlinemarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miu.onlinemarket.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
