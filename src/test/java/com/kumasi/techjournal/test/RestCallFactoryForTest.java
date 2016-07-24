package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.RestCall;

public class RestCallFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public RestCall newRestCall() {

		Integer id = mockValues.nextInteger();

		RestCall restCall = new RestCall();
		restCall.setId(id);
		return restCall;
	}
	
}
