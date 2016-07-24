/*
 * Created on 2 Jul 2016 ( Time 10:09:07 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.test.persistence;


import com.kumasi.techjournal.domain.jpa.SourceCodeEntity;
import com.kumasi.techjournal.mock.SourceCodeEntityMock;
import com.kumasi.techjournal.persistence.PersistenceServiceProvider;
import com.kumasi.techjournal.persistence.services.SourceCodePersistence;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test case for SourceCode persistence service
 * 
 * @author Telosys Tools Generator
 *
 */
public class SourceCodePersistenceTest 
{
	@Test
	public void test1() {
		
		System.out.println("Test count ..." );
		
		SourceCodePersistence service = PersistenceServiceProvider.getService(SourceCodePersistence.class);
		System.out.println("CountAll = " + service.countAll() );
	}
	
	@Test
	public void test2() {
		
		System.out.println("Test SourceCode persistence : delete + load ..." );
		
		SourceCodePersistence service = PersistenceServiceProvider.getService(SourceCodePersistence.class);
		
		SourceCodeEntityMock mock = new SourceCodeEntityMock();
		
		// TODO : set primary key values here 
		process( service, mock, 0  );
		// process( service, mock, ... );
	}

	private void process(SourceCodePersistence service, SourceCodeEntityMock mock, Integer id ) {
		System.out.println("----- "  );
		System.out.println(" . load : " );
		SourceCodeEntity entity = service.load( id );
		if ( entity != null ) {
			// Found 
			System.out.println("   FOUND : " + entity );
			
			// Save (update) with the same values to avoid database integrity errors  
			System.out.println(" . save : " + entity );
			service.save(entity);
			System.out.println("   saved : " + entity );
		}
		else {
			// Not found 
			System.out.println("   NOT FOUND" );
			// Create a new instance 
			entity = mock.createInstance( id ) ;
			Assert.assertNotNull(entity);

			// This entity references the following entities : 
			// . SourceType
			// . TechNote
			/* Insert only if references are OK
			// Try to insert the new instance
			System.out.println(" . insert : " + entity );
			service.insert(entity);
			System.out.println("   inserted : " + entity );
			*/

			System.out.println(" . delete : " );
			boolean deleted = service.delete( id );
			System.out.println("   deleted = " + deleted);
		}		
	}
}