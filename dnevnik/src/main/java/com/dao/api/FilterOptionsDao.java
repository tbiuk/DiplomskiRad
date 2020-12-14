package com.dao.api;

import com.domain.FilterOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterOptionsDao extends JpaRepository<FilterOptions, Integer> {

}
