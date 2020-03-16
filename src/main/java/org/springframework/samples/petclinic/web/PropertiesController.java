package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;


@Controller
public class PropertiesController {

	private final PropertyService propertyService;
	
	@Autowired
	public PropertiesController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
	
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value = "/properties/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("property", new Property());
		return "owners/findOwners";
	}
}
