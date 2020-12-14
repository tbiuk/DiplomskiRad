package com.domain;

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

@Entity
@Table(name = "activity_type")
public class ActivityType {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;
	
	@Column(name = "name")
	public String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	  @JoinColumn(name="primary_category_group_id")
	  public CategoryGroup primaryCategoryGroup;
	
	@ManyToOne(fetch=FetchType.EAGER)
	  @JoinColumn(name="secondary_category_group_id")
	  public CategoryGroup secondaryCategoryGroup;
	
	@ManyToOne(fetch=FetchType.EAGER)
	  @JoinColumn(name="filter_options_id")
	  public FilterOptions filterOptions;
	
	@Transient
	public Integer tempPrimaryCategoryGroupId;
	
	@Transient
	public Integer tempSecondaryCategoryGroupId;
	
	@Column(name = "isSelected")
	public Boolean isSelected;
	
	public ActivityType() 
    {
        
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

	public CategoryGroup getPrimaryCategoryGroup() {
		return primaryCategoryGroup;
	}

	public void setPrimaryCategoryGroup(CategoryGroup primaryCategoryGroup) {
		this.primaryCategoryGroup = primaryCategoryGroup;
	}

	public CategoryGroup getSecondaryCategoryGroup() {
		return secondaryCategoryGroup;
	}

	public void setSecondaryCategoryGroup(CategoryGroup secondaryCategoryGroup) {
		this.secondaryCategoryGroup = secondaryCategoryGroup;
	}

	public Boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	public Integer getTempPrimaryCategoryGroupId() {
		return tempPrimaryCategoryGroupId;
	}

	public void setTempPrimaryCategoryGroupId(Integer tempPrimaryCategoryGroupId) {
		this.tempPrimaryCategoryGroupId = tempPrimaryCategoryGroupId;
	}

	public Integer getTempSecondaryCategoryGroupId() {
		return tempSecondaryCategoryGroupId;
	}

	public void setTempSecondaryCategoryGroupId(Integer tempSecondaryCategoryGroupId) {
		this.tempSecondaryCategoryGroupId = tempSecondaryCategoryGroupId;
	}
	
	public Boolean isPrimaryCategoryGroupSelected()
	{
		if (this.tempPrimaryCategoryGroupId == null || this.tempPrimaryCategoryGroupId == 0)
			return false;
		else
			return true;
	}
	
	public Boolean isSecondaryCategoryGroupSelected()
	{
		if (this.tempSecondaryCategoryGroupId == null || this.tempSecondaryCategoryGroupId == 0)
			return false;
		else
			return true;
	}
	

}
