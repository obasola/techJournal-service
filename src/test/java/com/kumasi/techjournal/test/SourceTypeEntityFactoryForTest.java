package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.jpa.SourceTypeEntity;

public class SourceTypeEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public SourceTypeEntity newSourceTypeEntity() {

		Integer id = mockValues.nextInteger();

		SourceTypeEntity sourceTypeEntity = new SourceTypeEntity();
		sourceTypeEntity.setId(id);
		return sourceTypeEntity;
	}
	
}
