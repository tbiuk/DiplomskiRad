package com.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.domain.enums.IsCompleted;
import com.domain.enums.IsRepeatable;

@Entity
@Table(name = "filter_options")
public class FilterOptions {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;
	
	@Column(name = "showWithoutDate")
	public Boolean showWithoutDate;
	
	@Column(name = "showWithDate")
	public Boolean showWithDate;

	@Column(name = "minStartDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate minStartDate;
	
	@Column(name = "maxStartDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate maxStartDate;
	
	@Column(name = "minEndDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate minEndDate;
	
	@Column(name = "maxEndDate")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate maxEndDate;
	
	@Column(name = "repetition")
	public IsRepeatable repetition;
	
	@Column(name = "completion")
	public IsCompleted completion;
	
	@Column(name = "ShowWithoutPriority")
	public Boolean ShowWithoutPriority;
	
	@Column(name = "ShowWithPriority")
	public Boolean ShowWithPriority;
	
	@Column(name = "minPriorityValue")
	public Integer minPriorityValue;
	
	@Column(name = "maxPriorityValue")
	public Integer maxPriorityValue;
	
	@OneToMany(mappedBy = "filterOptions")
    private List<Priority> priorities;
	
	@OneToMany(mappedBy = "filterOptions")
    public List<ActivityType> activityTypes;
	
	@ManyToOne(fetch=FetchType.EAGER)
	  @JoinColumn(name="activityType_id")
	  public ActivityType chosenActivityType;
	
	@Transient
	public Integer tempActivityTypeId;
	
	public FilterOptions() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getShowWithoutDate() {
		return showWithoutDate;
	}

	public void setShowWithoutDate(Boolean showWithoutDate) {
		this.showWithoutDate = showWithoutDate;
	}

	public Boolean getShowWithDate() {
		return showWithDate;
	}

	public void setShowWithDate(Boolean showWithDate) {
		this.showWithDate = showWithDate;
	}

	public LocalDate getMinStartDate() {
		return minStartDate;
	}

	public void setMinStartDate(LocalDate minStartDate) {
		this.minStartDate = minStartDate;
	}

	public LocalDate getMaxStartDate() {
		return maxStartDate;
	}

	public void setMaxStartDate(LocalDate maxStartDate) {
		this.maxStartDate = maxStartDate;
	}

	public LocalDate getMinEndDate() {
		return minEndDate;
	}

	public void setMinEndDate(LocalDate minEndDate) {
		this.minEndDate = minEndDate;
	}

	public LocalDate getMaxEndDate() {
		return maxEndDate;
	}

	public void setMaxEndDate(LocalDate maxEndDate) {
		this.maxEndDate = maxEndDate;
	}

	public IsRepeatable getRepetition() {
		return repetition;
	}

	public void setRepetition(IsRepeatable repetition) {
		this.repetition = repetition;
	}

	public IsCompleted getCompletion() {
		return completion;
	}

	public void setCompletion(IsCompleted completion) {
		this.completion = completion;
	}

	public Boolean getShowWithoutPriority() {
		return ShowWithoutPriority;
	}

	public void setShowWithoutPriority(Boolean showWithoutPriority) {
		ShowWithoutPriority = showWithoutPriority;
	}

	public Boolean getShowWithPriority() {
		return ShowWithPriority;
	}

	public void setShowWithPriority(Boolean showWithPriority) {
		ShowWithPriority = showWithPriority;
	}

	public Integer getMinPriorityValue() {
		return minPriorityValue;
	}

	public void setMinPriorityValue(Integer minPriorityValue) {
		this.minPriorityValue = minPriorityValue;
	}

	public Integer getMaxPriorityValue() {
		return maxPriorityValue;
	}

	public void setMaxPriorityValue(Integer maxPriorityValue) {
		this.maxPriorityValue = maxPriorityValue;
	}

	public List<Priority> getPriorities() {
		return priorities;
	}

	public void setPriorities(List<Priority> priorities) {
		this.priorities = priorities;
	}

	public List<ActivityType> getActivityTypes() {
		return activityTypes;
	}

	public void setActivityTypes(List<ActivityType> activityTypes) {
		this.activityTypes = activityTypes;
	}

	public Integer getTempActivityTypeId() {
		return tempActivityTypeId;
	}

	public void setTempActivityTypeId(Integer tempActivityTypeId) {
		this.tempActivityTypeId = tempActivityTypeId;
	}

	public ActivityType getChosenActivityType() {
		return chosenActivityType;
	}

	public void setChosenActivityType(ActivityType chosenActivityType) {
		this.chosenActivityType = chosenActivityType;
	}
	
	

}
