package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Properties;
import org.springframework.samples.petclinic.model.Property;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PropertyService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PropertyController {

	private static final String VIEWS_PROPERTIES_CREATE_OR_UPDATE_FORM = "properties/createOrUpdatePropertyForm";
	private static final String VIEWS_PROPERTIES_SHOW = "properties/showForm";
	private static final String PROPERTY = "property";

	private final PropertyService propertyService;
	private final OwnerService ownerService;

	
	@Autowired
	public PropertyController(PropertyService propertyService,OwnerService ownerService,UserService userService) {
		this.propertyService = propertyService;
		this.ownerService = ownerService;
	}
	
	
	@GetMapping(value = {"/properties"})
	public String showPropertyList(Map<String, Object> model) {
		
		String dir = "properties/propertiesList";
		
		Properties properties = new Properties();
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Owner owner = this.ownerService.findOwnerByUsername(username);

		try {
			Collection<Property> prop = this.ownerService.findMyProperties(owner.getId());

			properties.getPropertyList().addAll(prop);

			model.put("properties", properties);
		}catch(NullPointerException e ) {
			
		}

		return dir;
	}
	
	
	@GetMapping(value = "/properties/{propertyId}/show")
	public String initShowForm(@PathVariable("propertyId") int propertyId, ModelMap model) {
		Property property = this.propertyService.findPropertyById(propertyId);
		model.put(PROPERTY, property);
		return VIEWS_PROPERTIES_SHOW;
	}
	
	
//	-------------------------- Property creation -----------------------------
	
	@GetMapping(value = "/properties/new")
	public String initCreateForm(ModelMap model) {
		Property property = new Property();

		model.put(PROPERTY, property);
		return VIEWS_PROPERTIES_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping(value = "/properties/new")
	public String processCreationForm(@RequestParam(name = "propertyType", required = true) String propertyType ,@Valid Property property, BindingResult result) throws Exception {

		if (result.hasErrors()) {
			return VIEWS_PROPERTIES_CREATE_OR_UPDATE_FORM;
		}
		else {
			
			property.setPropertyType(new Integer(propertyType));
			
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			
			Owner owner = this.ownerService.findOwnerByUsername(username);

			property.setOwner(owner);
			
			
			this.propertyService.saveProperty(property);
			
			return "redirect:/properties/" + property.getId() +"/show";
		}
	}
	
	
//	-------------------------- Property edit -----------------------------
	
	@GetMapping(value = "/properties/{propertyId}/edit")
	public String initUpdateForm(@PathVariable("propertyId") int propertyId, ModelMap model) {
		Property property = this.propertyService.findPropertyById(propertyId);
		model.put(PROPERTY, property);
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
	
	
//	-------------------------- Property find student -----------------------------

	@GetMapping(value = "/properties/find")
	public String initFindForm(Map<String, Object> model) {
		model.put(PROPERTY, new Property());
		return "properties/findPropertiesStudent";
	}
	
	@PostMapping(value = "/properties/find")
	public String processCreationForm(@RequestParam(name = "city", required = true)String location, Map<String, Object> model) throws Exception {

		Properties properties = new Properties();
		
		try {
			Collection<Property> prop = this.propertyService.findPropertyByLocation(location);

			properties.getPropertyList().addAll(prop);

			model.put("properties", properties);
		}catch(NullPointerException e ) {
			
		}
		return "properties/propertiesList";
	}
	
	@GetMapping(value = { "/properties.xml"})
	public @ResponseBody Properties showResourcesVetList() {		
		Properties properties = new Properties();
		properties.getPropertyList().addAll(this.propertyService.findAll());
		return properties;
	}
}
