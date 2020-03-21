package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Properties;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PropertyController {

	private static final String VIEWS_PROPERTIES_CREATE_OR_UPDATE_FORM = "properties/createOrUpdatePropertyForm";
	private static final String VIEWS_PROPERTIES_SHOW = "properties/showForm";

	private final PropertyService propertyService;
	
	

	
	@Autowired
	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
	
	@ModelAttribute("propertyTypes")
    public Collection<String> propertyTypes() {
		List<String> lista = new ArrayList<String>();
		lista.add("House");
		lista.add("Flat");
        return lista ;
    }
	
	@GetMapping(value = {"/properties"})
	public String showPropertyList(Map<String, Object> model) {
		Properties properties = new Properties();
		
		properties.getPropertyList().addAll(this.propertyService.findAll());
		
		model.put("properties", properties); 
		return "properties/propertiesList";
	}
	@GetMapping(value = "/properties/{propertyId}/show")
	public String initShowForm(@PathVariable("propertyId") int propertyId, ModelMap model) {
		Property property = this.propertyService.findPropertyById(propertyId);
		model.put("property", property);
		return VIEWS_PROPERTIES_SHOW;
	}
	
	
//	-------------------------- Property creation -----------------------------
	
	@GetMapping(value = "/properties/new")
	public String initCreateForm(ModelMap model) {
		Property property = new Property();

		model.put("property", property);
		return VIEWS_PROPERTIES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/properties/new")
	public String processCreationForm(@RequestParam(name = "propertyType", required = true) String propertyType ,@Valid Property property, BindingResult result) {

		if (result.hasErrors()) {
			return VIEWS_PROPERTIES_CREATE_OR_UPDATE_FORM;
		}
		else {
			if(propertyType == "House") {
				property.setPropertyType(0);
			}else {
				property.setPropertyType(1);
			}
			//creating owner, user and authorities
			this.propertyService.saveProperty(property);
			
			return "redirect:/properties/" + property.getId() +"/show";
		}
	}
	
	
//	-------------------------- Property edit -----------------------------
	
	@GetMapping(value = "/properties/{propertyId}/edit")
	public String initUpdateForm(@PathVariable("propertyId") int propertyId, ModelMap model) {
		Property property = this.propertyService.findPropertyById(propertyId);
		model.put("property", property);
		return VIEWS_PROPERTIES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/properties/{propertyId}/edit")
	public String processUpdateForm(@PathVariable("propertyId") int propertyId,@Valid Property property, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_PROPERTIES_CREATE_OR_UPDATE_FORM;
		}
		else {
			Property propertyD = this.propertyService.findPropertyById(propertyId);
			
			String address = property.getAddress();
			String city = property.getCity();
			String description = property.getDescription();
			Integer propertyType = property.getPropertyType();
			Integer surface = property.getSurface();
			Integer totalRooms = property.getTotalRooms();
			
			propertyD.setAddress(address);
			propertyD.setCity(city);		
			propertyD.setDescription(description);			
			propertyD.setPropertyType(propertyType);
			propertyD.setSurface(surface);
			propertyD.setTotalRooms(totalRooms);				
			
			
			this.propertyService.saveProperty(propertyD);
			
			
			return "redirect:/properties/" + propertyId +"/show";
		}
	}
	
	@GetMapping(value = { "/properties.xml"})
	public @ResponseBody Properties showResourcesVetList() {		
		Properties properties = new Properties();
		properties.getPropertyList().addAll(this.propertyService.findAll());
		return properties;
	}
}
