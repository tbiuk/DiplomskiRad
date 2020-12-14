package com.services.api;

import com.domain.Activity;
import com.domain.FilterOptions;

import java.util.List;

public interface ActivityService {

	List<Activity> getAllActivities();
	List<Activity> getFilteredActivities(FilterOptions filterOptions);
	public void save(Activity activity);
	public Activity get(Integer id);
	public void delete(Integer id);
	public boolean updateActivity(Activity activity);
	public void deleteActivity(Integer activityId);

}
