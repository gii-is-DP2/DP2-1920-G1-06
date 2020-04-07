package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.repository.StudentRepository;

public interface SpringDataStudentRepository extends StudentRepository, Repository<Student, Integer> {
	
	@Override
	@Query("SELECT student FROM Student student  WHERE student.lastName LIKE :lastName%")
	public Collection<Student> findByLastName(@Param("lastName") String lastName);

	@Override
	@Query("SELECT student FROM Student student  WHERE student.id =:id")
	public Student findById(@Param("id") int id);

}
