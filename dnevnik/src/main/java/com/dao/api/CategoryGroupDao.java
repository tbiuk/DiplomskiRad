package com.dao.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.domain.CategoryGroup;

@Repository
public interface CategoryGroupDao extends JpaRepository<CategoryGroup, Integer> {
	
	@Query("SELECT u FROM CategoryGroup u WHERE u.type = 1")
	List<CategoryGroup> findPrimaryCategoryGroups();
	
	@Query("SELECT u FROM CategoryGroup u WHERE u.type = 2")
	List<CategoryGroup> findSecondaryCategoryGroups();
	
}
