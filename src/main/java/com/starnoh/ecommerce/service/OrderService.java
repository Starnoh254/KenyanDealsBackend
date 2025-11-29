package com.starnoh.ecommerce.service;

import com.starnoh.ecommerce.dto.CreateOrderRequest;
import com.starnoh.ecommerce.dto.CreateOrderResponse;
import com.starnoh.ecommerce.dto.OrderDto;
import com.starnoh.ecommerce.entity.Orders;

import java.util.List;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);
    OrderDto getOrder(Long id);
    List<OrderDto> getAllOrders();
}
