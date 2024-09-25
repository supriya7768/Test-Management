package com.bnt.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.engine.internal.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.bnt.exception.CategoryAlreadyPresentException;
import com.bnt.exception.CategoryNotFoundException;
import com.bnt.model.Category;
import com.bnt.repository.CategoryRepository;
import com.bnt.service.serviceImpl.CategoryServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

     @Test
    void testCreatecategory(){
         Category expectedResult = new Category(1, "Java", "Java Framework Category");
        when(categoryRepository.save(expectedResult)).thenReturn(expectedResult);
        Category actualResult = categoryService.createCategory(expectedResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllcategory(){
        List<Category> category = new ArrayList();
        category.add(new Category(1, "Java", "Java Framework Category"));
        category.add(new Category(1, "Java", "Java Framework Category"));
        when(categoryRepository.findAll()).thenReturn(category);
        List<Category> actualResult = categoryService.getCategory();
        assertEquals(category, actualResult);
    }

    @Test
    void testUpdateCategory() {
        Category existingCategory = new Category(1, "Java", "Java Framework Category");
        Category updatedCategory = new Category(1, "Updated Java", "Updated Java Framework Category");
        when(categoryRepository.findById(1)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(updatedCategory);
        Category actualResult = categoryService.updateCategory(existingCategory);
        assertEquals(updatedCategory, actualResult);
        verify(categoryRepository).findById(1);
        verify(categoryRepository).save(existingCategory);
    }

    @Test
    void testDeleteCategory() {
        int categoryId = 1;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(new Category(categoryId, "Java", "Java Framework Category")));
        categoryService.deleteCategory(categoryId);
        verify(categoryRepository).deleteById(categoryId);
    }


    @Test
    void testCreateCategoryAlreadyExists() {
        Category existingCategory = new Category(1, "Java", "Java Framework Category");
        List<Category> existingCategories = new ArrayList<>();
        existingCategories.add(existingCategory);
        when(categoryRepository.findByName("Java")).thenReturn(existingCategories);
        Category newCategory = new Category(2, "Java", "Another Java Framework");
        CategoryAlreadyPresentException exception = assertThrows(CategoryAlreadyPresentException.class, () -> {
            categoryService.createCategory(newCategory);
        });
        verify(categoryRepository, never()).save(any());
        assertEquals("Category with name Java is already present", exception.getMessage());
    }

  @Test
    void testGetAllcategoryNegative() {
        List<Category> category = new ArrayList<>();
        category.add(new Category(1, "Java", "Java Framework Category"));
        category.add(new Category(2, "Python", "Python Framework Category"));
        when(categoryRepository.findAll()).thenReturn(new ArrayList<>());
        List<Category> actualResult = categoryService.getCategory();
        assertNotNull(actualResult);
        assertTrue(actualResult.isEmpty(), "Expected an empty list");
        assertFalse(actualResult.contains(category.get(0)), "Category should not be present");
        assertFalse(actualResult.contains(category.get(1)), "Category should not be present");
    }



    @Test
    void testUpdateCategoryNotFound() {
    Category nonExistingCategory = new Category(1, "Non Existing Category", "Non Existing Description");
    when(categoryRepository.findById(nonExistingCategory.getCategoryId())).thenReturn(Optional.empty());
    assertThrows(CategoryNotFoundException.class, () -> {
        categoryService.updateCategory(nonExistingCategory);
    });
    verify(categoryRepository, never()).save(any());
}

   @Test
    void testDeleteCategoryNotFound() {
    int nonExistingCategoryId = 1;
    when(categoryRepository.findById(nonExistingCategoryId)).thenReturn(Optional.empty());
    assertThrows(CategoryNotFoundException.class, () -> {
        categoryService.deleteCategory(nonExistingCategoryId);
    });
    verify(categoryRepository, never()).deleteById(nonExistingCategoryId);
}

    
    
}
