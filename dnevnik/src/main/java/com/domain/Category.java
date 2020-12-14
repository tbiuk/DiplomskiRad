package com.domain;

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


@Entity
@Table(name = "category")
public class Category {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;
	
	@Column(name = "name")
	public String name;
	
	@OneToMany(mappedBy = "primaryCategory")
    private List<Activity> activitiesWithPrimaryCategory;
	
	@OneToMany(mappedBy = "secondaryCategory")
    private List<Activity> activitiesWithSecondaryCategory;
	
	@ManyToOne(fetch=FetchType.LAZY)
	  @JoinColumn(name="category_group_id")
	  public CategoryGroup categoryGroup;
	
	@Column(name = "isSelected")
	public Boolean isSelected;
	
	public Category() 
    {
        
    }

	//Getters and setters
    public Category(Integer id, String name) {
        this.id = id;
    	this.name = name;
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
    
    public CategoryGroup getCategoryGroup() {
        return categoryGroup;
    }
    
    public void setCategoryGroup(CategoryGroup categoryGroup) {
        this.categoryGroup = categoryGroup;
    }
    
    public Boolean getIsSelected() {
        return isSelected;
    }
    
    public void setIsSelected(Boolean isSelected) {
        this.isSelected = isSelected;
    }

}
