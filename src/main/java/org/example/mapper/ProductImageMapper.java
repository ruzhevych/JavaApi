package org.example.mapper;

import org.example.dto.product.ProductImageDTO;
import org.example.entites.ProductImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    @Mapping(target = "productId", source = "product.id")
    ProductImageDTO toDto(ProductImageEntity image);
    List<ProductImageDTO> toDto(Iterable<ProductImageEntity> product);
}