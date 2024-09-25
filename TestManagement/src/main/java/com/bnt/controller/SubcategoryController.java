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
import org.springframework.web.bind.annotation.RestController;

import com.bnt.model.Subcategory;
import com.bnt.service.serviceImpl.SubcategoryServiceImpl;

@RestController
@RequestMapping("/api/subcategory")
public class SubcategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

@Autowired
private SubcategoryServiceImpl subcategoryService;

    @PostMapping
    public ResponseEntity<Subcategory> createSubcategory(@RequestBody Subcategory subcategory){
        logger.info("Creating subcategory in database");
        Subcategory createSubcategory = subcategoryService.createSubcategory(subcategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createSubcategory);  
    }

    @GetMapping
    public ResponseEntity<List<Subcategory>> getSubcategory(){
        logger.info("List of all subcategory to be received");
        List<Subcategory> getSubcategory = subcategoryService.getSubcategory();
        return ResponseEntity.status(HttpStatus.FOUND).body(getSubcategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subcategory> updateSubCategory(@PathVariable("id") int subcategoryId, @RequestBody Subcategory subcategory) {
        logger.info("Updating subcategory with id: {}", subcategoryId);
        Subcategory updateSubcategory =  subcategoryService.updateSubcategory(subcategoryId, subcategory);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(updateSubcategory);
    } 

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubcategory(@PathVariable("id") int subcategoryId){
        logger.info("Subcategory with id {} is to be deleted", subcategoryId);
        subcategoryService.deleteSubcategory(subcategoryId);
        return ResponseEntity.ok("Deleted subcategory with id : " + subcategoryId);
    }
    
}
