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
import com.kumasi.techjournal.domain.RestCall;
import com.kumasi.techjournal.domain.ReleaseNote;
import com.kumasi.techjournal.test.RestCallFactoryForTest;
import com.kumasi.techjournal.test.ReleaseNoteFactoryForTest;

//--- Services 
import com.kumasi.techjournal.business.service.RestCallService;
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
public class RestCallControllerTest {
	
	@InjectMocks
	private RestCallController restCallController;
	@Mock
	private RestCallService restCallService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ReleaseNoteService releaseNoteService; // Injected by Spring

	private RestCallFactoryForTest restCallFactoryForTest = new RestCallFactoryForTest();
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
		
		List<RestCall> list = new ArrayList<RestCall>();
		when(restCallService.findAll()).thenReturn(list);
		
		// When
		String viewName = restCallController.list(model);
		
		// Then
		assertEquals("restCall/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = restCallController.formForCreate(model);
		
		// Then
		assertEquals("restCall/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((RestCall)modelMap.get("restCall")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/restCall/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RestCall restCall = restCallFactoryForTest.newRestCall();
		Integer id = restCall.getId();
		when(restCallService.findById(id)).thenReturn(restCall);
		
		// When
		String viewName = restCallController.formForUpdate(model, id);
		
		// Then
		assertEquals("restCall/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(restCall, (RestCall) modelMap.get("restCall"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/restCall/update", modelMap.get("saveAction"));
		
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		RestCall restCall = restCallFactoryForTest.newRestCall();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		RestCall restCallCreated = new RestCall();
		when(restCallService.create(restCall)).thenReturn(restCallCreated); 
		
		// When
		String viewName = restCallController.create(restCall, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/restCall/form/"+restCall.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(restCallCreated, (RestCall) modelMap.get("restCall"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RestCall restCall = restCallFactoryForTest.newRestCall();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = restCallController.create(restCall, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("restCall/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(restCall, (RestCall) modelMap.get("restCall"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/restCall/create", modelMap.get("saveAction"));
		
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

		RestCall restCall = restCallFactoryForTest.newRestCall();
		
		Exception exception = new RuntimeException("test exception");
		when(restCallService.create(restCall)).thenThrow(exception);
		
		// When
		String viewName = restCallController.create(restCall, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("restCall/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(restCall, (RestCall) modelMap.get("restCall"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/restCall/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "restCall.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		RestCall restCall = restCallFactoryForTest.newRestCall();
		Integer id = restCall.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		RestCall restCallSaved = new RestCall();
		restCallSaved.setId(id);
		when(restCallService.update(restCall)).thenReturn(restCallSaved); 
		
		// When
		String viewName = restCallController.update(restCall, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/restCall/form/"+restCall.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(restCallSaved, (RestCall) modelMap.get("restCall"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RestCall restCall = restCallFactoryForTest.newRestCall();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = restCallController.update(restCall, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("restCall/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(restCall, (RestCall) modelMap.get("restCall"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/restCall/update", modelMap.get("saveAction"));
		
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

		RestCall restCall = restCallFactoryForTest.newRestCall();
		
		Exception exception = new RuntimeException("test exception");
		when(restCallService.update(restCall)).thenThrow(exception);
		
		// When
		String viewName = restCallController.update(restCall, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("restCall/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(restCall, (RestCall) modelMap.get("restCall"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/restCall/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "restCall.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		RestCall restCall = restCallFactoryForTest.newRestCall();
		Integer id = restCall.getId();
		
		// When
		String viewName = restCallController.delete(redirectAttributes, id);
		
		// Then
		verify(restCallService).delete(id);
		assertEquals("redirect:/restCall", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		RestCall restCall = restCallFactoryForTest.newRestCall();
		Integer id = restCall.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(restCallService).delete(id);
		
		// When
		String viewName = restCallController.delete(redirectAttributes, id);
		
		// Then
		verify(restCallService).delete(id);
		assertEquals("redirect:/restCall", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "restCall.error.delete", exception);
	}
	
	
}
