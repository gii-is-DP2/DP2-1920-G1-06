package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Properties;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PropertyController {

	private static final String VIEWS_PROPERTIES_CREATE_OR_UPDATE_FORM = "properties/createOrUpdatePropertyForm";
	
	private final PropertyService propertyService;
	
	@Autowired
	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
	
	
	@GetMapping(value = {"/properties"})
	public String showPropertyList(Map<String, Object> model) {
		Properties properties = new Properties();
		
		properties.getPropertyList().addAll(this.propertyService.findAll());
		
		model.put("properties", properties);
		return "properties/propertiesList";
	}
	@GetMapping(value = "/properties/{propertyId}/edit")
	public String initUpdateForm(@PathVariable("propertyId") int propertyId, ModelMap model) {
		Property property = this.propertyService.findPropertyById(propertyId);
		model.put("property", property);
		return VIEWS_PROPERTIES_CREATE_OR_UPDATE_FORM;
	}
	
	@GetMapping(value = { "/properties.xml"})
	public @ResponseBody Properties showResourcesVetList() {		
		Properties properties = new Properties();
		properties.getPropertyList().addAll(this.propertyService.findAll());
		return properties;
	}
}
