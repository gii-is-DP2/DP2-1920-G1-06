package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.StudentService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController {

		private static final String VIEWS_STUDENT_CREATE_OR_UPDATE_FORM = "students/createOrUpdateStudentForm";

		private final StudentService studentService;
		

		@Autowired
		public StudentController(StudentService studentService,UserService userService, AuthoritiesService authoritiesService) {
			this.studentService = studentService;
		}

		@InitBinder
		public void setAllowedFields(WebDataBinder dataBinder) {
			dataBinder.setDisallowedFields("id");
		}

		@GetMapping(value = "/students/new")
		public String initCreationForm(Map<String, Object> model) {
			Student student = new Student();
			model.put("student", student);
			return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
		}

		@PostMapping(value = "/students/new")
		public String processCreationForm(@Valid Student student, BindingResult result) {
			if (result.hasErrors()) {
				return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
			}
			else {
				//creating owner, user and authorities
				this.studentService.saveStudent(student);
				
				return "redirect:/students/" + student.getId();
			}
		}

		@GetMapping(value = "/owners/find")
		public String initFindForm(Map<String, Object> model) {
			model.put("owner", new Owner());
			return "owners/findOwners";
		}

		@GetMapping(value = "/students")
		public String processFindForm(Student student, BindingResult result, Map<String, Object> model) {

			// allow parameterless GET request for /owners to return all records
			if (student.getLastName() == null) {
				student.setLastName(""); // empty string signifies broadest possible search
			}

			// find owners by last name
			Collection<Student> results = this.studentService.findStudentByLastName(student.getLastName());
			if (results.isEmpty()) {
				// no owners found
				result.rejectValue("lastName", "notFound", "not found");
				return "students/findStudents";
			}
			else if (results.size() == 1) {
				// 1 owner found
				student = results.iterator().next();
				return "redirect:/owners/" + student.getId();
			}
			else {
				// multiple owners found
				model.put("selections", results);
				return "students/studentsList";
			}
		}

		@GetMapping(value = "/students/{studentId}/edit")
		public String initUpdateStudentForm(@PathVariable("studentId") int studentId, Model model) {
			Student student = this.studentService.findStudentById(studentId);
			model.addAttribute(student);
			return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
		}

		@PostMapping(value = "/students/{studentId}/edit")
		public String processUpdateOwnerForm(@Valid Student student, BindingResult result,
				@PathVariable("studentId") int studentId) {
			if (result.hasErrors()) {
				return VIEWS_STUDENT_CREATE_OR_UPDATE_FORM;
			}
			else {
				student.setId(studentId);
				this.studentService.saveStudent(student);
				return "redirect:/students/{studentId}";
			}
		}

		/**
		 * Custom handler for displaying an owner.
		 * @param ownerId the ID of the owner to display
		 * @return a ModelMap with the model attributes for the view
		 */
		@GetMapping("/students/{studentId}")
		public ModelAndView showStudent(@PathVariable("studentId") int studentId) {
			ModelAndView mav = new ModelAndView("owners/ownerDetails");
			mav.addObject(this.studentService.findStudentById(studentId));
			return mav;
		}

	}



