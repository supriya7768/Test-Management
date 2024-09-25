package com.bnt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bnt.model.Category;
import com.bnt.service.serviceImpl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryServiceImpl categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        logger.info("Category create request is received");
        Category createCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategory(){
        logger.info("List of all category is to be retrieved");
        List<Category> getCategory = categoryService.getCategory();
        return ResponseEntity.status(HttpStatus.FOUND).body(getCategory);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable int categoryId, @RequestParam String categoryName, @RequestParam String categoryDescription ){
        logger.info("Category with id {} is to be updated", categoryId);
        Category category = new Category(categoryId, categoryName, categoryDescription);
        Category updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedCategory);
    } 
    
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable int categoryId){
        logger.info("Category with id {} is to be deleted", categoryId);
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Deleted category with id : " + categoryId);
    }
}
