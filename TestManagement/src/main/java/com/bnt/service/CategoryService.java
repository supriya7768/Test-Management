package com.bnt.service;

import java.util.List;

import com.bnt.model.Category;

public interface CategoryService {

    public Category createCategory(Category category) ;

    public List<Category> getCategory();

    public Category updateCategory(Category category);

    public void deleteCategory(int categoryId);
}