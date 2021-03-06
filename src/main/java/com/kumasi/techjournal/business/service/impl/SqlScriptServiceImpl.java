/*
 * Created on 2 Jul 2016 ( Time 10:09:18 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kumasi.techjournal.domain.SqlScript;
import com.kumasi.techjournal.domain.jpa.SqlScriptEntity;
import java.util.Date;
import com.kumasi.techjournal.business.service.SqlScriptService;
import com.kumasi.techjournal.business.service.mapping.SqlScriptServiceMapper;
import com.kumasi.techjournal.persistence.PersistenceServiceProvider;
import com.kumasi.techjournal.persistence.services.SqlScriptPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of SqlScriptService
 */
@Component
public class SqlScriptServiceImpl implements SqlScriptService {

	private SqlScriptPersistence sqlScriptPersistence;

	@Resource
	private SqlScriptServiceMapper sqlScriptServiceMapper;
	
	public SqlScriptServiceImpl() {
		sqlScriptPersistence = PersistenceServiceProvider.getService(SqlScriptPersistence.class);
	}
		
	@Override
	public SqlScript findById(Integer id) {
		SqlScriptEntity entity = sqlScriptPersistence.load(id);
		return sqlScriptServiceMapper.mapSqlScriptEntityToSqlScript(entity);
	}

	@Override
	public List<SqlScript> findAll() {
		List<SqlScriptEntity> entities = sqlScriptPersistence.loadAll();
		List<SqlScript> beans = new ArrayList<SqlScript>();
		for(SqlScriptEntity entity : entities) {
			beans.add(sqlScriptServiceMapper.mapSqlScriptEntityToSqlScript(entity));
		}
		return beans;
	}

	@Override
	public SqlScript save(SqlScript sqlScript) {
		return update(sqlScript) ;
	}

	@Override
	public SqlScript create(SqlScript sqlScript) {
		if(sqlScript.getId() != null && sqlScriptPersistence.load(sqlScript.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		SqlScriptEntity sqlScriptEntity = new SqlScriptEntity();
		sqlScriptServiceMapper.mapSqlScriptToSqlScriptEntity(sqlScript, sqlScriptEntity);
		SqlScriptEntity sqlScriptEntitySaved = sqlScriptPersistence.save(sqlScriptEntity);
		return sqlScriptServiceMapper.mapSqlScriptEntityToSqlScript(sqlScriptEntitySaved);
	}

	@Override
	public SqlScript update(SqlScript sqlScript) {
		SqlScriptEntity sqlScriptEntity = sqlScriptPersistence.load(sqlScript.getId());
		sqlScriptServiceMapper.mapSqlScriptToSqlScriptEntity(sqlScript, sqlScriptEntity);
		SqlScriptEntity sqlScriptEntitySaved = sqlScriptPersistence.save(sqlScriptEntity);
		return sqlScriptServiceMapper.mapSqlScriptEntityToSqlScript(sqlScriptEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		sqlScriptPersistence.delete(id);
	}

	public SqlScriptPersistence getSqlScriptPersistence() {
		return sqlScriptPersistence;
	}

	public void setSqlScriptPersistence(SqlScriptPersistence sqlScriptPersistence) {
		this.sqlScriptPersistence = sqlScriptPersistence;
	}

	public SqlScriptServiceMapper getSqlScriptServiceMapper() {
		return sqlScriptServiceMapper;
	}

	public void setSqlScriptServiceMapper(SqlScriptServiceMapper sqlScriptServiceMapper) {
		this.sqlScriptServiceMapper = sqlScriptServiceMapper;
	}

}
