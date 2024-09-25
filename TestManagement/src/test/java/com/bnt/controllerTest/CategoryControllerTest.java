package com.bnt.controllerTest;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bnt.controller.CategoryController;
import com.bnt.exception.CategoryAlreadyPresentException;
import com.bnt.exception.CategoryNotFoundException;
import com.bnt.model.Category;
import com.bnt.repository.CategoryRepository;
import com.bnt.service.serviceImpl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CategoryControllerTest {

    @Mock
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryController categoryController;

     @Test
    void testCreateCategory(){
        Category category = new Category(1, "Java", "Java Framework Category");
        ResponseEntity<Category> expectedResult = ResponseEntity.status(HttpStatus.CREATED).body(category);
        when(categoryService.createCategory(category)).thenReturn(category);
        ResponseEntity<Category> actualResult = categoryController.createCategory(category);
        assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(), actualResult.getBody());
    }

    @Test
    void testGetAllCategory(){
        List<Category> category = new ArrayList();
        category.add(new Category(1, "Java", "Java Framework Category"));
        category.add(new Category(1, "Java", "Java Framework Category"));
        ResponseEntity<List<Category>> expectedResult = ResponseEntity.status(HttpStatus.FOUND).body(category);
        when(categoryService.getCategory()).thenReturn(category);
        ResponseEntity<List<Category>> actualResult = categoryController.getCategory();
        assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(), actualResult.getBody());
    }

     @Test
    void testUpdateCategory(){
        Category category =new Category(1, "Java", "Java Framework Category");
        ResponseEntity <Category> expectedResult = ResponseEntity.status(HttpStatus.ACCEPTED).body(category);
        when(categoryService.updateCategory(category)).thenReturn(category);
        ResponseEntity <Category> actualResult = categoryController.updateCategory(1, "Java", "Java Framework Category");
        assertEquals(expectedResult.getStatusCode(), actualResult.getStatusCode());
        assertEquals(expectedResult.getBody(), actualResult.getBody());
    }

    @Test
    void testDeleteCategory(){
        ResponseEntity<String> expectedResult = categoryController.deleteCategory(3);
        verify(categoryService).deleteCategory(3);
        assertEquals(HttpStatus.OK, expectedResult.getStatusCode());
        assertEquals("Deleted category with id : 3", expectedResult.getBody());

    }


    @Test
    void testSaveCategoryAlreadyExists() throws CategoryAlreadyPresentException {
        Category existingCategory = new Category();
        existingCategory.setCategoryName("Existing Category");
        Category newCategory = new Category();
        newCategory.setCategoryName("Existing Category");
        doThrow(new CategoryAlreadyPresentException("Category already exists")).when(categoryService).createCategory(newCategory);
        assertThrows(CategoryAlreadyPresentException.class, () -> {
            categoryController.createCategory(newCategory);
        });
        verify(categoryService, times(1)).createCategory(newCategory);
    }

    @Test
    void testGetCategoryNotFound() throws CategoryNotFoundException {
        doThrow(new CategoryNotFoundException("No categories found")).when(categoryService).getCategory();
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryController.getCategory();
        });
        verify(categoryService, times(1)).getCategory();
    }

    @Test
    void testUpdateCategoryNotFound() throws CategoryNotFoundException {
        Category nonExistingCategory = new Category();
        nonExistingCategory.setCategoryId(1);
        nonExistingCategory.setCategoryName("Non Existing Category");
        doThrow(new CategoryNotFoundException("Category not found")).when(categoryService).updateCategory(nonExistingCategory);
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryController.updateCategory(nonExistingCategory.getCategoryId(), nonExistingCategory.getCategoryName(), nonExistingCategory.getCategoryDescription());
        });
        verify(categoryService, times(1)).updateCategory(nonExistingCategory);
    }

    @Test
    void testDeleteCategoryNotFound() throws CategoryNotFoundException {
        int nonExistingCategoryId = 1;
        doThrow(new CategoryNotFoundException("Category not found")).when(categoryService).deleteCategory(nonExistingCategoryId);
        assertThrows(CategoryNotFoundException.class, () -> {
            categoryController.deleteCategory(nonExistingCategoryId);
        });
        verify(categoryService, times(1)).deleteCategory(nonExistingCategoryId);
    }
    
}
