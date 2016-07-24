package com.kumasi.techjournal.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import com.kumasi.techjournal.domain.SqlScript;
import com.kumasi.techjournal.domain.ReleaseNote;
import com.kumasi.techjournal.test.SqlScriptFactoryForTest;
import com.kumasi.techjournal.test.ReleaseNoteFactoryForTest;

//--- Services 
import com.kumasi.techjournal.business.service.SqlScriptService;
import com.kumasi.techjournal.business.service.ReleaseNoteService;

//--- List Items 
import com.kumasi.techjournal.web.listitem.ReleaseNoteListItem;

import com.kumasi.techjournal.web.common.Message;
import com.kumasi.techjournal.web.common.MessageHelper;
import com.kumasi.techjournal.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class SqlScriptControllerTest {
	
	@InjectMocks
	private SqlScriptController sqlScriptController;
	@Mock
	private SqlScriptService sqlScriptService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ReleaseNoteService releaseNoteService; // Injected by Spring

	private SqlScriptFactoryForTest sqlScriptFactoryForTest = new SqlScriptFactoryForTest();
	private ReleaseNoteFactoryForTest releaseNoteFactoryForTest = new ReleaseNoteFactoryForTest();

	List<ReleaseNote> releaseNotes = new ArrayList<ReleaseNote>();

	private void givenPopulateModel() {
		ReleaseNote releaseNote1 = releaseNoteFactoryForTest.newReleaseNote();
		ReleaseNote releaseNote2 = releaseNoteFactoryForTest.newReleaseNote();
		List<ReleaseNote> releaseNotes = new ArrayList<ReleaseNote>();
		releaseNotes.add(releaseNote1);
		releaseNotes.add(releaseNote2);
		when(releaseNoteService.findAll()).thenReturn(releaseNotes);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<SqlScript> list = new ArrayList<SqlScript>();
		when(sqlScriptService.findAll()).thenReturn(list);
		
		// When
		String viewName = sqlScriptController.list(model);
		
		// Then
		assertEquals("sqlScript/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = sqlScriptController.formForCreate(model);
		
		// Then
		assertEquals("sqlScript/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((SqlScript)modelMap.get("sqlScript")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/sqlScript/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		SqlScript sqlScript = sqlScriptFactoryForTest.newSqlScript();
		Integer id = sqlScript.getId();
		when(sqlScriptService.findById(id)).thenReturn(sqlScript);
		
		// When
		String viewName = sqlScriptController.formForUpdate(model, id);
		
		// Then
		assertEquals("sqlScript/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sqlScript, (SqlScript) modelMap.get("sqlScript"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/sqlScript/update", modelMap.get("saveAction"));
		
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		SqlScript sqlScript = sqlScriptFactoryForTest.newSqlScript();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		SqlScript sqlScriptCreated = new SqlScript();
		when(sqlScriptService.create(sqlScript)).thenReturn(sqlScriptCreated); 
		
		// When
		String viewName = sqlScriptController.create(sqlScript, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/sqlScript/form/"+sqlScript.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sqlScriptCreated, (SqlScript) modelMap.get("sqlScript"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		SqlScript sqlScript = sqlScriptFactoryForTest.newSqlScript();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = sqlScriptController.create(sqlScript, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sqlScript/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sqlScript, (SqlScript) modelMap.get("sqlScript"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/sqlScript/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		SqlScript sqlScript = sqlScriptFactoryForTest.newSqlScript();
		
		Exception exception = new RuntimeException("test exception");
		when(sqlScriptService.create(sqlScript)).thenThrow(exception);
		
		// When
		String viewName = sqlScriptController.create(sqlScript, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sqlScript/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sqlScript, (SqlScript) modelMap.get("sqlScript"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/sqlScript/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "sqlScript.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		SqlScript sqlScript = sqlScriptFactoryForTest.newSqlScript();
		Integer id = sqlScript.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		SqlScript sqlScriptSaved = new SqlScript();
		sqlScriptSaved.setId(id);
		when(sqlScriptService.update(sqlScript)).thenReturn(sqlScriptSaved); 
		
		// When
		String viewName = sqlScriptController.update(sqlScript, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/sqlScript/form/"+sqlScript.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sqlScriptSaved, (SqlScript) modelMap.get("sqlScript"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		SqlScript sqlScript = sqlScriptFactoryForTest.newSqlScript();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = sqlScriptController.update(sqlScript, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sqlScript/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sqlScript, (SqlScript) modelMap.get("sqlScript"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/sqlScript/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		SqlScript sqlScript = sqlScriptFactoryForTest.newSqlScript();
		
		Exception exception = new RuntimeException("test exception");
		when(sqlScriptService.update(sqlScript)).thenThrow(exception);
		
		// When
		String viewName = sqlScriptController.update(sqlScript, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sqlScript/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sqlScript, (SqlScript) modelMap.get("sqlScript"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/sqlScript/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "sqlScript.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		SqlScript sqlScript = sqlScriptFactoryForTest.newSqlScript();
		Integer id = sqlScript.getId();
		
		// When
		String viewName = sqlScriptController.delete(redirectAttributes, id);
		
		// Then
		verify(sqlScriptService).delete(id);
		assertEquals("redirect:/sqlScript", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		SqlScript sqlScript = sqlScriptFactoryForTest.newSqlScript();
		Integer id = sqlScript.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(sqlScriptService).delete(id);
		
		// When
		String viewName = sqlScriptController.delete(redirectAttributes, id);
		
		// Then
		verify(sqlScriptService).delete(id);
		assertEquals("redirect:/sqlScript", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "sqlScript.error.delete", exception);
	}
	
	
}
