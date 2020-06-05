/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.StudentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
public class UserController {

	private static final String VIEWS_OWNER_CREATE_FORM = "users/createOwnerForm";
	private static final String VIEWS_STUDENT_CREATE_FORM = "users/createStudentForm";

	
	private final OwnerService ownerService;
	private final StudentService studentService;

	@Autowired
	public UserController(OwnerService ownerService, StudentService studentService, UserService userService) {
		this.ownerService = ownerService;
		this.studentService =studentService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping(value ="/users/new")
	public String getUserType(Map<String, Object> model) {
		
	    return "users/register";
		
	}


	@GetMapping(value = "/users/new/owner")
	public String initOwnerCreationForm(Map<String, Object> model) {
		Owner owner = new Owner();
		model.put("owner", owner);
		return VIEWS_OWNER_CREATE_FORM;
	}

	@PostMapping(value = "/users/new/owner")
	public String processOwnerCreationForm(@Valid Owner owner, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_FORM;
		}
		else {
			//creating owner, user, and authority
			this.ownerService.saveOwner(owner);
		return "redirect:/";
		}
	}
	
	@GetMapping(value = "/users/new/student")
	public String initStudentCreationForm(Map<String, Object> model) {
		Student student = new Student();
		model.put("student", student);
		return VIEWS_STUDENT_CREATE_FORM;
	}

	@PostMapping(value = "/users/new/student")
	public String processStudentCreationForm(@Valid Student student, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_STUDENT_CREATE_FORM;
		}
		else {
			//creating owner, user, and authority
			this.studentService.saveStudent(student);
			
		
			return "redirect:/";
		}
	}

}
