/*
 * Created on 2 Jul 2016 ( Time 10:09:18 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.techjournal.domain.BatchJob;
import com.kumasi.techjournal.domain.jpa.BatchJobEntity;
import com.kumasi.techjournal.domain.jpa.ReleaseNoteEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class BatchJobServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public BatchJobServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'BatchJobEntity' to 'BatchJob'
	 * @param batchJobEntity
	 */
	public BatchJob mapBatchJobEntityToBatchJob(BatchJobEntity batchJobEntity) {
		if(batchJobEntity == null) {
			return null;
		}

		//--- Generic mapping 
		BatchJob batchJob = map(batchJobEntity, BatchJob.class);

		//--- Link mapping ( link to ReleaseNote )
		if(batchJobEntity.getReleaseNote() != null) {
			batchJob.setReleaseNoteId(batchJobEntity.getReleaseNote().getId());
		}
		return batchJob;
	}
	
	/**
	 * Mapping from 'BatchJob' to 'BatchJobEntity'
	 * @param batchJob
	 * @param batchJobEntity
	 */
	public void mapBatchJobToBatchJobEntity(BatchJob batchJob, BatchJobEntity batchJobEntity) {
		if(batchJob == null) {
			return;
		}

		//--- Generic mapping 
		map(batchJob, batchJobEntity);

		//--- Link mapping ( link : batchJob )
		if( hasLinkToReleaseNote(batchJob) ) {
			ReleaseNoteEntity releaseNote1 = new ReleaseNoteEntity();
			releaseNote1.setId( batchJob.getReleaseNoteId() );
			batchJobEntity.setReleaseNote( releaseNote1 );
		} else {
			batchJobEntity.setReleaseNote( null );
		}

	}
	
	/**
	 * Verify that ReleaseNote id is valid.
	 * @param ReleaseNote ReleaseNote
	 * @return boolean
	 */
	private boolean hasLinkToReleaseNote(BatchJob batchJob) {
		if(batchJob.getReleaseNoteId() != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}