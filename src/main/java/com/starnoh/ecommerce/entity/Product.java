package com.starnoh.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Float price;
    private int stock;

    @OneToMany(mappedBy = "product")
    private List<OrderItems> orderItems;

    @OneToOne
    @JoinColumn(name = "id" , nullable = false)
    private Category category_id;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;


}
