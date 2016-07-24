/*
 * Created on 4 Jul 2016 ( Time 10:11:23 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.persistence.services;

import java.util.List;
import java.util.Map;

import com.kumasi.techjournal.domain.jpa.SourceTypeEntity;

/**
 * Basic persistence operations for entity "SourceType"
 * 
 * This Bean has a basic Primary Key : Integer
 *
 * @author Telosys Tools Generator
 *
 */
public interface SourceTypePersistence {

	/**
	 * Deletes the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param sourceType
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(SourceTypeEntity sourceType) ;

	/**
	 * Deletes the entity by its Primary Key <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param id
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(Integer id) ;

	/**
	 * Inserts the given entity and commit <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param sourceType
	 */
	public void insert(SourceTypeEntity sourceType) ;

	/**
	 * Loads the entity for the given Primary Key <br>
	 * @param id
	 * @return the entity loaded (or null if not found)
	 */
	public SourceTypeEntity load(Integer id) ;

	/**
	 * Loads ALL the entities (use with caution)
	 * @return
	 */
	public List<SourceTypeEntity> loadAll() ;

	/**
	 * Loads a list of entities using the given "named query" without parameter 
	 * @param queryName
	 * @return
	 */
	public List<SourceTypeEntity> loadByNamedQuery(String queryName) ;

	/**
	 * Loads a list of entities using the given "named query" with parameters 
	 * @param queryName
	 * @param queryParameters
	 * @return
	 */
	public List<SourceTypeEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) ;

	/**
	 * Saves (create or update) the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param sourceType
	 * @return
	 */
	public SourceTypeEntity save(SourceTypeEntity sourceType) ;

	/**
	 * Search the entities matching the given search criteria
	 * @param criteria
	 * @return
	 */
	public List<SourceTypeEntity> search( Map<String, Object> criteria ) ;

	/**
	 * Count all the occurrences
	 * @return
	 */
	public long countAll();
	
}
