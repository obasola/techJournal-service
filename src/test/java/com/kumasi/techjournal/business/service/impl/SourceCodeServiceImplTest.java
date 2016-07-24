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

import com.kumasi.techjournal.domain.SourceCode;
import com.kumasi.techjournal.domain.jpa.SourceCodeEntity;
import java.util.Date;
import com.kumasi.techjournal.business.service.mapping.SourceCodeServiceMapper;
import com.kumasi.techjournal.persistence.services.jpa.SourceCodePersistenceJPA;
import com.kumasi.techjournal.test.SourceCodeFactoryForTest;
import com.kumasi.techjournal.test.SourceCodeEntityFactoryForTest;
import com.kumasi.techjournal.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of SourceCodeService
 */
@RunWith(MockitoJUnitRunner.class)
public class SourceCodeServiceImplTest {

	@InjectMocks
	private SourceCodeServiceImpl sourceCodeService;
	@Mock
	private SourceCodePersistenceJPA sourceCodePersistenceJPA;
	@Mock
	private SourceCodeServiceMapper sourceCodeServiceMapper;
	
	private SourceCodeFactoryForTest sourceCodeFactoryForTest = new SourceCodeFactoryForTest();

	private SourceCodeEntityFactoryForTest sourceCodeEntityFactoryForTest = new SourceCodeEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		SourceCodeEntity sourceCodeEntity = sourceCodePersistenceJPA.load(id);
		
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();
		when(sourceCodeServiceMapper.mapSourceCodeEntityToSourceCode(sourceCodeEntity)).thenReturn(sourceCode);

		// When
		SourceCode sourceCodeFound = sourceCodeService.findById(id);

		// Then
		assertEquals(sourceCode.getId(),sourceCodeFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<SourceCodeEntity> sourceCodeEntitys = new ArrayList<SourceCodeEntity>();
		SourceCodeEntity sourceCodeEntity1 = sourceCodeEntityFactoryForTest.newSourceCodeEntity();
		sourceCodeEntitys.add(sourceCodeEntity1);
		SourceCodeEntity sourceCodeEntity2 = sourceCodeEntityFactoryForTest.newSourceCodeEntity();
		sourceCodeEntitys.add(sourceCodeEntity2);
		when(sourceCodePersistenceJPA.loadAll()).thenReturn(sourceCodeEntitys);
		
		SourceCode sourceCode1 = sourceCodeFactoryForTest.newSourceCode();
		when(sourceCodeServiceMapper.mapSourceCodeEntityToSourceCode(sourceCodeEntity1)).thenReturn(sourceCode1);
		SourceCode sourceCode2 = sourceCodeFactoryForTest.newSourceCode();
		when(sourceCodeServiceMapper.mapSourceCodeEntityToSourceCode(sourceCodeEntity2)).thenReturn(sourceCode2);

		// When
		List<SourceCode> sourceCodesFounds = sourceCodeService.findAll();

		// Then
		assertTrue(sourceCode1 == sourceCodesFounds.get(0));
		assertTrue(sourceCode2 == sourceCodesFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();

		SourceCodeEntity sourceCodeEntity = sourceCodeEntityFactoryForTest.newSourceCodeEntity();
		when(sourceCodePersistenceJPA.load(sourceCode.getId())).thenReturn(null);
		
		sourceCodeEntity = new SourceCodeEntity();
		sourceCodeServiceMapper.mapSourceCodeToSourceCodeEntity(sourceCode, sourceCodeEntity);
		SourceCodeEntity sourceCodeEntitySaved = sourceCodePersistenceJPA.save(sourceCodeEntity);
		
		SourceCode sourceCodeSaved = sourceCodeFactoryForTest.newSourceCode();
		when(sourceCodeServiceMapper.mapSourceCodeEntityToSourceCode(sourceCodeEntitySaved)).thenReturn(sourceCodeSaved);

		// When
		SourceCode sourceCodeResult = sourceCodeService.create(sourceCode);

		// Then
		assertTrue(sourceCodeResult == sourceCodeSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();

		SourceCodeEntity sourceCodeEntity = sourceCodeEntityFactoryForTest.newSourceCodeEntity();
		when(sourceCodePersistenceJPA.load(sourceCode.getId())).thenReturn(sourceCodeEntity);

		// When
		Exception exception = null;
		try {
			sourceCodeService.create(sourceCode);
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
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();

		SourceCodeEntity sourceCodeEntity = sourceCodeEntityFactoryForTest.newSourceCodeEntity();
		when(sourceCodePersistenceJPA.load(sourceCode.getId())).thenReturn(sourceCodeEntity);
		
		SourceCodeEntity sourceCodeEntitySaved = sourceCodeEntityFactoryForTest.newSourceCodeEntity();
		when(sourceCodePersistenceJPA.save(sourceCodeEntity)).thenReturn(sourceCodeEntitySaved);
		
		SourceCode sourceCodeSaved = sourceCodeFactoryForTest.newSourceCode();
		when(sourceCodeServiceMapper.mapSourceCodeEntityToSourceCode(sourceCodeEntitySaved)).thenReturn(sourceCodeSaved);

		// When
		SourceCode sourceCodeResult = sourceCodeService.update(sourceCode);

		// Then
		verify(sourceCodeServiceMapper).mapSourceCodeToSourceCodeEntity(sourceCode, sourceCodeEntity);
		assertTrue(sourceCodeResult == sourceCodeSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		sourceCodeService.delete(id);

		// Then
		verify(sourceCodePersistenceJPA).delete(id);
		
	}

}
