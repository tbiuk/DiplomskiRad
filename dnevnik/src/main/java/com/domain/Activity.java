package com.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.domain.enums.RepetitionType;

@Entity
@Table(name = "activity")
public class Activity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;
	
	@Column(name = "name")
	public String name;
	
	@Column(name = "description")
	public String description;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "startDate")
	public LocalDateTime startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	@Column(name = "endDate")
	public LocalDateTime endDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "finalRepetitionDate")
	public LocalDate finalRepetitionDate;
	
	@Column(name = "repetitionType")
	public RepetitionType repetitionType;
	
	@Column(name = "status")
	public Boolean status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	  @JoinColumn(name="priority_id")
	  public Priority priority;
	
	@ManyToOne(fetch=FetchType.EAGER)
	  @JoinColumn(name="activityType_id")
	  public ActivityType activityType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	  @JoinColumn(name="primaryCategory_id")
	  public Category primaryCategory;
	
	@ManyToOne(fetch=FetchType.EAGER)
	  @JoinColumn(name="secondaryCategory_id")
	  public Category secondaryCategory;
	
	@Transient
	public Integer tempActivityTypeId;
	
	@Transient
	public Integer tempPriorityId;
	
	@Transient
	public Integer tempPrimaryCategoryId;
	
	@Transient
	public Integer tempSecondaryCategoryId;
	
	@Transient
	public LocalDateTime currentRepetitionStartDate;
	
	@Transient
	public LocalDateTime currentRepetitionEndDate;
	
	
	public Activity() 
    {
        
    }
	
	
	
	public Activity(Activity activity, Integer repetitionNumber) {
		super();
		this.id = activity.id;
		this.name = activity.name;
		this.description = activity.description;
		this.startDate = activity.startDate;
		this.endDate = activity.endDate;
		this.finalRepetitionDate = activity.finalRepetitionDate;
		this.repetitionType = activity.repetitionType;
		this.status = activity.status;
		this.priority = activity.priority;
		this.activityType = activity.activityType;
		this.primaryCategory = activity.primaryCategory;
		this.secondaryCategory = activity.secondaryCategory;
		this.tempActivityTypeId = activity.tempActivityTypeId;
		
		if (this.repetitionType == RepetitionType.Daily) {
			this.currentRepetitionStartDate = activity.startDate.plusDays(repetitionNumber);
			this.currentRepetitionEndDate = activity.endDate.plusDays(repetitionNumber);
		}
		else
		if (this.repetitionType == RepetitionType.Weekly) {
			this.currentRepetitionStartDate = activity.startDate.plusWeeks(repetitionNumber);
			this.currentRepetitionEndDate = activity.endDate.plusWeeks(repetitionNumber);
		}
		else
		if (this.repetitionType == RepetitionType.Monthly) {
			this.currentRepetitionStartDate = activity.startDate.plusMonths(repetitionNumber);
			this.currentRepetitionEndDate = activity.endDate.plusMonths(repetitionNumber);
		}
		else
		if (this.repetitionType == RepetitionType.Yearly) {
			this.currentRepetitionStartDate = activity.startDate.plusYears(repetitionNumber);
			this.currentRepetitionEndDate = activity.endDate.plusYears(repetitionNumber);
		}
		else {
			this.currentRepetitionStartDate = activity.startDate;
			this.currentRepetitionEndDate = activity.endDate;
		}
			
		
	}
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public RepetitionType getRepetitionType() {
		return repetitionType;
	}

	public void setRepetitionType(RepetitionType repetitionType) {
		this.repetitionType = repetitionType;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public Integer getTempActivityTypeId() {
		return tempActivityTypeId;
	}

	public void setTempActivityTypeId(Integer tempActivityTypeId) {
		this.tempActivityTypeId = tempActivityTypeId;
	}

	public LocalDateTime getCurrentRepetitionStartDate() {
		return currentRepetitionStartDate;
	}

	public void setCurrentRepetitionStartDate(LocalDateTime currentRepetitionStartDate) {
		this.currentRepetitionStartDate = currentRepetitionStartDate;
	}

	public LocalDateTime getCurrentRepetitionEndDate() {
		return currentRepetitionEndDate;
	}

	public void setCurrentRepetitionEndDate(LocalDateTime currentRepetitionEndDate) {
		this.currentRepetitionEndDate = currentRepetitionEndDate;
	}

	public LocalDate getFinalRepetitionDate() {
		return finalRepetitionDate;
	}

	public void setFinalRepetitionDate(LocalDate finalRepetitionDate) {
		this.finalRepetitionDate = finalRepetitionDate;
	}


	public Integer getTempPriorityId() {
		return tempPriorityId;
	}


	public void setTempPriorityId(Integer tempPriorityId) {
		this.tempPriorityId = tempPriorityId;
	}



	public Integer getTempPrimaryCategoryId() {
		return tempPrimaryCategoryId;
	}



	public void setTempPrimaryCategoryId(Integer tempPrimaryCategoryId) {
		this.tempPrimaryCategoryId = tempPrimaryCategoryId;
	}



	public Integer getTempSecondaryCategoryId() {
		return tempSecondaryCategoryId;
	}



	public void setTempSecondaryCategoryId(Integer tempSecondaryCategoryId) {
		this.tempSecondaryCategoryId = tempSecondaryCategoryId;
	}
	
	public Boolean hasDate() {
		return (startDate != null && endDate != null);
	}
	

}
