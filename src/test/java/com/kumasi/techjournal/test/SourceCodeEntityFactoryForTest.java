package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.jpa.SourceCodeEntity;

public class SourceCodeEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public SourceCodeEntity newSourceCodeEntity() {

		Integer id = mockValues.nextInteger();

		SourceCodeEntity sourceCodeEntity = new SourceCodeEntity();
		sourceCodeEntity.setId(id);
		return sourceCodeEntity;
	}
	
}
