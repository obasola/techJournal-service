/*
 * Created on 2 Jul 2016 ( Time 10:09:18 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.techjournal.domain.Application;
import com.kumasi.techjournal.domain.jpa.ApplicationEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class ApplicationServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public ApplicationServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'ApplicationEntity' to 'Application'
	 * @param applicationEntity
	 */
	public Application mapApplicationEntityToApplication(ApplicationEntity applicationEntity) {
		if(applicationEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Application application = map(applicationEntity, Application.class);

		return application;
	}
	
	/**
	 * Mapping from 'Application' to 'ApplicationEntity'
	 * @param application
	 * @param applicationEntity
	 */
	public void mapApplicationToApplicationEntity(Application application, ApplicationEntity applicationEntity) {
		if(application == null) {
			return;
		}

		//--- Generic mapping 
		map(application, applicationEntity);

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