package com.dao.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.domain.Activity;

@Repository
public interface ActivityDao extends JpaRepository<Activity, Integer> {
	
	@Query("SELECT u FROM Activity u WHERE u.priority.id = :priorityId")
	List<Activity> activitiesWithPriority(@Param("priorityId") Integer priorityId);
	
	Boolean existsByName(String name);
	
}
