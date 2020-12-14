package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.api.CategoryDao;
import com.dao.api.CategoryGroupDao;
import com.domain.Activity;
import com.domain.Category;
import com.domain.CategoryGroup;
import com.services.api.CategoryGroupService;

@Service
public class CategoryGroupServiceImpl implements CategoryGroupService {
	
	@Autowired
	CategoryGroupDao CategoryGroupDao;

    
	@Override
	public List<CategoryGroup> getAllCategoryGroups() {
		return CategoryGroupDao.findAll();
	}
	
	@Override
	public List<CategoryGroup> getPrimaryCategoryGroups() {
		return CategoryGroupDao.findPrimaryCategoryGroups();
	}
	
	@Override
	public List<CategoryGroup> getSecondaryCategoryGroups() {
		return CategoryGroupDao.findSecondaryCategoryGroups();
	}
	
	@Override
	public CategoryGroup get(Integer id){
        return CategoryGroupDao.findById(id).get();
	}
	
	@Override
	public void saveNewCategoryGroup(CategoryGroup categoryGroup) {
		CategoryGroupDao.save(categoryGroup);
	}
	
	@Override
	public void deleteCategoryGroup(Integer categoryGroupId) {
		CategoryGroupDao.deleteById(categoryGroupId);
	}
	

}