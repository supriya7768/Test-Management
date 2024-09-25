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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.bnt.exception.CategoryAlreadyPresentException;
import com.bnt.exception.CategoryNotFoundException;
import com.bnt.exception.SubcategoryAlreadyPresentException;
import com.bnt.exception.SubcategoryNotFoundException;
import com.bnt.model.Category;
import com.bnt.model.Subcategory;
import com.bnt.repository.SubcategoryRepository;
import com.bnt.service.serviceImpl.SubcategoryServiceImpl;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SubcategoryServiceTest {
    
    @Mock
    SubcategoryRepository subcategoryRepository;

    @InjectMocks
    SubcategoryServiceImpl subcategoryService;

     @Test
    void testCreatecategory(){
         Subcategory expectedResult = new Subcategory(1, "Annotation", "Annotation in Spring", new Category(1, "Springboot", "Springboot Framework"));
        when(subcategoryRepository.save(expectedResult)).thenReturn(expectedResult);
        Subcategory actualResult = subcategoryService.createSubcategory(expectedResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetAllcategory(){
        List<Subcategory> subcategory = new ArrayList();
        subcategory.add(new Subcategory(1, "Annotation", "Annotation in Spring", new Category(1, "Springboot", "Springboot Framework")));
        subcategory.add(new Subcategory(1, "Annotation", "Annotation in Spring", new Category(1, "Springboot", "Springboot Framework")));
        when(subcategoryRepository.findAll()).thenReturn(subcategory);
        List<Subcategory> actualResult = subcategoryService.getSubcategory();
        assertEquals(subcategory, actualResult);
    }

    @Test
    void testUpdateSubcategory_SuccessfulUpdate() {
        Subcategory existingSubcategory = new Subcategory(1, "Annotation", "Annotation in Spring", new Category(1, "Springboot", "Springboot Framework"));
        Subcategory updatedSubcategory = new Subcategory(1, "Updated Annotation", "Updated Annotation in Spring", new Category(1, "Springboot", "Springboot Framework"));
        when(subcategoryRepository.findById(1)).thenReturn(Optional.of(existingSubcategory));
        when(subcategoryRepository.save(updatedSubcategory)).thenReturn(updatedSubcategory);
        Subcategory actualResult = subcategoryService.updateSubcategory(1, updatedSubcategory);
        assertEquals(updatedSubcategory, actualResult);
        verify(subcategoryRepository).findById(1);
        verify(subcategoryRepository).save(updatedSubcategory); // Verify save with updatedSubcategory
    }


    @Test
    void testDeleteCategory(){
        subcategoryService.deleteSubcategory(1);
        verify(subcategoryRepository).deleteById(1);
    }


      @Test
    void testCreateSubcategoryAlreadyExists() {
        Subcategory existingSubcategory = new Subcategory(1, "Annotation", "Existing Annotation", new Category(1, "Springboot", "Springboot Framework"));
        List<Subcategory> existingSubcategories = new ArrayList<>();
        existingSubcategories.add(existingSubcategory);
        when(subcategoryRepository.findByName("Annotation")).thenReturn(existingSubcategories);
        Subcategory newSubcategory = new Subcategory(2, "Annotation", "Another Annotation", new Category(1, "Springboot", "Springboot Framework"));
        SubcategoryAlreadyPresentException exception = assertThrows(SubcategoryAlreadyPresentException.class, () -> {
            subcategoryService.createSubcategory(newSubcategory);
        });
        verify(subcategoryRepository, never()).save(any());
        assertEquals("Subcategory with name Annotation already present", exception.getMessage());
    }

    @Test
    void testGetAllsubcategoryNegative() {
        List<Subcategory> subcategory = new ArrayList<>();
        subcategory.add(new Subcategory(1, "Annotation", "Existing Annotation", new Category(1, "Springboot", "Springboot Framework")));
        subcategory.add(new Subcategory(1, "Annotation", "Existing Annotation", new Category(1, "Springboot", "Springboot Framework")));
        when(subcategoryRepository.findAll()).thenReturn(new ArrayList<>());
        List<Subcategory> actualResult = subcategoryService.getSubcategory();
        assertNotNull(actualResult);
        assertTrue(actualResult.isEmpty(), "Expected an empty list");
        assertFalse(actualResult.contains(subcategory.get(0)), "Subcategory should not be present");
        assertFalse(actualResult.contains(subcategory.get(1)), "Subcategory should not be present");
    }

    @Test
    void testUpdateSubcategoryNotFound() {
    Subcategory nonExistingSubcategory =new Subcategory(1, "Annotation", "Existing Annotation", new Category(1, "Springboot", "Springboot Framework"));
    when(subcategoryRepository.findById(nonExistingSubcategory.getSubcategoryId())).thenReturn(Optional.empty());
    assertThrows(SubcategoryNotFoundException.class, () -> {
        subcategoryService.updateSubcategory(1,nonExistingSubcategory);
    });
    verify(subcategoryRepository, never()).save(any());
}


  @Test
    void testDeleteSubcategoryNotFound() {
    int nonExistingSubcategoryId = 1;
    when(subcategoryRepository.findById(nonExistingSubcategoryId)).thenReturn(Optional.empty());
    assertThrows(SubcategoryNotFoundException.class, () -> {
        subcategoryService.deleteSubcategory(nonExistingSubcategoryId);
    });
    verify(subcategoryRepository, never()).deleteById(nonExistingSubcategoryId);
}
}
