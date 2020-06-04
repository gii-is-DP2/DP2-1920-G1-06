package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Properties {

	private List<Property> listOfproperties;

	@XmlElement
	public List<Property> getPropertyList() {
		if (listOfproperties == null) {
			listOfproperties = new ArrayList<>();
		}
		return listOfproperties;
	}

	
	
}
