package com.services.api;

import com.domain.Priority;

import java.util.List;

public interface PriorityService {

	List<Priority> getAllPriorities();
	void saveNewPriority(Priority priority);
	void deletePriority(Integer priorityId);
	void increasePriority(Integer priorityValue);
	void decreasePriority(Integer priorityValue);
}
