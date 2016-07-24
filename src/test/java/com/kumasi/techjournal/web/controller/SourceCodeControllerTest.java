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
import com.kumasi.techjournal.domain.SourceCode;
import com.kumasi.techjournal.domain.SourceType;
import com.kumasi.techjournal.domain.TechNote;
import com.kumasi.techjournal.test.SourceCodeFactoryForTest;
import com.kumasi.techjournal.test.SourceTypeFactoryForTest;
import com.kumasi.techjournal.test.TechNoteFactoryForTest;

//--- Services 
import com.kumasi.techjournal.business.service.SourceCodeService;
import com.kumasi.techjournal.business.service.SourceTypeService;
import com.kumasi.techjournal.business.service.TechNoteService;

//--- List Items 
import com.kumasi.techjournal.web.listitem.SourceTypeListItem;
import com.kumasi.techjournal.web.listitem.TechNoteListItem;

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
public class SourceCodeControllerTest {
	
	@InjectMocks
	private SourceCodeController sourceCodeController;
	@Mock
	private SourceCodeService sourceCodeService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private SourceTypeService sourceTypeService; // Injected by Spring
	@Mock
	private TechNoteService techNoteService; // Injected by Spring

	private SourceCodeFactoryForTest sourceCodeFactoryForTest = new SourceCodeFactoryForTest();
	private SourceTypeFactoryForTest sourceTypeFactoryForTest = new SourceTypeFactoryForTest();
	private TechNoteFactoryForTest techNoteFactoryForTest = new TechNoteFactoryForTest();

	List<SourceType> sourceTypes = new ArrayList<SourceType>();
	List<TechNote> techNotes = new ArrayList<TechNote>();

