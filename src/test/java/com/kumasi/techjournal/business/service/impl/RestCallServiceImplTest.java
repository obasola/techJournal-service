/*
 * Created on 2 Jul 2016 ( Time 10:09:18 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.kumasi.techjournal.domain.RestCall;
import com.kumasi.techjournal.domain.jpa.RestCallEntity;
import java.util.Date;
import com.kumasi.techjournal.business.service.mapping.RestCallServiceMapper;
import com.kumasi.techjournal.persistence.services.jpa.RestCallPersistenceJPA;
import com.kumasi.techjournal.test.RestCallFactoryForTest;
import com.kumasi.techjournal.test.RestCallEntityFactoryForTest;
import com.kumasi.techjournal.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of RestCallService
 */
@RunWith(MockitoJUnitRunner.class)
public class RestCallServiceImplTest {

	@InjectMocks
	private RestCallServiceImpl restCallService;
	@Mock
	private RestCallPersistenceJPA restCallPersistenceJPA;
	@Mock
	private RestCallServiceMapper restCallServiceMapper;
	
	private RestCallFactoryForTest restCallFactoryForTest = new RestCallFactoryForTest();

	private RestCallEntityFactoryForTest restCallEntityFactoryForTest = new RestCallEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		RestCallEntity restCallEntity = restCallPersistenceJPA.load(id);
		
		RestCall restCall = restCallFactoryForTest.newRestCall();
		when(restCallServiceMapper.mapRestCallEntityToRestCall(restCallEntity)).thenReturn(restCall);

		// When
		RestCall restCallFound = restCallService.findById(id);

		// Then
		assertEquals(restCall.getId(),restCallFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<RestCallEntity> restCallEntitys = new ArrayList<RestCallEntity>();
		RestCallEntity restCallEntity1 = restCallEntityFactoryForTest.newRestCallEntity();
		restCallEntitys.add(restCallEntity1);
		RestCallEntity restCallEntity2 = restCallEntityFactoryForTest.newRestCallEntity();
		restCallEntitys.add(restCallEntity2);
		when(restCallPersistenceJPA.loadAll()).thenReturn(restCallEntitys);
		
		RestCall restCall1 = restCallFactoryForTest.newRestCall();
		when(restCallServiceMapper.mapRestCallEntityToRestCall(restCallEntity1)).thenReturn(restCall1);
		RestCall restCall2 = restCallFactoryForTest.newRestCall();
		when(restCallServiceMapper.mapRestCallEntityToRestCall(restCallEntity2)).thenReturn(restCall2);

		// When
		List<RestCall> restCallsFounds = restCallService.findAll();

		// Then
		assertTrue(restCall1 == restCallsFounds.get(0));
		assertTrue(restCall2 == restCallsFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		RestCall restCall = restCallFactoryForTest.newRestCall();

		RestCallEntity restCallEntity = restCallEntityFactoryForTest.newRestCallEntity();
		when(restCallPersistenceJPA.load(restCall.getId())).thenReturn(null);
		
		restCallEntity = new RestCallEntity();
		restCallServiceMapper.mapRestCallToRestCallEntity(restCall, restCallEntity);
		RestCallEntity restCallEntitySaved = restCallPersistenceJPA.save(restCallEntity);
		
		RestCall restCallSaved = restCallFactoryForTest.newRestCall();
		when(restCallServiceMapper.mapRestCallEntityToRestCall(restCallEntitySaved)).thenReturn(restCallSaved);

		// When
		RestCall restCallResult = restCallService.create(restCall);

		// Then
		assertTrue(restCallResult == restCallSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		RestCall restCall = restCallFactoryForTest.newRestCall();

		RestCallEntity restCallEntity = restCallEntityFactoryForTest.newRestCallEntity();
		when(restCallPersistenceJPA.load(restCall.getId())).thenReturn(restCallEntity);

		// When
		Exception exception = null;
		try {
			restCallService.create(restCall);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		RestCall restCall = restCallFactoryForTest.newRestCall();

		RestCallEntity restCallEntity = restCallEntityFactoryForTest.newRestCallEntity();
		when(restCallPersistenceJPA.load(restCall.getId())).thenReturn(restCallEntity);
		
		RestCallEntity restCallEntitySaved = restCallEntityFactoryForTest.newRestCallEntity();
		when(restCallPersistenceJPA.save(restCallEntity)).thenReturn(restCallEntitySaved);
		
		RestCall restCallSaved = restCallFactoryForTest.newRestCall();
		when(restCallServiceMapper.mapRestCallEntityToRestCall(restCallEntitySaved)).thenReturn(restCallSaved);

		// When
		RestCall restCallResult = restCallService.update(restCall);

		// Then
		verify(restCallServiceMapper).mapRestCallToRestCallEntity(restCall, restCallEntity);
		assertTrue(restCallResult == restCallSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		restCallService.delete(id);

		// Then
		verify(restCallPersistenceJPA).delete(id);
		
	}

}
