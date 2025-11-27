package com.starnoh.ecommerce.repository;

import com.starnoh.ecommerce.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

    List<OrderItems> findByOrderId(Long orderId);
}
