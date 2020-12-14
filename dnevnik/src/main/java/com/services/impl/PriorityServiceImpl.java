package com.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.api.PriorityDao;
import com.dao.api.ActivityDao;
import com.domain.Priority;
import com.services.api.PriorityService;

@Service
public class PriorityServiceImpl implements PriorityService {
	
	@Autowired
	PriorityDao PriorityDao;
	
	@Autowired
	ActivityDao ActivityDao;
	
	@Override
    public List<Priority> getAllPriorities(){
		List<Priority> allPriorities = PriorityDao.findByOrderByValueAsc(); //PriorityDao.findAll();
		for (Priority element : allPriorities)
			element.isUsed = !ActivityDao.activitiesWithPriority(element.id).isEmpty();
		return allPriorities;
    }
	
	@Override
	public void saveNewPriority(Priority priority) {
		priority.value = PriorityDao.findMaxPriorityValue() + 1;
		PriorityDao.save(priority);
	}
	
	@Override
	public void deletePriority(Integer priorityId) {
		if (ActivityDao.activitiesWithPriority(priorityId).isEmpty() == true)
			PriorityDao.deleteById(priorityId);
	}
	
	@Override
	public void increasePriority(Integer priorityId) {
		Priority chosenPriority = PriorityDao.findById(priorityId).get();
		Priority otherPriority = PriorityDao.findPriorityWithHigherValue(chosenPriority.value);
		System.out.println(otherPriority);
		if (otherPriority != null) {
			Integer tempValue = chosenPriority.value;
			chosenPriority.value = otherPriority.value;
			otherPriority.value = tempValue;
			PriorityDao.save(chosenPriority);
			PriorityDao.save(otherPriority);
		}
	}
	
	@Override
	public void decreasePriority(Integer priorityId) {
		Priority chosenPriority = PriorityDao.findById(priorityId).get();
		Priority otherPriority = PriorityDao.findPriorityWithLowerValue(chosenPriority.value);
		System.out.println(otherPriority);
		if (otherPriority != null) {
			Integer tempValue = chosenPriority.value;
			chosenPriority.value = otherPriority.value;
			otherPriority.value = tempValue;
			PriorityDao.save(chosenPriority);
			PriorityDao.save(otherPriority);
			}
		}


}