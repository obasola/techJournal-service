/*
 * Created on 2 Jul 2016 ( Time 10:09:18 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.business.service.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.techjournal.domain.ReleaseNote;
import com.kumasi.techjournal.domain.jpa.ReleaseNoteEntity;
import com.kumasi.techjournal.domain.jpa.TechNoteEntity;
import com.kumasi.techjournal.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class ReleaseNoteServiceMapperTest {

	private ReleaseNoteServiceMapper releaseNoteServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		releaseNoteServiceMapper = new ReleaseNoteServiceMapper();
		releaseNoteServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'ReleaseNoteEntity' to 'ReleaseNote'
	 * @param releaseNoteEntity
	 */
	@Test
	public void testMapReleaseNoteEntityToReleaseNote() {
		// Given
		ReleaseNoteEntity releaseNoteEntity = new ReleaseNoteEntity();
		releaseNoteEntity.setTicketNumber(mockValues.nextString(15));
		releaseNoteEntity.setDatePlaced(mockValues.nextDate());
		releaseNoteEntity.setComment(mockValues.nextString(16777215));
		releaseNoteEntity.setDateModified(mockValues.nextDate());
		releaseNoteEntity.setTechNote(new TechNoteEntity());
		releaseNoteEntity.getTechNote().setId(mockValues.nextInteger());
		
		// When
		ReleaseNote releaseNote = releaseNoteServiceMapper.mapReleaseNoteEntityToReleaseNote(releaseNoteEntity);
		
		// Then
		assertEquals(releaseNoteEntity.getTicketNumber(), releaseNote.getTicketNumber());
		assertEquals(releaseNoteEntity.getDatePlaced(), releaseNote.getDatePlaced());
		assertEquals(releaseNoteEntity.getComment(), releaseNote.getComment());
		assertEquals(releaseNoteEntity.getDateModified(), releaseNote.getDateModified());
		assertEquals(releaseNoteEntity.getTechNote().getId(), releaseNote.getTechNoteId());
	}
	
	/**
	 * Test : Mapping from 'ReleaseNote' to 'ReleaseNoteEntity'
	 */
	@Test
	public void testMapReleaseNoteToReleaseNoteEntity() {
		// Given
		ReleaseNote releaseNote = new ReleaseNote();
		releaseNote.setTicketNumber(mockValues.nextString(15));
		releaseNote.setDatePlaced(mockValues.nextDate());
		releaseNote.setComment(mockValues.nextString(16777215));
		releaseNote.setDateModified(mockValues.nextDate());
		releaseNote.setTechNoteId(mockValues.nextInteger());

		ReleaseNoteEntity releaseNoteEntity = new ReleaseNoteEntity();
		
		// When
		releaseNoteServiceMapper.mapReleaseNoteToReleaseNoteEntity(releaseNote, releaseNoteEntity);
		
		// Then
		assertEquals(releaseNote.getTicketNumber(), releaseNoteEntity.getTicketNumber());
		assertEquals(releaseNote.getDatePlaced(), releaseNoteEntity.getDatePlaced());
		assertEquals(releaseNote.getComment(), releaseNoteEntity.getComment());
		assertEquals(releaseNote.getDateModified(), releaseNoteEntity.getDateModified());
		assertEquals(releaseNote.getTechNoteId(), releaseNoteEntity.getTechNote().getId());
	}

}