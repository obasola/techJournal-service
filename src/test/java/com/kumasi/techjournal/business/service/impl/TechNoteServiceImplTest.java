/*
 * Created on 2 Jul 2016 ( Time 10:09:19 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.kumasi.techjournal.domain.TechNote;
import com.kumasi.techjournal.domain.jpa.TechNoteEntity;
import java.util.Date;
import java.util.List;
import com.kumasi.techjournal.business.service.mapping.TechNoteServiceMapper;
import com.kumasi.techjournal.persistence.services.jpa.TechNotePersistenceJPA;
import com.kumasi.techjournal.test.TechNoteFactoryForTest;
import com.kumasi.techjournal.test.TechNoteEntityFactoryForTest;
import com.kumasi.techjournal.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of TechNoteService
 */
@RunWith(MockitoJUnitRunner.class)
public class TechNoteServiceImplTest {

	@InjectMocks
	private TechNoteServiceImpl techNoteService;
	@Mock
	private TechNotePersistenceJPA techNotePersistenceJPA;
	@Mock
	private TechNoteServiceMapper techNoteServiceMapper;
	
	private TechNoteFactoryForTest techNoteFactoryForTest = new TechNoteFactoryForTest();

	private TechNoteEntityFactoryForTest techNoteEntityFactoryForTest = new TechNoteEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		TechNoteEntity techNoteEntity = techNotePersistenceJPA.load(id);
		
		TechNote techNote = techNoteFactoryForTest.newTechNote();
		when(techNoteServiceMapper.mapTechNoteEntityToTechNote(techNoteEntity)).thenReturn(techNote);

		// When
		TechNote techNoteFound = techNoteService.findById(id);

		// Then
		assertEquals(techNote.getId(),techNoteFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<TechNoteEntity> techNoteEntitys = new ArrayList<TechNoteEntity>();
		TechNoteEntity techNoteEntity1 = techNoteEntityFactoryForTest.newTechNoteEntity();
		techNoteEntitys.add(techNoteEntity1);
		TechNoteEntity techNoteEntity2 = techNoteEntityFactoryForTest.newTechNoteEntity();
		techNoteEntitys.add(techNoteEntity2);
		when(techNotePersistenceJPA.loadAll()).thenReturn(techNoteEntitys);
		
		TechNote techNote1 = techNoteFactoryForTest.newTechNote();
		when(techNoteServiceMapper.mapTechNoteEntityToTechNote(techNoteEntity1)).thenReturn(techNote1);
		TechNote techNote2 = techNoteFactoryForTest.newTechNote();
		when(techNoteServiceMapper.mapTechNoteEntityToTechNote(techNoteEntity2)).thenReturn(techNote2);

		// When
		List<TechNote> techNotesFounds = techNoteService.findAll();

		// Then
		assertTrue(techNote1 == techNotesFounds.get(0));
		assertTrue(techNote2 == techNotesFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		TechNote techNote = techNoteFactoryForTest.newTechNote();

		TechNoteEntity techNoteEntity = techNoteEntityFactoryForTest.newTechNoteEntity();
		when(techNotePersistenceJPA.load(techNote.getId())).thenReturn(null);
		
		techNoteEntity = new TechNoteEntity();
		techNoteServiceMapper.mapTechNoteToTechNoteEntity(techNote, techNoteEntity);
		TechNoteEntity techNoteEntitySaved = techNotePersistenceJPA.save(techNoteEntity);
		
		TechNote techNoteSaved = techNoteFactoryForTest.newTechNote();
		when(techNoteServiceMapper.mapTechNoteEntityToTechNote(techNoteEntitySaved)).thenReturn(techNoteSaved);

		// When
		TechNote techNoteResult = techNoteService.create(techNote);

		// Then
		assertTrue(techNoteResult == techNoteSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		TechNote techNote = techNoteFactoryForTest.newTechNote();

		TechNoteEntity techNoteEntity = techNoteEntityFactoryForTest.newTechNoteEntity();
		when(techNotePersistenceJPA.load(techNote.getId())).thenReturn(techNoteEntity);

		// When
		Exception exception = null;
		try {
			techNoteService.create(techNote);
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
		TechNote techNote = techNoteFactoryForTest.newTechNote();

		TechNoteEntity techNoteEntity = techNoteEntityFactoryForTest.newTechNoteEntity();
		when(techNotePersistenceJPA.load(techNote.getId())).thenReturn(techNoteEntity);
		
		TechNoteEntity techNoteEntitySaved = techNoteEntityFactoryForTest.newTechNoteEntity();
		when(techNotePersistenceJPA.save(techNoteEntity)).thenReturn(techNoteEntitySaved);
		
		TechNote techNoteSaved = techNoteFactoryForTest.newTechNote();
		when(techNoteServiceMapper.mapTechNoteEntityToTechNote(techNoteEntitySaved)).thenReturn(techNoteSaved);

		// When
		TechNote techNoteResult = techNoteService.update(techNote);

		// Then
		verify(techNoteServiceMapper).mapTechNoteToTechNoteEntity(techNote, techNoteEntity);
		assertTrue(techNoteResult == techNoteSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		techNoteService.delete(id);

		// Then
		verify(techNotePersistenceJPA).delete(id);
		
	}

}