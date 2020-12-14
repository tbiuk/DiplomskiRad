package com.services.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.api.ActivityTypeDao;
import com.dao.api.CategoryGroupDao;
import com.domain.ActivityType;
import com.domain.CategoryGroup;
import com.services.api.ActivityTypeService;

@Service
public class ActivityTypeServiceImpl implements ActivityTypeService {
	
	ActivityTypeDao activityTypeDao;
	CategoryGroupDao categoryGroupDao;

    @Autowired
    public ActivityTypeServiceImpl(ActivityTypeDao activityTypeDao, CategoryGroupDao categoryGroupDao) {
        this.activityTypeDao = activityTypeDao;
        this.categoryGroupDao = categoryGroupDao;
    }
	
	@Override
    public List<ActivityType> getAllActivityTypes(){
        return activityTypeDao.findAll();
    }
	
	@Override
	public void save(ActivityType activityType){
		activityTypeDao.save(activityType);
	}
	
	public ActivityType get(Integer id){
        return activityTypeDao.findById(id).get();
	}
	
	public void delete(Integer id){
		activityTypeDao.deleteById(id);
	}
	
	public void updateActivityType(ActivityType activityType){
		
		if (activityType.isPrimaryCategoryGroupSelected() == false)
			activityType.primaryCategoryGroup = null;
		else
			activityType.primaryCategoryGroup = findCategoryGroup(activityType.tempPrimaryCategoryGroupId);
		
		if (activityType.isSecondaryCategoryGroupSelected() == false)
			activityType.secondaryCategoryGroup = null;
		else
			activityType.secondaryCategoryGroup = findCategoryGroup(activityType.tempSecondaryCategoryGroupId);
		
		
		
		activityTypeDao.save(activityType);
	}
	
	private CategoryGroup findCategoryGroup(Integer id) {
		return categoryGroupDao.findById(id).get();
	}
	
	
	
}