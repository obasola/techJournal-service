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
import com.kumasi.techjournal.domain.Application;
import com.kumasi.techjournal.test.ApplicationFactoryForTest;

//--- Services 
import com.kumasi.techjournal.business.service.ApplicationService;


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
public class ApplicationControllerTest {
	
	@InjectMocks
	private ApplicationController applicationController;
	@Mock
	private ApplicationService applicationService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private ApplicationFactoryForTest applicationFactoryForTest = new ApplicationFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Application> list = new ArrayList<Application>();
		when(applicationService.findAll()).thenReturn(list);
		
		// When
		String viewName = applicationController.list(model);
		
		// Then
		assertEquals("application/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = applicationController.formForCreate(model);
		
		// Then
		assertEquals("application/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Application)modelMap.get("application")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/application/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Application application = applicationFactoryForTest.newApplication();
		Integer id = application.getId();
		when(applicationService.findById(id)).thenReturn(application);
		
		// When
		String viewName = applicationController.formForUpdate(model, id);
		
		// Then
		assertEquals("application/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(application, (Application) modelMap.get("application"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/application/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Application application = applicationFactoryForTest.newApplication();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Application applicationCreated = new Application();
		when(applicationService.create(application)).thenReturn(applicationCreated); 
		
		// When
		String viewName = applicationController.create(application, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/application/form/"+application.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(applicationCreated, (Application) modelMap.get("application"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Application application = applicationFactoryForTest.newApplication();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = applicationController.create(application, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("application/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(application, (Application) modelMap.get("application"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/application/create", modelMap.get("saveAction"));
		
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

		Application application = applicationFactoryForTest.newApplication();
		
		Exception exception = new RuntimeException("test exception");
		when(applicationService.create(application)).thenThrow(exception);
		
		// When
		String viewName = applicationController.create(application, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("application/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(application, (Application) modelMap.get("application"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/application/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "application.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Application application = applicationFactoryForTest.newApplication();
		Integer id = application.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Application applicationSaved = new Application();
		applicationSaved.setId(id);
		when(applicationService.update(application)).thenReturn(applicationSaved); 
		
		// When
		String viewName = applicationController.update(application, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/application/form/"+application.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(applicationSaved, (Application) modelMap.get("application"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Application application = applicationFactoryForTest.newApplication();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = applicationController.update(application, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("application/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(application, (Application) modelMap.get("application"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/application/update", modelMap.get("saveAction"));
		
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

		Application application = applicationFactoryForTest.newApplication();
		
		Exception exception = new RuntimeException("test exception");
		when(applicationService.update(application)).thenThrow(exception);
		
		// When
		String viewName = applicationController.update(application, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("application/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(application, (Application) modelMap.get("application"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/application/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "application.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Application application = applicationFactoryForTest.newApplication();
		Integer id = application.getId();
		
		// When
		String viewName = applicationController.delete(redirectAttributes, id);
		
		// Then
		verify(applicationService).delete(id);
		assertEquals("redirect:/application", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Application application = applicationFactoryForTest.newApplication();
		Integer id = application.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(applicationService).delete(id);
		
		// When
		String viewName = applicationController.delete(redirectAttributes, id);
		
		// Then
		verify(applicationService).delete(id);
		assertEquals("redirect:/application", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "application.error.delete", exception);
	}
	
	
}
