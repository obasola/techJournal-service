package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.jpa.ReleaseNoteEntity;

public class ReleaseNoteEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ReleaseNoteEntity newReleaseNoteEntity() {

		Integer id = mockValues.nextInteger();

		ReleaseNoteEntity releaseNoteEntity = new ReleaseNoteEntity();
		releaseNoteEntity.setId(id);
		return releaseNoteEntity;
	}
	
}
