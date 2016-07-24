/*
 * Created on 4 Jul 2016 ( Time 10:11:22 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.persistence.services.fake;

import java.util.List;
import java.util.Map;

import com.kumasi.techjournal.domain.jpa.BatchJobEntity;
import com.kumasi.techjournal.persistence.commons.fake.GenericFakeService;
import com.kumasi.techjournal.persistence.services.BatchJobPersistence;

/**
 * Fake persistence service implementation ( entity "BatchJob" )
 *
 * @author Telosys Tools Generator
 */
public class BatchJobPersistenceFAKE extends GenericFakeService<BatchJobEntity> implements BatchJobPersistence {

	public BatchJobPersistenceFAKE () {
		super(BatchJobEntity.class);
	}
	
	protected BatchJobEntity buildEntity(int index) {
		BatchJobEntity entity = new BatchJobEntity();
		// Init fields with mock values
		entity.setId( nextInteger() ) ;
		entity.setJobName( nextString() ) ;
		entity.setFileName( nextString() ) ;
		entity.setData( nextString() ) ;
		entity.setDatePlaced( nextDate() ) ;
		entity.setDateModified( nextDate() ) ;
		return entity ;
	}
	
	
	public boolean delete(BatchJobEntity entity) {
		log("delete ( BatchJobEntity : " + entity + ")" ) ;
		return true;
	}

	public boolean delete( Integer id ) {
		log("delete ( PK )") ;
		return true;
	}

	public void insert(BatchJobEntity entity) {
		log("insert ( BatchJobEntity : " + entity + ")" ) ;
	}

	public BatchJobEntity load( Integer id ) {
		log("load ( PK )") ;
		return buildEntity(1) ; 
	}

	public List<BatchJobEntity> loadAll() {
		log("loadAll()") ;
		return buildList(40) ;
	}

	public List<BatchJobEntity> loadByNamedQuery(String queryName) {
		log("loadByNamedQuery ( '" + queryName + "' )") ;
		return buildList(20) ;
	}

	public List<BatchJobEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		log("loadByNamedQuery ( '" + queryName + "', parameters )") ;
		return buildList(10) ;
	}

	public BatchJobEntity save(BatchJobEntity entity) {
		log("insert ( BatchJobEntity : " + entity + ")" ) ;
		return entity;
	}

	public List<BatchJobEntity> search(Map<String, Object> criteria) {
		log("search (criteria)" ) ;
		return buildList(15) ;
	}

	@Override
	public long countAll() {
		return 0 ;
	}

}
