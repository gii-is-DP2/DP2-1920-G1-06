package org.springframework.samples.petclinic.repository;


import java.util.Collection;

import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.User;


public interface UserRepository {
	
	
	User findByUsername(String username) throws DataAccessException;

	void save(@Valid User user) throws DataAccessException;

	Collection<User> findAll() throws DataAccessException;

	

}
