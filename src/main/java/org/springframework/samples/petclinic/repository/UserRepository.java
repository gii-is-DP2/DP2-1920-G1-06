package org.springframework.samples.petclinic.repository;


import javax.validation.Valid;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.User;


public interface UserRepository {
	
//	User findById(int id) throws DataAccessException;
//	
//	User findByUsername(String username) throws DataAccessException;

	void save(@Valid User user) throws DataAccessException;

	

}
