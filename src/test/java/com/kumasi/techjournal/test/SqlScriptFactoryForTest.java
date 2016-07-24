package com.kumasi.techjournal.test;

import com.kumasi.techjournal.domain.SqlScript;

public class SqlScriptFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public SqlScript newSqlScript() {

		Integer id = mockValues.nextInteger();

		SqlScript sqlScript = new SqlScript();
		sqlScript.setId(id);
		return sqlScript;
	}
	
}
