package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.jpa.TechNoteEntity;

public class TechNoteEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public TechNoteEntity newTechNoteEntity() {

		Integer id = mockValues.nextInteger();

		TechNoteEntity techNoteEntity = new TechNoteEntity();
		techNoteEntity.setId(id);
		return techNoteEntity;
	}
	
}
