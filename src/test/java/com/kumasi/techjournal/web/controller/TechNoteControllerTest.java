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
import com.kumasi.techjournal.domain.TechNote;
import com.kumasi.techjournal.domain.Application;
import com.kumasi.techjournal.test.TechNoteFactoryForTest;
import com.kumasi.techjournal.test.ApplicationFactoryForTest;

//--- Services 
import com.kumasi.techjournal.business.service.TechNoteService;
import com.kumasi.techjournal.business.service.ApplicationService;

//--- List Items 
import com.kumasi.techjournal.web.listitem.ApplicationListItem;

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
public class TechNoteControllerTest {
	
	@InjectMocks
	private TechNoteController techNoteController;
	@Mock
	private TechNoteService techNoteService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ApplicationService applicationService; // Injected by Spring

	private TechNoteFactoryForTest techNoteFactoryForTest = new TechNoteFactoryForTest();
	private ApplicationFactoryForTest applicationFactoryForTest = new ApplicationFactoryForTest();

	List<Application> applications = new ArrayList<Application>();

	private void givenPopulateModel() {
		Application application1 = applicationFactoryForTest.newApplication();
		Application application2 = applicationFactoryForTest.newApplication();
		List<Application> applications = new ArrayList<Application>();
		applications.add(application1);
		applications.add(application2);
		when(applicationService.findAll()).thenReturn(applications);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<TechNote> list = new ArrayList<TechNote>();
		when(techNoteService.findAll()).thenReturn(list);
		
		// When
		String viewName = techNoteController.list(model);
		
		// Then
		assertEquals("techNote/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = techNoteController.formForCreate(model);
		
		// Then
		assertEquals("techNote/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((TechNote)modelMap.get("techNote")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/techNote/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ApplicationListItem> applicationListItems = (List<ApplicationListItem>) modelMap.get("listOfApplicationItems");
		assertEquals(2, applicationListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		TechNote techNote = techNoteFactoryForTest.newTechNote();
		Integer id = techNote.getId();
		when(techNoteService.findById(id)).thenReturn(techNote);
		
		// When
		String viewName = techNoteController.formForUpdate(model, id);
		
		// Then
		assertEquals("techNote/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(techNote, (TechNote) modelMap.get("techNote"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/techNote/update", modelMap.get("saveAction"));
		
		List<ApplicationListItem> applicationListItems = (List<ApplicationListItem>) modelMap.get("listOfApplicationItems");
		assertEquals(2, applicationListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		TechNote techNote = techNoteFactoryForTest.newTechNote();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		TechNote techNoteCreated = new TechNote();
		when(techNoteService.create(techNote)).thenReturn(techNoteCreated); 
		
		// When
		String viewName = techNoteController.create(techNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/techNote/form/"+techNote.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(techNoteCreated, (TechNote) modelMap.get("techNote"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		TechNote techNote = techNoteFactoryForTest.newTechNote();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = techNoteController.create(techNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("techNote/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(techNote, (TechNote) modelMap.get("techNote"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/techNote/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ApplicationListItem> applicationListItems = (List<ApplicationListItem>) modelMap.get("listOfApplicationItems");
		assertEquals(2, applicationListItems.size());
		
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

		TechNote techNote = techNoteFactoryForTest.newTechNote();
		
		Exception exception = new RuntimeException("test exception");
		when(techNoteService.create(techNote)).thenThrow(exception);
		
		// When
		String viewName = techNoteController.create(techNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("techNote/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(techNote, (TechNote) modelMap.get("techNote"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/techNote/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "techNote.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ApplicationListItem> applicationListItems = (List<ApplicationListItem>) modelMap.get("listOfApplicationItems");
		assertEquals(2, applicationListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		TechNote techNote = techNoteFactoryForTest.newTechNote();
		Integer id = techNote.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		TechNote techNoteSaved = new TechNote();
		techNoteSaved.setId(id);
		when(techNoteService.update(techNote)).thenReturn(techNoteSaved); 
		
		// When
		String viewName = techNoteController.update(techNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/techNote/form/"+techNote.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(techNoteSaved, (TechNote) modelMap.get("techNote"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		TechNote techNote = techNoteFactoryForTest.newTechNote();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = techNoteController.update(techNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("techNote/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(techNote, (TechNote) modelMap.get("techNote"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/techNote/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ApplicationListItem> applicationListItems = (List<ApplicationListItem>) modelMap.get("listOfApplicationItems");
		assertEquals(2, applicationListItems.size());
		
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

		TechNote techNote = techNoteFactoryForTest.newTechNote();
		
		Exception exception = new RuntimeException("test exception");
		when(techNoteService.update(techNote)).thenThrow(exception);
		
		// When
		String viewName = techNoteController.update(techNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("techNote/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(techNote, (TechNote) modelMap.get("techNote"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/techNote/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "techNote.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ApplicationListItem> applicationListItems = (List<ApplicationListItem>) modelMap.get("listOfApplicationItems");
		assertEquals(2, applicationListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		TechNote techNote = techNoteFactoryForTest.newTechNote();
		Integer id = techNote.getId();
		
		// When
		String viewName = techNoteController.delete(redirectAttributes, id);
		
		// Then
		verify(techNoteService).delete(id);
		assertEquals("redirect:/techNote", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		TechNote techNote = techNoteFactoryForTest.newTechNote();
		Integer id = techNote.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(techNoteService).delete(id);
		
		// When
		String viewName = techNoteController.delete(redirectAttributes, id);
		
		// Then
		verify(techNoteService).delete(id);
		assertEquals("redirect:/techNote", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "techNote.error.delete", exception);
	}
	
	
}
