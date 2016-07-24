/*
 * Created on 4 Jul 2016 ( Time 10:11:22 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */

package com.kumasi.techjournal.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.kumasi.techjournal.domain.jpa.RestCallEntity;
import com.kumasi.techjournal.persistence.commons.jpa.GenericJpaService;
import com.kumasi.techjournal.persistence.commons.jpa.JpaOperation;
import com.kumasi.techjournal.persistence.services.RestCallPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "RestCall" )
 * 
 * @author Telosys Tools Generator
 *
 */
public class RestCallPersistenceJPA extends GenericJpaService<RestCallEntity, Integer> implements RestCallPersistence {

	/**
	 * Constructor
	 */
	public RestCallPersistenceJPA() {
		super(RestCallEntity.class);
	}

	@Override
	public RestCallEntity load( Integer id ) {
		return super.load( id );
	}

	@Override
	public boolean delete( Integer id ) {
		return super.delete( id );
	}

	@Override
	public boolean delete(RestCallEntity entity) {
		if ( entity != null ) {
			return super.delete( entity.getId() );
		}
		return false ;
	}

	@Override
	public long countAll() {
		// JPA operation definition 
		JpaOperation operation = new JpaOperation() {
			@Override
			public Object exectue(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery("RestCallEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}