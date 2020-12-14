package com.dao.api;

import com.domain.Priority;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriorityDao extends JpaRepository<Priority, Integer> {

	
	@Query("SELECT coalesce(max(u.value), 0) FROM Priority u")
	Integer findMaxPriorityValue();
	
	@Query(value = "SELECT * FROM Priority WHERE value < :value ORDER BY value DESC LIMIT 1", nativeQuery = true)
	Priority findPriorityWithHigherValue(@Param("value") Integer value);
	
	@Query(value = "SELECT * FROM Priority WHERE value > :value ORDER BY value ASC LIMIT 1", nativeQuery = true)
	Priority findPriorityWithLowerValue(@Param("value") Integer value);
	
	List<Priority> findByOrderByValueAsc();
}
