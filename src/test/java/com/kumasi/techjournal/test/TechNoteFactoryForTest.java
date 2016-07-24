package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.TechNote;

public class TechNoteFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public TechNote newTechNote() {

		Integer id = mockValues.nextInteger();

		TechNote techNote = new TechNote();
		techNote.setId(id);
		return techNote;
	}
	
}
