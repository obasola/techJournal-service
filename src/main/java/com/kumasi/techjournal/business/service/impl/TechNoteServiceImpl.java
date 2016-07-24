/*
 * Created on 2 Jul 2016 ( Time 10:09:19 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kumasi.techjournal.domain.TechNote;
import com.kumasi.techjournal.domain.jpa.TechNoteEntity;
import java.util.Date;
import java.util.List;
import com.kumasi.techjournal.business.service.TechNoteService;
import com.kumasi.techjournal.business.service.mapping.TechNoteServiceMapper;
import com.kumasi.techjournal.persistence.PersistenceServiceProvider;
import com.kumasi.techjournal.persistence.services.TechNotePersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of TechNoteService
 */
@Component
public class TechNoteServiceImpl implements TechNoteService {

	private TechNotePersistence techNotePersistence;

	@Resource
	private TechNoteServiceMapper techNoteServiceMapper;
	
	public TechNoteServiceImpl() {
		techNotePersistence = PersistenceServiceProvider.getService(TechNotePersistence.class);
	}
		
	@Override
	public TechNote findById(Integer id) {
		TechNoteEntity entity = techNotePersistence.load(id);
		return techNoteServiceMapper.mapTechNoteEntityToTechNote(entity);
	}

	@Override
	public List<TechNote> findAll() {
		List<TechNoteEntity> entities = techNotePersistence.loadAll();
		List<TechNote> beans = new ArrayList<TechNote>();
		for(TechNoteEntity entity : entities) {
			beans.add(techNoteServiceMapper.mapTechNoteEntityToTechNote(entity));
		}
		return beans;
	}

	@Override
	public TechNote save(TechNote techNote) {
		return update(techNote) ;
	}

	@Override
	public TechNote create(TechNote techNote) {
		if(techNote.getId() != null && techNotePersistence.load(techNote.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		TechNoteEntity techNoteEntity = new TechNoteEntity();
		techNoteServiceMapper.mapTechNoteToTechNoteEntity(techNote, techNoteEntity);
		TechNoteEntity techNoteEntitySaved = techNotePersistence.save(techNoteEntity);
		return techNoteServiceMapper.mapTechNoteEntityToTechNote(techNoteEntitySaved);
	}

	@Override
	public TechNote update(TechNote techNote) {
		TechNoteEntity techNoteEntity = techNotePersistence.load(techNote.getId());
		techNoteServiceMapper.mapTechNoteToTechNoteEntity(techNote, techNoteEntity);
		TechNoteEntity techNoteEntitySaved = techNotePersistence.save(techNoteEntity);
		return techNoteServiceMapper.mapTechNoteEntityToTechNote(techNoteEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		techNotePersistence.delete(id);
	}

	public TechNotePersistence getTechNotePersistence() {
		return techNotePersistence;
	}

	public void setTechNotePersistence(TechNotePersistence techNotePersistence) {
		this.techNotePersistence = techNotePersistence;
	}

	public TechNoteServiceMapper getTechNoteServiceMapper() {
		return techNoteServiceMapper;
	}

	public void setTechNoteServiceMapper(TechNoteServiceMapper techNoteServiceMapper) {
		this.techNoteServiceMapper = techNoteServiceMapper;
	}

}