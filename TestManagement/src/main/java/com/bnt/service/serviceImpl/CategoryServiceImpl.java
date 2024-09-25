package com.bnt.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnt.exception.CategoryAlreadyPresentException;
import com.bnt.exception.CategoryNotFoundException;
import com.bnt.model.Category;
import com.bnt.repository.CategoryRepository;
import com.bnt.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        String categoryName = category.getCategoryName();
        List<Category> existingCategories = categoryRepository.findByName(categoryName);
        if (!existingCategories.isEmpty()) {
            throw new CategoryAlreadyPresentException("Category with name " + categoryName + " is already present");
        }
        log.info("Category is created");
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getCategory() {
        try{
            log.info("All category are retrieved");
            return categoryRepository.findAll();
        }catch(Exception ex){
            throw new CategoryNotFoundException("Category table is empty");
        }    
    }

    @Override
    public Category updateCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(category.getCategoryId());
        if (!existingCategory.isPresent()) {
            throw new CategoryNotFoundException("Category not found with id : " + category.getCategoryId());
        }
        log.info("Category is updated");
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int categoryId) {
        Optional<Category> existingCategory = categoryRepository.findById(categoryId);
        if (!existingCategory.isPresent()) {
            throw new CategoryNotFoundException("Category not found with id : " + categoryId);
        }
        log.info("Category with id{} is deleted", categoryId);
        categoryRepository.deleteById(categoryId);
    }
}
