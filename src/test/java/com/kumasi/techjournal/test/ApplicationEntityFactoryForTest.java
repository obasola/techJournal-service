package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.jpa.ApplicationEntity;

public class ApplicationEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ApplicationEntity newApplicationEntity() {

		Integer id = mockValues.nextInteger();

		ApplicationEntity applicationEntity = new ApplicationEntity();
		applicationEntity.setId(id);
		return applicationEntity;
	}
	
}
