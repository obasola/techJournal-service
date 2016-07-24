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
import com.kumasi.techjournal.domain.SourceType;
import com.kumasi.techjournal.test.SourceTypeFactoryForTest;

//--- Services 
import com.kumasi.techjournal.business.service.SourceTypeService;


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
public class SourceTypeControllerTest {
	
	@InjectMocks
	private SourceTypeController sourceTypeController;
	@Mock
	private SourceTypeService sourceTypeService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private SourceTypeFactoryForTest sourceTypeFactoryForTest = new SourceTypeFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<SourceType> list = new ArrayList<SourceType>();
		when(sourceTypeService.findAll()).thenReturn(list);
		
		// When
		String viewName = sourceTypeController.list(model);
		
		// Then
		assertEquals("sourceType/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = sourceTypeController.formForCreate(model);
		
		// Then
		assertEquals("sourceType/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((SourceType)modelMap.get("sourceType")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/sourceType/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		SourceType sourceType = sourceTypeFactoryForTest.newSourceType();
		Integer id = sourceType.getId();
		when(sourceTypeService.findById(id)).thenReturn(sourceType);
		
		// When
		String viewName = sourceTypeController.formForUpdate(model, id);
		
		// Then
		assertEquals("sourceType/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceType, (SourceType) modelMap.get("sourceType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/sourceType/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		SourceType sourceType = sourceTypeFactoryForTest.newSourceType();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		SourceType sourceTypeCreated = new SourceType();
		when(sourceTypeService.create(sourceType)).thenReturn(sourceTypeCreated); 
		
		// When
		String viewName = sourceTypeController.create(sourceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/sourceType/form/"+sourceType.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceTypeCreated, (SourceType) modelMap.get("sourceType"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		SourceType sourceType = sourceTypeFactoryForTest.newSourceType();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = sourceTypeController.create(sourceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sourceType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceType, (SourceType) modelMap.get("sourceType"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/sourceType/create", modelMap.get("saveAction"));
		
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

		SourceType sourceType = sourceTypeFactoryForTest.newSourceType();
		
		Exception exception = new RuntimeException("test exception");
		when(sourceTypeService.create(sourceType)).thenThrow(exception);
		
		// When
		String viewName = sourceTypeController.create(sourceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sourceType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceType, (SourceType) modelMap.get("sourceType"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/sourceType/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "sourceType.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		SourceType sourceType = sourceTypeFactoryForTest.newSourceType();
		Integer id = sourceType.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		SourceType sourceTypeSaved = new SourceType();
		sourceTypeSaved.setId(id);
		when(sourceTypeService.update(sourceType)).thenReturn(sourceTypeSaved); 
		
		// When
		String viewName = sourceTypeController.update(sourceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/sourceType/form/"+sourceType.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceTypeSaved, (SourceType) modelMap.get("sourceType"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		SourceType sourceType = sourceTypeFactoryForTest.newSourceType();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = sourceTypeController.update(sourceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sourceType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceType, (SourceType) modelMap.get("sourceType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/sourceType/update", modelMap.get("saveAction"));
		
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

		SourceType sourceType = sourceTypeFactoryForTest.newSourceType();
		
		Exception exception = new RuntimeException("test exception");
		when(sourceTypeService.update(sourceType)).thenThrow(exception);
		
		// When
		String viewName = sourceTypeController.update(sourceType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("sourceType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(sourceType, (SourceType) modelMap.get("sourceType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/sourceType/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "sourceType.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		SourceType sourceType = sourceTypeFactoryForTest.newSourceType();
		Integer id = sourceType.getId();
		
		// When
		String viewName = sourceTypeController.delete(redirectAttributes, id);
		
		// Then
		verify(sourceTypeService).delete(id);
		assertEquals("redirect:/sourceType", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		SourceType sourceType = sourceTypeFactoryForTest.newSourceType();
		Integer id = sourceType.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(sourceTypeService).delete(id);
		
		// When
		String viewName = sourceTypeController.delete(redirectAttributes, id);
		
		// Then
		verify(sourceTypeService).delete(id);
		assertEquals("redirect:/sourceType", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "sourceType.error.delete", exception);
	}
	
	
}
