package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.jpa.SqlScriptEntity;

public class SqlScriptEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public SqlScriptEntity newSqlScriptEntity() {

		Integer id = mockValues.nextInteger();

		SqlScriptEntity sqlScriptEntity = new SqlScriptEntity();
		sqlScriptEntity.setId(id);
		return sqlScriptEntity;
	}
	
}
