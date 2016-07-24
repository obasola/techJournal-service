
/*
 * Created on 4 Jul 2016 ( Time 10:14:10 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.techjournal.domain.jpa.ApplicationEntity;
import com.kumasi.techjournal.mock.tool.MockValues;

public class ApplicationEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public ApplicationEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public ApplicationEntity createInstance( Integer id ) {
		ApplicationEntity entity = new ApplicationEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setName( mockValues.nextString(45) ) ; // java.lang.String 
		// Init Link fields (if any)
		// setListOfSprint( TODO ) ; // List<Sprint> 
		// setListOfTechNote( TODO ) ; // List<TechNote> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<ApplicationEntity> createList(int count) {
		List<ApplicationEntity> list = new LinkedList<ApplicationEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}