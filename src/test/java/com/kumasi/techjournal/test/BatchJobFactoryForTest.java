package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.BatchJob;

public class BatchJobFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public BatchJob newBatchJob() {

		Integer id = mockValues.nextInteger();

		BatchJob batchJob = new BatchJob();
		batchJob.setId(id);
		return batchJob;
	}
	
}
