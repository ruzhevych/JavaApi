package org.example.controller;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.entites.CategoryEntity;
import org.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // Дозволяє доступ з React
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getList();
    }

    @GetMapping("/{id}")
    public CategoryEntity getCategoryById(@PathVariable int id) {
        return categoryService.getById(id);
    }

    @PostMapping
    public CategoryEntity create(@RequestBody CategoryCreateDTO dto) {
        return categoryService.create(dto);
    }

    @PutMapping
    public CategoryEntity edit(@RequestBody CategoryEditDTO dto) {
        return categoryService.edit(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        categoryService.delete(id);
    }

}