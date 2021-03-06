
/*
 * Created on 4 Jul 2016 ( Time 10:11:22 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.techjournal.domain.jpa.BatchJobEntity;
import com.kumasi.techjournal.mock.tool.MockValues;

public class BatchJobEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public BatchJobEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public BatchJobEntity createInstance( Integer id ) {
		BatchJobEntity entity = new BatchJobEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setJobName( mockValues.nextString(45) ) ; // java.lang.String 
		entity.setFileName( mockValues.nextString(65) ) ; // java.lang.String 
		entity.setData( mockValues.nextString(16777215) ) ; // java.lang.String 
		entity.setDatePlaced( mockValues.nextDate() ) ; // java.util.Date 
		entity.setDateModified( mockValues.nextDate() ) ; // java.util.Date 
		// Init Link fields (if any)
		// setReleaseNote( TODO ) ; // ReleaseNote 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<BatchJobEntity> createList(int count) {
		List<BatchJobEntity> list = new LinkedList<BatchJobEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
