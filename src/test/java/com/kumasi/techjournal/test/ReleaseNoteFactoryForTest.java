package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.ReleaseNote;

public class ReleaseNoteFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ReleaseNote newReleaseNote() {

		Integer id = mockValues.nextInteger();

		ReleaseNote releaseNote = new ReleaseNote();
		releaseNote.setId(id);
		return releaseNote;
	}
	
}
