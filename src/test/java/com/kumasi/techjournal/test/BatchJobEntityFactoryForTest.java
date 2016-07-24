package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.jpa.BatchJobEntity;

public class BatchJobEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public BatchJobEntity newBatchJobEntity() {

		Integer id = mockValues.nextInteger();

		BatchJobEntity batchJobEntity = new BatchJobEntity();
		batchJobEntity.setId(id);
		return batchJobEntity;
	}
	
}
