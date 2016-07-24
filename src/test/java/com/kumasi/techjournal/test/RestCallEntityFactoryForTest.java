package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.jpa.RestCallEntity;

public class RestCallEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public RestCallEntity newRestCallEntity() {

		Integer id = mockValues.nextInteger();

		RestCallEntity restCallEntity = new RestCallEntity();
		restCallEntity.setId(id);
		return restCallEntity;
	}
	
}
