
/*
 * Created on 4 Jul 2016 ( Time 10:11:22 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.techjournal.domain.jpa.RestCallEntity;
import com.kumasi.techjournal.mock.tool.MockValues;

public class RestCallEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public RestCallEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public RestCallEntity createInstance( Integer id ) {
		RestCallEntity entity = new RestCallEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setRestUrl( mockValues.nextString(65) ) ; // java.lang.String 
		entity.setApiKey( mockValues.nextString(75) ) ; // java.lang.String 
		entity.setComment( mockValues.nextString(16777215) ) ; // java.lang.String 
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
	public List<RestCallEntity> createList(int count) {
		List<RestCallEntity> list = new LinkedList<RestCallEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
