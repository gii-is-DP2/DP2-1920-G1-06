package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Properties {

	private List<Property> properties;

	@XmlElement
	public List<Property> getPropertyList() {
		if (properties == null) {
			properties = new ArrayList<>();
		}
		return properties;
	}

	
	
}
