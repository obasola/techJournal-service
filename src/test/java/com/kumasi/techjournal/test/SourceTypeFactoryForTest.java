package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.SourceType;

public class SourceTypeFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public SourceType newSourceType() {

		Integer id = mockValues.nextInteger();

		SourceType sourceType = new SourceType();
		sourceType.setId(id);
		return sourceType;
	}
	
}
