package com.starnoh.ecommerce.repository;

import com.starnoh.ecommerce.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders , Long> {

    List<Orders> findByUserId(Long user_id);
}
