package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.Application;

public class ApplicationFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Application newApplication() {

		Integer id = mockValues.nextInteger();

		Application application = new Application();
		application.setId(id);
		return application;
	}
	
}
