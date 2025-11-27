package com.starnoh.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItems {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id" , nullable = false)
    private Orders order_id;

    @ManyToOne
    @JoinColumn(name = "id" , nullable = false)
    private Product product_id;

    private int quantity;

    private Float price_at_order;
}
