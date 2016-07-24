package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.SourceCode;

public class SourceCodeFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public SourceCode newSourceCode() {

		Integer id = mockValues.nextInteger();

		SourceCode sourceCode = new SourceCode();
		sourceCode.setId(id);
		return sourceCode;
	}
	
}
