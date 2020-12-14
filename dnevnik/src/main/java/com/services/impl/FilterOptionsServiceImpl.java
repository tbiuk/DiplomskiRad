package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.api.ActivityTypeDao;
import com.dao.api.CategoryGroupDao;
import com.dao.api.FilterOptionsDao;
import com.domain.FilterOptions;
import com.domain.Priority;
import com.services.api.FilterOptionsService;


@Service
public class FilterOptionsServiceImpl implements FilterOptionsService {
	
	
	FilterOptionsDao filterOptionsDao;
	ActivityTypeDao activityTypeDao;

    @Autowired
    public FilterOptionsServiceImpl(FilterOptionsDao filterOptionsDao, ActivityTypeDao activityTypeDao) {
        this.filterOptionsDao = filterOptionsDao;
        this.activityTypeDao = activityTypeDao;
    }
	
	@Override
    public FilterOptions getFilterOptions(){
		return filterOptionsDao.findById(1).get();
    }
	
	@Override
    public void UpdateFilterOptions(FilterOptions filterOptions){
		if (filterOptions.tempActivityTypeId == null || filterOptions.tempActivityTypeId == 0)
			filterOptions.chosenActivityType = null;
		else
			filterOptions.chosenActivityType = activityTypeDao.findById(filterOptions.tempActivityTypeId).get();
		
		filterOptionsDao.save(filterOptions);
    }

}