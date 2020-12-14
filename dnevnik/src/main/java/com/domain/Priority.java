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
import javax.persistence.Transient;

@Entity
@Table(name = "priority")
public class Priority {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;
	
	@Column(name = "value")
    public Integer value;
	
	@Column(name = "name")
    public String name;
    
    @OneToMany(mappedBy = "priority")
    private List<Activity> activities;
    
    @ManyToOne(fetch=FetchType.LAZY)
	  @JoinColumn(name="filter_options_id")
	  private FilterOptions filterOptions;
    
    @Transient
	public Boolean isUsed;
    
    public Priority() {

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
    
    public Integer getValue() {
        return value;
    }
    
    public void setValue(Integer value) {
        this.value = value;
    }

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}
    
    

}