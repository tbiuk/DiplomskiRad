package com.services.api;

import com.domain.Category;
import com.domain.CategoryGroup;

import java.util.List;

public interface CategoryGroupService {

	List<CategoryGroup> getAllCategoryGroups();
	public CategoryGroup get(Integer id);
	public List<CategoryGroup> getPrimaryCategoryGroups();
	public List<CategoryGroup> getSecondaryCategoryGroups();
	void saveNewCategoryGroup(CategoryGroup newCategoryGroup);
	void deleteCategoryGroup(Integer categoryGroupId);

}
