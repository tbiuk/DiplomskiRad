package com.dao.api;

import com.domain.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityTypeDao extends JpaRepository<ActivityType, Integer> {

}
