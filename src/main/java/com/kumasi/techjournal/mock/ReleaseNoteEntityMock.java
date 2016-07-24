
/*
 * Created on 4 Jul 2016 ( Time 10:11:22 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.techjournal.domain.jpa.ReleaseNoteEntity;
import com.kumasi.techjournal.mock.tool.MockValues;

public class ReleaseNoteEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public ReleaseNoteEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public ReleaseNoteEntity createInstance( Integer id ) {
		ReleaseNoteEntity entity = new ReleaseNoteEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setTicketNumber( mockValues.nextString(15) ) ; // java.lang.String 
		entity.setDatePlaced( mockValues.nextDate() ) ; // java.util.Date 
		entity.setComment( mockValues.nextString(16777215) ) ; // java.lang.String 
		entity.setDateModified( mockValues.nextDate() ) ; // java.util.Date 
		// Init Link fields (if any)
		// setTechNote( TODO ) ; // TechNote 
		// setListOfSqlScript( TODO ) ; // List<SqlScript> 
		// setListOfRestCall( TODO ) ; // List<RestCall> 
		// setListOfBatchJob( TODO ) ; // List<BatchJob> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<ReleaseNoteEntity> createList(int count) {
		List<ReleaseNoteEntity> list = new LinkedList<ReleaseNoteEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