	private void givenPopulateModel() {
		SourceType sourceType1 = sourceTypeFactoryForTest.newSourceType();
		SourceType sourceType2 = sourceTypeFactoryForTest.newSourceType();
		List<SourceType> sourceTypes = new ArrayList<SourceType>();
		sourceTypes.add(sourceType1);
		sourceTypes.add(sourceType2);
		when(sourceTypeService.findAll()).thenReturn(sourceTypes);

		TechNote techNote1 = techNoteFactoryForTest.newTechNote();
		TechNote techNote2 = techNoteFactoryForTest.newTechNote();
		List<TechNote> techNotes = new ArrayList<TechNote>();
		techNotes.add(techNote1);
		techNotes.add(techNote2);
		when(techNoteService.findAll()).thenReturn(techNotes);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<SourceCode> list = new ArrayList<SourceCode>();
		when(sourceCodeService.findAll()).thenReturn(list);
		
		// When
		String viewName = sourceCodeController.list(model);
		
		// Then
		assertEquals("sourceCode/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = sourceCodeController.formForCreate(model);
		
		// Then
		assertEquals("sourceCode/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((SourceCode)modelMap.get("sourceCode")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/sourceCode/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<SourceTypeListItem> sourceTypeListItems = (List<SourceTypeListItem>) modelMap.get("listOfSourceTypeItems");
		assertEquals(2, sourceTypeListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TechNoteListItem> techNoteListItems = (List<TechNoteListItem>) modelMap.get("listOfTechNoteItems");
		assertEquals(2, techNoteListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();
		Integer id = sourceCode.getId();
		when(sourceCodeService.findById(id)).thenReturn(sourceCode);
		
		// When
		String viewName = sourceCodeController.formForUpdate(model, id);
		
		// Then
		assertEquals("sourceCode/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceCode, (SourceCode) modelMap.get("sourceCode"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/sourceCode/update", modelMap.get("saveAction"));
		
		List<SourceTypeListItem> sourceTypeListItems = (List<SourceTypeListItem>) modelMap.get("listOfSourceTypeItems");
		assertEquals(2, sourceTypeListItems.size());
		
		List<TechNoteListItem> techNoteListItems = (List<TechNoteListItem>) modelMap.get("listOfTechNoteItems");
		assertEquals(2, techNoteListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		SourceCode sourceCodeCreated = new SourceCode();
		when(sourceCodeService.create(sourceCode)).thenReturn(sourceCodeCreated); 
		
		// When
		String viewName = sourceCodeController.create(sourceCode, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/sourceCode/form/"+sourceCode.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceCodeCreated, (SourceCode) modelMap.get("sourceCode"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = sourceCodeController.create(sourceCode, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sourceCode/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceCode, (SourceCode) modelMap.get("sourceCode"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/sourceCode/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<SourceTypeListItem> sourceTypeListItems = (List<SourceTypeListItem>) modelMap.get("listOfSourceTypeItems");
		assertEquals(2, sourceTypeListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TechNoteListItem> techNoteListItems = (List<TechNoteListItem>) modelMap.get("listOfTechNoteItems");
		assertEquals(2, techNoteListItems.size());
		
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

		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();
		
		Exception exception = new RuntimeException("test exception");
		when(sourceCodeService.create(sourceCode)).thenThrow(exception);
		
		// When
		String viewName = sourceCodeController.create(sourceCode, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sourceCode/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceCode, (SourceCode) modelMap.get("sourceCode"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/sourceCode/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "sourceCode.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<SourceTypeListItem> sourceTypeListItems = (List<SourceTypeListItem>) modelMap.get("listOfSourceTypeItems");
		assertEquals(2, sourceTypeListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TechNoteListItem> techNoteListItems = (List<TechNoteListItem>) modelMap.get("listOfTechNoteItems");
		assertEquals(2, techNoteListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();
		Integer id = sourceCode.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		SourceCode sourceCodeSaved = new SourceCode();
		sourceCodeSaved.setId(id);
		when(sourceCodeService.update(sourceCode)).thenReturn(sourceCodeSaved); 
		
		// When
		String viewName = sourceCodeController.update(sourceCode, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/sourceCode/form/"+sourceCode.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceCodeSaved, (SourceCode) modelMap.get("sourceCode"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = sourceCodeController.update(sourceCode, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sourceCode/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceCode, (SourceCode) modelMap.get("sourceCode"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/sourceCode/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<SourceTypeListItem> sourceTypeListItems = (List<SourceTypeListItem>) modelMap.get("listOfSourceTypeItems");
		assertEquals(2, sourceTypeListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TechNoteListItem> techNoteListItems = (List<TechNoteListItem>) modelMap.get("listOfTechNoteItems");
		assertEquals(2, techNoteListItems.size());
		
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

		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();
		
		Exception exception = new RuntimeException("test exception");
		when(sourceCodeService.update(sourceCode)).thenThrow(exception);
		
		// When
		String viewName = sourceCodeController.update(sourceCode, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sourceCode/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceCode, (SourceCode) modelMap.get("sourceCode"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/sourceCode/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "sourceCode.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<SourceTypeListItem> sourceTypeListItems = (List<SourceTypeListItem>) modelMap.get("listOfSourceTypeItems");
		assertEquals(2, sourceTypeListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TechNoteListItem> techNoteListItems = (List<TechNoteListItem>) modelMap.get("listOfTechNoteItems");
		assertEquals(2, techNoteListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();
		Integer id = sourceCode.getId();
		
		// When
		String viewName = sourceCodeController.delete(redirectAttributes, id);
		
		// Then
		verify(sourceCodeService).delete(id);
		assertEquals("redirect:/sourceCode", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		SourceCode sourceCode = sourceCodeFactoryForTest.newSourceCode();
		Integer id = sourceCode.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(sourceCodeService).delete(id);
		
		// When
		String viewName = sourceCodeController.delete(redirectAttributes, id);
		
		// Then
		verify(sourceCodeService).delete(id);
		assertEquals("redirect:/sourceCode", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "sourceCode.error.delete", exception);
	}
	
	
}
