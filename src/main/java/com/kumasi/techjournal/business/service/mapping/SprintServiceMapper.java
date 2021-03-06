/*
 * Created on 4 Jul 2016 ( Time 10:14:39 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.techjournal.domain.Sprint;
import com.kumasi.techjournal.domain.jpa.SprintEntity;
import com.kumasi.techjournal.domain.jpa.ApplicationEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class SprintServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public SprintServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'SprintEntity' to 'Sprint'
	 * @param sprintEntity
	 */
	public Sprint mapSprintEntityToSprint(SprintEntity sprintEntity) {
		if(sprintEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Sprint sprint = map(sprintEntity, Sprint.class);

		//--- Link mapping ( link to Application )
		if(sprintEntity.getApplication() != null) {
			sprint.setApplicationId(sprintEntity.getApplication().getId());
		}
		return sprint;
	}
	
	/**
	 * Mapping from 'Sprint' to 'SprintEntity'
	 * @param sprint
	 * @param sprintEntity
	 */
	public void mapSprintToSprintEntity(Sprint sprint, SprintEntity sprintEntity) {
		if(sprint == null) {
			return;
		}

		//--- Generic mapping 
		map(sprint, sprintEntity);

		//--- Link mapping ( link : sprint )
		if( hasLinkToApplication(sprint) ) {
			ApplicationEntity application1 = new ApplicationEntity();
			application1.setId( sprint.getApplicationId() );
			sprintEntity.setApplication( application1 );
		} else {
			sprintEntity.setApplication( null );
		}

	}
	
	/**
	 * Verify that Application id is valid.
	 * @param Application Application
	 * @return boolean
	 */
	private boolean hasLinkToApplication(Sprint sprint) {
		if(sprint.getApplicationId() != null) {
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