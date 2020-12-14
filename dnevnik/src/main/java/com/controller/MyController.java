package com.controller;


import com.domain.Activity;
import com.domain.ActivityType;
import com.domain.Category;
import com.domain.CategoryGroup;
import com.domain.FilterOptions;
import com.domain.Priority;
import com.services.api.ActivityService;
import com.services.api.ActivityTypeService;
import com.services.api.CategoryGroupService;
import com.services.api.CategoryService;
import com.services.api.FilterOptionsService;
import com.services.api.PriorityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;


@Controller
public class MyController {

	@Autowired
	CategoryGroupService categoryGroupService;
	
	@Autowired
    ActivityService activityService;
	
	@Autowired
    CategoryService categoryService;
	
	@Autowired
    FilterOptionsService filterOptionsService;
	
	@Autowired
    ActivityTypeService activityTypeService;
	
	@Autowired
    PriorityService priorityService;
    
	
    
    @GetMapping("/getAllActivities")
    public List<Activity> getAllPriorities (){
        return activityService.getAllActivities();
    }
    
    @GetMapping("/getAllCategoryGroups")
    public List<CategoryGroup> getAllCategoryGroups (){
        return categoryGroupService.getAllCategoryGroups();
    }
    
    @RequestMapping("/")
    public String mainPage(Model model){
    	Activity changedActivity  = new Activity();
    	model.addAttribute("activities", activityService.getFilteredActivities(filterOptionsService.getFilterOptions()));
    	model.addAttribute("filterOptions", filterOptionsService.getFilterOptions());
    	model.addAttribute("activityTypes", activityTypeService.getAllActivityTypes());
    	model.addAttribute("changedActivity", changedActivity);
        return "pregledAktivnosti";
    }
    
    @RequestMapping("/pregledAktivnosti")
    public String pregledAktivnosti(Model model){
    	Activity changedActivity  = new Activity();
    	model.addAttribute("activities", activityService.getFilteredActivities(filterOptionsService.getFilterOptions()));
    	model.addAttribute("filterOptions", filterOptionsService.getFilterOptions());
    	model.addAttribute("activityTypes", activityTypeService.getAllActivityTypes());
    	model.addAttribute("changedActivity", changedActivity);
        return "pregledAktivnosti";
    }
    
    @RequestMapping("/kategorije")
    public String kategorije(Model model){
    	Category newCategory  = new Category();
    	CategoryGroup newCategoryGroup  = new CategoryGroup();
    	model.addAttribute("PrimaryCategoryGroups", categoryGroupService.getPrimaryCategoryGroups());
    	model.addAttribute("SecondryCategoryGroups", categoryGroupService.getSecondaryCategoryGroups());
    	model.addAttribute("newCategoryGroup", newCategoryGroup);
    	model.addAttribute("newCategory", newCategory);
        return "kategorije";
    }
    
    @RequestMapping("/prioriteti")
    public String prioriteti(Model model){
    	Priority newPriority  = new Priority();
    	model.addAttribute("priorities", priorityService.getAllPriorities());
    	model.addAttribute("newPriority", newPriority);
        return "prioriteti";
    }
    
    @RequestMapping("/novaAktivnost")
    public String novaAktivnost(Model model){
    	Activity newActivity  = new Activity();
    	model.addAttribute("activityTypes", activityTypeService.getAllActivityTypes());
    	model.addAttribute("priorities", priorityService.getAllPriorities());
    	model.addAttribute("newActivity", newActivity);
        return "novaAktivnost";
    }
    
    @RequestMapping("/vrsteAktivnosti")
    public String vrsteAktivnosti(Model model){
    	ActivityType newActivityType  = new ActivityType();
    	model.addAttribute("activityTypes", activityTypeService.getAllActivityTypes());
    	model.addAttribute("PrimaryCategoryGroups", categoryGroupService.getPrimaryCategoryGroups());
    	model.addAttribute("SecondryCategoryGroups", categoryGroupService.getSecondaryCategoryGroups());
    	model.addAttribute("newActivityType", newActivityType);
        return "vrsteAktivnosti";
    }
    
    @PostMapping("/saveNewCategory")
    public String saveNewCategory(@ModelAttribute Category category) {
    	category.categoryGroup = categoryGroupService.get(category.id);
    	category.id = null;
    	categoryService.saveNewCategory(category);
        return "redirect:/kategorije";
    	}
    
    @PostMapping("/saveNewPrimaryCategoryGroup")
    public String saveNewPrimaryCategoryGroup(@ModelAttribute CategoryGroup categoryGroup) {
    	categoryGroup.type = 1;
    	categoryGroupService.saveNewCategoryGroup(categoryGroup);
        return "redirect:/kategorije";
    	}
    
    @PostMapping("/saveNewSecondaryCategoryGroup")
    public String saveNewSecondaryCategoryGroup(@ModelAttribute CategoryGroup categoryGroup) {
    	categoryGroup.type = 2;
    	categoryGroupService.saveNewCategoryGroup(categoryGroup);
        return "redirect:/kategorije";
    	}
    
    @PostMapping("/saveNewPriority")
    public String saveNewPriority(@ModelAttribute Priority priority) {
    	priorityService.saveNewPriority(priority);
        return "redirect:/prioriteti";
    	}
    
    @RequestMapping("/deleteActivity/{id}")
    public String deleteActivity(@PathVariable (name = "id") int id) {
    	activityService.deleteActivity(id);
        return "redirect:/pregledAktivnosti";
    	}
    
    @RequestMapping("/deletePriority/{id}")
    public String deletePriority(@PathVariable (name = "id") int id) {
    	priorityService.deletePriority(id);
        return "redirect:/prioriteti";
    	}
    
    @RequestMapping("/increasePriority/{id}")
    public String increasePriority(@PathVariable (name = "id") int id) {
    	priorityService.increasePriority(id);
        return "redirect:/prioriteti";
    	}
    
    @RequestMapping("/decreasePriority/{id}")
    public String decreasePriority(@PathVariable (name = "id") int id) {
    	priorityService.decreasePriority(id);
        return "redirect:/prioriteti";
    	}
    
    @PostMapping("/updateActivity")
    public String updateActivity(@ModelAttribute Activity activity) {
    	activityService.updateActivity(activity);
        return "redirect:/pregledAktivnosti";
    	}
    
    @PostMapping("/saveNewActivity")
    public String saveNewActivity(@ModelAttribute Activity activity) {
    	activityService.updateActivity(activity);
        return "redirect:/novaAktivnost";
    	}
    
    @PostMapping("/updateActivityType")
    public String updateActivity(@ModelAttribute ActivityType activityType) {
    	activityTypeService.updateActivityType(activityType);
        return "redirect:/vrsteAktivnosti";
    	}
    
    @PostMapping("/changeFilterOptions")
    public String changeFilterOptions(@ModelAttribute FilterOptions filterOptions) {
    	filterOptionsService.UpdateFilterOptions(filterOptions);
        return "redirect:/pregledAktivnosti";
    	}
    
    @RequestMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable (name = "id") int id) {
    	categoryService.deleteCategory(id);
        return "redirect:/kategorije";
    	}
    
    @RequestMapping("/deleteCategoryGroup/{id}")
    public String deleteCategoryGroup(@PathVariable (name = "id") int id) {
    	categoryGroupService.deleteCategoryGroup(id);
        return "redirect:/kategorije";
    	}
    
    @RequestMapping("/deleteActivityType/{id}")
    public String deleteActivityType(@PathVariable (name = "id") int id) {
    	activityTypeService.delete(id);
        return "redirect:/vrsteAktivnosti";
    	}

}
