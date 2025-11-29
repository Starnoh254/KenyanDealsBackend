package com.starnoh.ecommerce.dto;

import com.starnoh.ecommerce.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private OrderStatus status;
    private double totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items;
}
