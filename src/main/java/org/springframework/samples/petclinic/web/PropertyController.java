package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Properties;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PropertyController {

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
	
	@GetMapping(value = { "/properties.xml"})
	public @ResponseBody Properties showResourcesVetList() {		
		Properties properties = new Properties();
		properties.getPropertyList().addAll(this.propertyService.findAll());
		return properties;
	}
}
