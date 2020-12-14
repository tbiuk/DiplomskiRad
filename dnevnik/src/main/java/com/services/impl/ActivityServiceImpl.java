package com.services.impl;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.api.ActivityDao;
import com.dao.api.PriorityDao;
import com.dao.api.ActivityTypeDao;
import com.dao.api.CategoryDao;
import com.domain.Activity;
import com.domain.FilterOptions;
import com.domain.enums.IsRepeatable;
import com.domain.enums.RepetitionType;
import com.services.api.ActivityService;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	Integer flag = 0;

    ActivityDao ActivityDao;
    PriorityDao PriorityDao;
    ActivityTypeDao ActivityTypeDao;
    CategoryDao CategoryDao;
    
    @Autowired
    public ActivityServiceImpl(ActivityDao ActivityDao, PriorityDao PriorityDao, ActivityTypeDao ActivityTypeDao, CategoryDao CategoryDao) {
        this.ActivityDao = ActivityDao;
        this.PriorityDao = PriorityDao;
        this.ActivityTypeDao = ActivityTypeDao;
        this.CategoryDao = CategoryDao;
    }
    
	
	@Override
    public List<Activity> getAllActivities(){
        return ActivityDao.findAll();
    }
	
	@Override
    public List<Activity> getFilteredActivities(FilterOptions filterOptions){
		List<Activity> filteredActivities = ActivityDao.findAll();
		List<Activity> found = new ArrayList<Activity>();
		List<Activity> foundToAdd = new ArrayList<Activity>();
		
		if(filterOptions.chosenActivityType != null) {
			for (Activity element : filteredActivities) {
				if (element.activityType != filterOptions.chosenActivityType)
					found.add(element);
			}
			filteredActivities.removeAll(found);
			found.removeAll(found);
		}
		
		if(filterOptions.showWithoutDate == false) {
			for (Activity element : filteredActivities) {
				if (element.startDate == null)
					found.add(element);
			}
			filteredActivities.removeAll(found);
			found.removeAll(found);
		}
		
		if(filterOptions.showWithDate == false) {
			for (Activity element : filteredActivities) {
				if (element.startDate != null)
					found.add(element);
			}
			filteredActivities.removeAll(found);
			found.removeAll(found);
		}
		
		if(filterOptions.showWithDate == true) {
			for (Activity element : filteredActivities) {
				if(element.startDate != null && element.endDate != null && element.repetitionType == RepetitionType.None) {
					if (filterOptions.getMinStartDate() != null && element.startDate.isBefore(filterOptions.getMinStartDate().atStartOfDay()))
						found.add(element);
					if (filterOptions.getMaxStartDate() != null && element.startDate.isAfter(filterOptions.getMaxStartDate().plusDays(1).atStartOfDay()))
						found.add(element);
					if (filterOptions.getMinEndDate() != null && element.endDate.isBefore(filterOptions.getMinEndDate().atStartOfDay()))
						found.add(element);
					if (filterOptions.getMaxEndDate() != null && element.endDate.isAfter(filterOptions.getMaxEndDate().plusDays(1).atStartOfDay()))
						found.add(element);
				}
			}
			filteredActivities.removeAll(found);
			found.removeAll(found);
		}
		
		
		if(filterOptions.ShowWithoutPriority == false) {
			for (Activity element : filteredActivities) {
				if (element.priority == null)
					found.add(element);
			}
			filteredActivities.removeAll(found);
			found.removeAll(found);
		}
		
		if(filterOptions.ShowWithPriority == false) {
			for (Activity element : filteredActivities) {
				if (element.priority != null)
					found.add(element);
			}
			filteredActivities.removeAll(found);
			found.removeAll(found);
		}
		else {
			for (Activity element : filteredActivities) {
				if (element.priority != null) {
					flag = 0;
					if (filterOptions.maxPriorityValue != null && element.priority.value < filterOptions.maxPriorityValue) {
						flag = 1;
						found.add(element);
					}	
					if (flag == 0 && filterOptions.minPriorityValue != null && element.priority.value > filterOptions.minPriorityValue)
						found.add(element);
				}
				flag = 0;
			}
			filteredActivities.removeAll(found);
			found.removeAll(found);
		}
		
		switch(filterOptions.repetition) {
		  case Jednokratne:
			  for (Activity element : filteredActivities) {
					if (element.repetitionType != RepetitionType.None)
						found.add(element);
				}
				filteredActivities.removeAll(found);
				found.removeAll(found);
		    break;
		  case Ponavljajuće:
			  for (Activity element : filteredActivities) {
					if (element.repetitionType == RepetitionType.None)
						found.add(element);
				}
				filteredActivities.removeAll(found);
				found.removeAll(found);
		    break;
		  case Sve:
			    break;
		  default:
		}
		
		//Filtriranje po završenosti
		switch(filterOptions.completion) {
		  case Završene:
			  for (Activity element : filteredActivities) {
					if (element.repetitionType == RepetitionType.None)
						if (element.status != null && element.status == false)
							found.add(element);
					if (element.repetitionType != RepetitionType.None) {
						//stvoriti sve ponavljajuće aktivnosti koje su završene
						int i = 0;
						LocalDateTime tempEnd = element.endDate;
						while (tempEnd.isBefore(LocalDateTime.now())) {
							foundToAdd.add(new Activity(element, i));
							i++;
							if (element.repetitionType == RepetitionType.Daily)
								tempEnd = tempEnd.plusDays(1);
							else
							if (element.repetitionType == RepetitionType.Weekly)
								tempEnd = tempEnd.plusWeeks(1);
							else
							if (element.repetitionType == RepetitionType.Monthly)
								tempEnd = tempEnd.plusMonths(1);
							else
							if (element.repetitionType == RepetitionType.Yearly)
								tempEnd = tempEnd.plusYears(1);
						}
						
						for (Activity repetingElement : foundToAdd) {
								if (filterOptions.getMinStartDate() != null && repetingElement.currentRepetitionStartDate.isBefore(filterOptions.getMinStartDate().atStartOfDay()))
									found.add(repetingElement);
								if (filterOptions.getMaxStartDate() != null && repetingElement.currentRepetitionStartDate.isAfter(filterOptions.getMaxStartDate().plusDays(1).atStartOfDay()))
									found.add(repetingElement);
								if (filterOptions.getMinEndDate() != null && repetingElement.currentRepetitionEndDate.isBefore(filterOptions.getMinEndDate().atStartOfDay()))
									found.add(repetingElement);
								if (filterOptions.getMaxEndDate() != null && repetingElement.currentRepetitionEndDate.isAfter(filterOptions.getMaxEndDate().plusDays(1).atStartOfDay()))
									found.add(repetingElement);
								
								if (repetingElement.finalRepetitionDate != null && repetingElement.currentRepetitionEndDate.isAfter(repetingElement.finalRepetitionDate.plusDays(1).atStartOfDay()))
									found.add(repetingElement);
						}
						
						
						found.add(element);
					}
				}
			  filteredActivities.addAll(foundToAdd);
			  filteredActivities.removeAll(found);
			  found.removeAll(found);
			  foundToAdd.removeAll(found);
		    break;
		  case Nezavršene:
			  for (Activity element : filteredActivities) {
				    //Izbaciti jednokratnu aktivnost ako je završena
					if (element.repetitionType == RepetitionType.None)
						if (element.status != null && element.status == true)
							found.add(element);
					//Dodavanje nedovršenih ponavljanja ponavljajuće aktivnosti
					if (element.repetitionType != RepetitionType.None) {
						int i = 0;
						LocalDateTime tempEnd = element.endDate;
						while (tempEnd.isBefore(LocalDateTime.now())) {
							i++;
							if (element.repetitionType == RepetitionType.Daily)
								tempEnd = tempEnd.plusDays(1);
							else
							if (element.repetitionType == RepetitionType.Weekly)
								tempEnd = tempEnd.plusWeeks(1);
							else
							if (element.repetitionType == RepetitionType.Monthly)
								tempEnd = tempEnd.plusMonths(1);
							else
							if (element.repetitionType == RepetitionType.Yearly)
								tempEnd = tempEnd.plusYears(1);
						}
						if (filterOptions.getMaxStartDate() == null && filterOptions.getMaxEndDate() == null) {
							if (element.finalRepetitionDate == null || element.finalRepetitionDate.plusDays(1).atStartOfDay().isAfter(tempEnd)) {
									foundToAdd.add(new Activity(element, i));
									i++;
								}
							}
							
						if ( filterOptions.getMaxStartDate() != null || filterOptions.getMaxEndDate() != null) {
								//Case where filter has max start date
								if(filterOptions.getMaxStartDate() != null && filterOptions.getMaxEndDate() == null) {
									Duration duration = Duration.between(element.startDate, element.endDate);
									LocalDateTime tempStart = tempEnd.minus(duration);
									
									if (filterOptions.getMaxStartDate().plusDays(1).atStartOfDay().isAfter(tempStart)) {
										while (tempStart.isBefore(filterOptions.getMaxStartDate().plusDays(1).atStartOfDay())) {
											foundToAdd.add(new Activity(element, i));
											i++;
											if (element.repetitionType == RepetitionType.Daily)
												tempStart = tempStart.plusDays(1);
											else
											if (element.repetitionType == RepetitionType.Weekly)
												tempStart = tempStart.plusWeeks(1);
											else
											if (element.repetitionType == RepetitionType.Monthly)
												tempStart = tempStart.plusMonths(1);
											else
											if (element.repetitionType == RepetitionType.Yearly)
												tempStart = tempStart.plusYears(1);
										}
									}
								}
								
								//Case where filter has max end date
								if(filterOptions.getMaxStartDate() == null && filterOptions.getMaxEndDate() != null) {			
									if (filterOptions.getMaxEndDate().plusDays(1).atStartOfDay().isAfter(tempEnd)) {
										while (tempEnd.isBefore(filterOptions.getMaxEndDate().plusDays(1).atStartOfDay())) {
											foundToAdd.add(new Activity(element, i));
											i++;
											if (element.repetitionType == RepetitionType.Daily)
												tempEnd = tempEnd.plusDays(1);
											else
											if (element.repetitionType == RepetitionType.Weekly)
												tempEnd = tempEnd.plusWeeks(1);
											else
											if (element.repetitionType == RepetitionType.Monthly)
												tempEnd = tempEnd.plusMonths(1);
											else
											if (element.repetitionType == RepetitionType.Yearly)
												tempEnd = tempEnd.plusYears(1);
										}
									}
								}
								
								//Case where filter has both max start date and max end date
								if(filterOptions.getMaxStartDate() != null && filterOptions.getMaxEndDate() != null) {		
									Duration duration = Duration.between(element.startDate, element.endDate);
									LocalDateTime tempStart = tempEnd.minus(duration);
									if (filterOptions.getMaxEndDate().plusDays(1).atStartOfDay().isAfter(tempEnd) && filterOptions.getMaxStartDate().plusDays(1).atStartOfDay().isAfter(tempStart)) {
										while (tempEnd.isBefore(filterOptions.getMaxEndDate().plusDays(1).atStartOfDay()) && tempStart.isBefore(filterOptions.getMaxStartDate().plusDays(1).atStartOfDay())) {
											foundToAdd.add(new Activity(element, i));
											i++;
											if (element.repetitionType == RepetitionType.Daily) {
												tempEnd = tempEnd.plusDays(1);
												tempStart = tempStart.plusDays(1);
											}
											else
											if (element.repetitionType == RepetitionType.Weekly) {
												tempEnd = tempEnd.plusWeeks(1);
												tempStart = tempStart.plusWeeks(1);
											}
											else
											if (element.repetitionType == RepetitionType.Monthly) {
												tempEnd = tempEnd.plusMonths(1);
												tempStart = tempStart.plusMonths(1);
											}
											else
											if (element.repetitionType == RepetitionType.Yearly) {
												tempEnd = tempEnd.plusYears(1);
												tempStart = tempStart.plusYears(1);
											}
										}
									}
								}
								
								
							}
								
						
							
						found.add(element);
						
						for (Activity repetingElement : foundToAdd) {
							if (filterOptions.getMinStartDate() != null && repetingElement.currentRepetitionStartDate.isBefore(filterOptions.getMinStartDate().atStartOfDay()))
								found.add(repetingElement);
							if (filterOptions.getMaxStartDate() != null && repetingElement.currentRepetitionStartDate.isAfter(filterOptions.getMaxStartDate().plusDays(1).atStartOfDay()))
								found.add(repetingElement);
							if (filterOptions.getMinEndDate() != null && repetingElement.currentRepetitionEndDate.isBefore(filterOptions.getMinEndDate().atStartOfDay()))
								found.add(repetingElement);
							if (filterOptions.getMaxEndDate() != null && repetingElement.currentRepetitionEndDate.isAfter(filterOptions.getMaxEndDate().plusDays(1).atStartOfDay()))
								found.add(repetingElement);
							
							if (repetingElement.finalRepetitionDate != null && repetingElement.currentRepetitionEndDate.isAfter(repetingElement.finalRepetitionDate.plusDays(1).atStartOfDay()))
								found.add(repetingElement);
							}
					}
				}
			  filteredActivities.addAll(foundToAdd);
			  filteredActivities.removeAll(found);
			  found.removeAll(found);
			  foundToAdd.removeAll(found);
			  
		    break;
		  case Sve:
			  for (Activity element : filteredActivities) {
						//Dodavanje nedovršenih ponavljanja ponavljajuće aktivnosti
						if (element.repetitionType != RepetitionType.None) {
							int i = 0;
							LocalDateTime tempEnd = element.endDate;
							while (tempEnd.isBefore(LocalDateTime.now())) {
								foundToAdd.add(new Activity(element, i));
								i++;
								if (element.repetitionType == RepetitionType.Daily)
									tempEnd = tempEnd.plusDays(1);
								else
								if (element.repetitionType == RepetitionType.Weekly)
									tempEnd = tempEnd.plusWeeks(1);
								else
								if (element.repetitionType == RepetitionType.Monthly)
									tempEnd = tempEnd.plusMonths(1);
								else
								if (element.repetitionType == RepetitionType.Yearly)
									tempEnd = tempEnd.plusYears(1);
							}
							if (filterOptions.getMaxStartDate() == null && filterOptions.getMaxEndDate() == null) {
								if (element.finalRepetitionDate == null || element.finalRepetitionDate.plusDays(1).atStartOfDay().isAfter(tempEnd)) {
										foundToAdd.add(new Activity(element, i));
										i++;
									}
								}
								
							if ( filterOptions.getMaxStartDate() != null || filterOptions.getMaxEndDate() != null) {
									//Case where filter has max start date
									if(filterOptions.getMaxStartDate() != null && filterOptions.getMaxEndDate() == null) {
										Duration duration = Duration.between(element.startDate, element.endDate);
										LocalDateTime tempStart = tempEnd.minus(duration);
										
										if (filterOptions.getMaxStartDate().plusDays(1).atStartOfDay().isAfter(tempStart)) {
											while (tempStart.isBefore(filterOptions.getMaxStartDate().plusDays(1).atStartOfDay())) {
												foundToAdd.add(new Activity(element, i));
												i++;
												if (element.repetitionType == RepetitionType.Daily)
													tempStart = tempStart.plusDays(1);
												else
												if (element.repetitionType == RepetitionType.Weekly)
													tempStart = tempStart.plusWeeks(1);
												else
												if (element.repetitionType == RepetitionType.Monthly)
													tempStart = tempStart.plusMonths(1);
												else
												if (element.repetitionType == RepetitionType.Yearly)
													tempStart = tempStart.plusYears(1);
											}
										}
									}
									
									//Case where filter has max end date
									if(filterOptions.getMaxStartDate() == null && filterOptions.getMaxEndDate() != null) {			
										if (filterOptions.getMaxEndDate().plusDays(1).atStartOfDay().isAfter(tempEnd)) {
											while (tempEnd.isBefore(filterOptions.getMaxEndDate().plusDays(1).atStartOfDay())) {
												foundToAdd.add(new Activity(element, i));
												i++;
												if (element.repetitionType == RepetitionType.Daily)
													tempEnd = tempEnd.plusDays(1);
												else
												if (element.repetitionType == RepetitionType.Weekly)
													tempEnd = tempEnd.plusWeeks(1);
												else
												if (element.repetitionType == RepetitionType.Monthly)
													tempEnd = tempEnd.plusMonths(1);
												else
												if (element.repetitionType == RepetitionType.Yearly)
													tempEnd = tempEnd.plusYears(1);
											}
										}
									}
									
									//Case where filter has both max start date and max end date
									if(filterOptions.getMaxStartDate() != null && filterOptions.getMaxEndDate() != null) {		
										Duration duration = Duration.between(element.startDate, element.endDate);
										LocalDateTime tempStart = tempEnd.minus(duration);
										if (filterOptions.getMaxEndDate().plusDays(1).atStartOfDay().isAfter(tempEnd) && filterOptions.getMaxStartDate().plusDays(1).atStartOfDay().isAfter(tempStart)) {
											while (tempEnd.isBefore(filterOptions.getMaxEndDate().plusDays(1).atStartOfDay()) && tempStart.isBefore(filterOptions.getMaxStartDate().plusDays(1).atStartOfDay())) {
												foundToAdd.add(new Activity(element, i));
												i++;
												if (element.repetitionType == RepetitionType.Daily) {
													tempEnd = tempEnd.plusDays(1);
													tempStart = tempStart.plusDays(1);
												}
												else
												if (element.repetitionType == RepetitionType.Weekly) {
													tempEnd = tempEnd.plusWeeks(1);
													tempStart = tempStart.plusWeeks(1);
												}
												else
												if (element.repetitionType == RepetitionType.Monthly) {
													tempEnd = tempEnd.plusMonths(1);
													tempStart = tempStart.plusMonths(1);
												}
												else
												if (element.repetitionType == RepetitionType.Yearly) {
													tempEnd = tempEnd.plusYears(1);
													tempStart = tempStart.plusYears(1);
												}
											}
										}
									}
									
									
								}
						
						for (Activity repetingElement : foundToAdd) {
								if (filterOptions.getMinStartDate() != null && repetingElement.currentRepetitionStartDate.isBefore(filterOptions.getMinStartDate().atStartOfDay()))
									found.add(repetingElement);
								if (filterOptions.getMaxStartDate() != null && repetingElement.currentRepetitionStartDate.isAfter(filterOptions.getMaxStartDate().plusDays(1).atStartOfDay()))
									found.add(repetingElement);
								if (filterOptions.getMinEndDate() != null && repetingElement.currentRepetitionEndDate.isBefore(filterOptions.getMinEndDate().atStartOfDay()))
									found.add(repetingElement);
								if (filterOptions.getMaxEndDate() != null && repetingElement.currentRepetitionEndDate.isAfter(filterOptions.getMaxEndDate().plusDays(1).atStartOfDay()))
									found.add(repetingElement);
								
								if (repetingElement.finalRepetitionDate != null && repetingElement.currentRepetitionEndDate.isAfter(repetingElement.finalRepetitionDate.plusDays(1).atStartOfDay()))
									found.add(repetingElement);
						}
						
						found.add(element);
					}
				}
			  filteredActivities.addAll(foundToAdd);
			  filteredActivities.removeAll(found);
			  found.removeAll(found);
			  foundToAdd.removeAll(found);
			  break;
		  default:
		}
		
		
		for (Activity element : filteredActivities) {
			  if (element.currentRepetitionStartDate == null) {
				  element.currentRepetitionStartDate = element.startDate;
				  element.currentRepetitionEndDate = element.endDate;
			  }
				  
				  
			}
		
		return filteredActivities;
    }
	
	@Override
	public void save(Activity activity){
        ActivityDao.save(activity);
	}
	
	@Override
	public boolean updateActivity(Activity activity) {
		
		if (activity.status == null)
			activity.status = false;
		
		if (activity.tempPrimaryCategoryId == null)
			activity.primaryCategory = null;
		else
			activity.primaryCategory = CategoryDao.findById(activity.tempPrimaryCategoryId).get();
		
		if (activity.tempSecondaryCategoryId == null)
			activity.secondaryCategory = null;
		else
			activity.secondaryCategory = CategoryDao.findById(activity.tempSecondaryCategoryId).get();
		
		if (activity.tempActivityTypeId == null)
			activity.activityType = null;
		else
			activity.activityType = ActivityTypeDao.findById(activity.tempActivityTypeId).get();
		
		if (activity.tempPriorityId == null || activity.tempPriorityId == 0)
			activity.priority = null;
		else
			activity.priority = PriorityDao.findById(activity.tempPriorityId).get();
		
		Boolean flag = false;
		
		//Postavi flag ako je uneseno samo vrijeme početka ili samo vrijeme kraja aktivnosti
		if (activity.startDate == null ^ activity.endDate == null) {
			flag = true;
		}
		
		//Postavi flag ako aktivnost nema prioritet i nije uneseno vrijeme kada se treba obaviti
		if (activity.priority == null && activity.hasDate() == false ) {
			flag = true;
		}
		
		//Postavi flag ako ponavljajuća aktivnost nema vrijeme početka i završetka prvog ponavljanja
		if (activity.repetitionType != RepetitionType.None && activity.hasDate() == false ) {
			flag = true;
		}
		
		if (flag == false) {
			ActivityDao.save(activity);
			return true;
		}
		else 
			return false;
			
	}
	
	@Override
	public void deleteActivity(Integer activityId) {
		ActivityDao.deleteById(activityId);
	}
	
	public Activity get(Integer id){
        return ActivityDao.findById(id).get();
	}
	
	public void delete(Integer id){
        ActivityDao.deleteById(id);
	}
	
}