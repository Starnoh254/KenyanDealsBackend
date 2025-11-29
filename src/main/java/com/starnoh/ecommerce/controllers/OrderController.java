package com.starnoh.ecommerce.controllers;

import com.starnoh.ecommerce.dto.CreateOrderRequest;
import com.starnoh.ecommerce.dto.CreateOrderResponse;
import com.starnoh.ecommerce.dto.OrderDto;
import com.starnoh.ecommerce.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    // 1 . CREATE an order
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestBody CreateOrderRequest orderRequestDTO
            ){
        CreateOrderResponse response = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(response);
    }

    // 2. GET order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        OrderDto order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    // 3. GET all orders
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
}
