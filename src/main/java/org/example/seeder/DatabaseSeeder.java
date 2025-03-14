package org.example.seeder;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.entites.CategoryEntity;
import org.example.entites.ProductEntity;
import org.example.entites.ProductImageEntity;
import org.example.repository.ICategoryRepository;
import org.example.repository.IProductImageRepository;
import org.example.repository.IProductRepository;
import org.example.service.FileService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final ICategoryRepository categoryRepository;
    private final IProductRepository productRepository;
    private final IProductImageRepository productImageRepository;
    private final FileService fileService;

    private final UserSeeder userSeeder;
    private final RoleSeeder roleSeeder;


    @PostConstruct
    public void seed() {

        roleSeeder.seed();
        userSeeder.seed();
        if (categoryRepository.count() == 0) {

            var electronics = fileService.load("https://www.recyclezone.org.uk/wp-content/uploads/2020/11/dispose-of-electronics-feature.jpg");
            var clothing = fileService.load("https://designrelated.com/wp-content/uploads/2024/01/image-1.jpeg");
            var books = fileService.load("https://collegeinfogeek.com/wp-content/uploads/2018/11/Essential-Books.jpg");
            var home_kitchen = fileService.load("https://cliffsliving.com/wp-content/uploads/2023/02/CLF-Mountain-Kitchen-e1596209483747.jpg");
            var toys = fileService.load("https://perfectcolourants.com/wp-content/uploads/2023/10/Toy.webp");

            List<CategoryEntity> categories = List.of(
                    createCategory("Electronics", electronics, "Devices and gadgets"),
                    createCategory("Clothing", clothing, "Apparel for men and women"),
                    createCategory("Books", books, "Various genres of books"),
                    createCategory("Home & Kitchen", home_kitchen, "Household essentials"),
                    createCategory("Toys", toys, "Toys and games for kids")
            );
            categoryRepository.saveAll(categories);
        }
        if (productRepository.count() == 0) {
            seedProducts();
        }
    }

    private CategoryEntity createCategory(String name, String image, String description) {
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setImage(image);
        category.setDescription(description);
        category.setCreationTime(LocalDateTime.now());
        return category;
    }

    private void seedProducts() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        if (categories.isEmpty()) return;
        ProductEntity laptop = createProduct("Laptop", "High-performance laptop", 1200.0, categories.get(0));
        ProductEntity shirt = createProduct("T-Shirt", "Cotton t-shirt", 20.0, categories.get(1));
        ProductEntity novel = createProduct("Fantasy Novel", "Bestselling fantasy novel", 15.0, categories.get(2));
        productRepository.saveAll(List.of(laptop, shirt, novel));
        seedProductImages(laptop, List.of("laptop1.jpg", "laptop2.jpg"));
        seedProductImages(shirt, List.of("shirt1.jpg", "shirt2.jpg"));
        seedProductImages(novel, List.of("novel1.jpg"));
    }
    private ProductEntity createProduct(String name, String description, Double price, CategoryEntity category) {
        ProductEntity product = new ProductEntity();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(category);
        product.setCreationTime(LocalDateTime.now());
        return product;
    }
    private void seedProductImages(ProductEntity product, List<String> imageUrls) {
        List<ProductImageEntity> images = imageUrls.stream()
                .map(url -> createProductImage(url, product))
                .toList();
        productImageRepository.saveAll(images);
    }
    private ProductImageEntity createProductImage(String imageUrl, ProductEntity product) {
        ProductImageEntity image = new ProductImageEntity();
        image.setName(imageUrl);
        image.setPriority(1);
        image.setProduct(product);
        return image;
    }
}