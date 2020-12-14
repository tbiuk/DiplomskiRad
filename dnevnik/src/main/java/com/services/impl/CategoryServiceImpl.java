package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.api.CategoryDao;
import com.domain.Category;
import com.services.api.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	CategoryDao CategoryDao;

    @Autowired
    public CategoryServiceImpl(CategoryDao CategoryDao) {
        this.CategoryDao = CategoryDao;
    }
	
	@Override
    public List<Category> getAllCategories(){
        //return CategoryDao.getAllCategories();
		return CategoryDao.findAll();
    }
	
	@Override
	public void saveNewCategory(Category category) {
		//CategoryDao.saveNewCategory(category);
		CategoryDao.save(category);
	}
	
	@Override
	public void deleteCategory(Integer categoryId) {
		//CategoryDao.saveNewCategory(category);
		CategoryDao.deleteById(categoryId);
	}
	
	@Override
	public Category getCategoryById(Integer categoryId) {
		//return CategoryDao.getCategoryById(categoryId);
		return CategoryDao.findById(categoryId).get();
	}

}