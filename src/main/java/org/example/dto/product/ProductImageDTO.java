package org.example.dto.product;

import lombok.Data;

@Data
public class ProductImageDTO {
    private Long id;
    private String name;
    private int priority;
    private Integer productId;
}