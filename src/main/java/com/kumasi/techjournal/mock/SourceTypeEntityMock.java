
/*
 * Created on 4 Jul 2016 ( Time 10:11:23 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.techjournal.domain.jpa.SourceTypeEntity;
import com.kumasi.techjournal.mock.tool.MockValues;

public class SourceTypeEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public SourceTypeEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public SourceTypeEntity createInstance( Integer id ) {
		SourceTypeEntity entity = new SourceTypeEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setCodeName( mockValues.nextString(45) ) ; // java.lang.String 
		// Init Link fields (if any)
		// setListOfSourceCode( TODO ) ; // List<SourceCode> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<SourceTypeEntity> createList(int count) {
		List<SourceTypeEntity> list = new LinkedList<SourceTypeEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}