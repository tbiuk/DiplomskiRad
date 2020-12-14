package com.services.api;

import com.domain.Category;

import java.util.List;

public interface CategoryService {

	List<Category> getAllCategories();
    void saveNewCategory(Category newCategory);
    void deleteCategory(Integer categoryId);
    Category getCategoryById(Integer activityId);

}
