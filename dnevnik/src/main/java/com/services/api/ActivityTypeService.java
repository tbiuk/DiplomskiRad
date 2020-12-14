package com.services.api;

import com.domain.ActivityType;

import java.util.List;

public interface ActivityTypeService {

	List<ActivityType> getAllActivityTypes();
	public void save(ActivityType activity);
	public ActivityType get(Integer id);
	public void delete(Integer id);
	public void updateActivityType(ActivityType activityType);

}