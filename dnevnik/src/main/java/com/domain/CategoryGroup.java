package com.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category_group")
public class CategoryGroup {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;
	
	@Column(name = "type")
	public Integer type;
	
	@Column(name = "name")
    public String name;
    
    @OneToMany(mappedBy = "categoryGroup")
    public List<Category> categories;
    
    @OneToMany(mappedBy = "primaryCategoryGroup")
    private List<ActivityType> activityTypesWithPrimaryCategoryGroup;
	
	@OneToMany(mappedBy = "secondaryCategoryGroup")
    private List<ActivityType> activityTypesWithSecondaryCategoryGroup;
    
    public CategoryGroup() {

    }

    public CategoryGroup(Integer id, Integer type, String name) {
        this.id = id;
        this.type = type;
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

}