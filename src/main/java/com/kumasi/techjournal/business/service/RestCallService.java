/*
 * Created on 2 Jul 2016 ( Time 10:09:28 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.business.service;

import java.util.List;

import com.kumasi.techjournal.domain.RestCall;

/**
 * Business Service Interface for entity RestCall.
 */
public interface RestCallService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	RestCall findById( Integer id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<RestCall> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	RestCall save(RestCall entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	RestCall update(RestCall entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	RestCall create(RestCall entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Integer id );


}
