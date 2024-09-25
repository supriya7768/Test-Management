package com.bnt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bnt.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    List<Category> findByName(@Param("categoryName") String categoryName);

    boolean existsByCategoryName(String categoryName);

    Optional<Category> findByCategoryName(String categoryName);
}