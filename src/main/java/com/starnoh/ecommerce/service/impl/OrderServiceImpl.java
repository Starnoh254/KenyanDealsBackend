package com.starnoh.ecommerce.service.impl;

import com.starnoh.ecommerce.dto.CreateOrderRequest;
import com.starnoh.ecommerce.dto.CreateOrderResponse;
import com.starnoh.ecommerce.dto.OrderDto;
import com.starnoh.ecommerce.dto.OrderItemRequest;
import com.starnoh.ecommerce.entity.OrderItems;
import com.starnoh.ecommerce.entity.Orders;
import com.starnoh.ecommerce.entity.Product;
import com.starnoh.ecommerce.repository.OrderItemRepository;
import com.starnoh.ecommerce.repository.OrderRepository;
import com.starnoh.ecommerce.repository.ProductRepository;
import com.starnoh.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            ProductRepository productRepository

    ){
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }


    @Override
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        Orders order = new Orders();
        order.setUser_id(request.getUserId());
        order.setTotal_amount(0.0f);
        order = orderRepository.save(order);

        float total = 0.0f;

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            float subtotal = product.getPrice() * itemReq.getQuantity();
            total += subtotal;


            OrderItems orderItem = new OrderItems();
            orderItem.setOrder_id(order);
            orderItem.setProduct_id(product);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice_at_order(product.getPrice());

            orderItemRepository.save(orderItem);
        }

        order.setTotal_amount(total);
        orderRepository.save(order);

        return new CreateOrderResponse(order.getId(), total);

    }

    @Override
    public OrderDto getOrder(Long id) {
        Orders order =  orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return new OrderDto(
                order.getId(),
                order.getStatus(),
                order.getTotal_amount(),
                order.getCreated_at(),
                null // For simplicity, not mapping order items here
        );
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Orders> orders = orderRepository.findAll();
        return orders.stream().map(order -> new OrderDto(
                order.getId(),
                order.getStatus(),
                order.getTotal_amount(),
                order.getCreated_at(),
                null // For simplicity, not mapping order items here
        )).toList();
    }
}
