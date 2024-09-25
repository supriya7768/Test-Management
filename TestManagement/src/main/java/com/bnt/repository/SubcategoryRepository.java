package com.bnt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bnt.model.Subcategory;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    @Query("SELECT c FROM Subcategory c WHERE c.subcategoryName = :subcategoryName")
    List<Subcategory> findByName(@Param("subcategoryName") String subcategoryName);

    Optional<Subcategory> findBySubcategoryName(String subcategoryName);

    
}
