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
import com.kumasi.techjournal.domain.ReleaseNote;
import com.kumasi.techjournal.domain.TechNote;
import com.kumasi.techjournal.test.ReleaseNoteFactoryForTest;
import com.kumasi.techjournal.test.TechNoteFactoryForTest;

//--- Services 
import com.kumasi.techjournal.business.service.ReleaseNoteService;
import com.kumasi.techjournal.business.service.TechNoteService;

//--- List Items 
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
public class ReleaseNoteControllerTest {
	
	@InjectMocks
	private ReleaseNoteController releaseNoteController;
	@Mock
	private ReleaseNoteService releaseNoteService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private TechNoteService techNoteService; // Injected by Spring

	private ReleaseNoteFactoryForTest releaseNoteFactoryForTest = new ReleaseNoteFactoryForTest();
	private TechNoteFactoryForTest techNoteFactoryForTest = new TechNoteFactoryForTest();

	List<TechNote> techNotes = new ArrayList<TechNote>();

	private void givenPopulateModel() {
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
		
		List<ReleaseNote> list = new ArrayList<ReleaseNote>();
		when(releaseNoteService.findAll()).thenReturn(list);
		
		// When
		String viewName = releaseNoteController.list(model);
		
		// Then
		assertEquals("releaseNote/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = releaseNoteController.formForCreate(model);
		
		// Then
		assertEquals("releaseNote/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((ReleaseNote)modelMap.get("releaseNote")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/releaseNote/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<TechNoteListItem> techNoteListItems = (List<TechNoteListItem>) modelMap.get("listOfTechNoteItems");
		assertEquals(2, techNoteListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReleaseNote releaseNote = releaseNoteFactoryForTest.newReleaseNote();
		Integer id = releaseNote.getId();
		when(releaseNoteService.findById(id)).thenReturn(releaseNote);
		
		// When
		String viewName = releaseNoteController.formForUpdate(model, id);
		
		// Then
		assertEquals("releaseNote/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(releaseNote, (ReleaseNote) modelMap.get("releaseNote"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/releaseNote/update", modelMap.get("saveAction"));
		
		List<TechNoteListItem> techNoteListItems = (List<TechNoteListItem>) modelMap.get("listOfTechNoteItems");
		assertEquals(2, techNoteListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ReleaseNote releaseNote = releaseNoteFactoryForTest.newReleaseNote();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ReleaseNote releaseNoteCreated = new ReleaseNote();
		when(releaseNoteService.create(releaseNote)).thenReturn(releaseNoteCreated); 
		
		// When
		String viewName = releaseNoteController.create(releaseNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/releaseNote/form/"+releaseNote.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(releaseNoteCreated, (ReleaseNote) modelMap.get("releaseNote"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReleaseNote releaseNote = releaseNoteFactoryForTest.newReleaseNote();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = releaseNoteController.create(releaseNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("releaseNote/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(releaseNote, (ReleaseNote) modelMap.get("releaseNote"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/releaseNote/create", modelMap.get("saveAction"));
		
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

		ReleaseNote releaseNote = releaseNoteFactoryForTest.newReleaseNote();
		
		Exception exception = new RuntimeException("test exception");
		when(releaseNoteService.create(releaseNote)).thenThrow(exception);
		
		// When
		String viewName = releaseNoteController.create(releaseNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("releaseNote/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(releaseNote, (ReleaseNote) modelMap.get("releaseNote"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/releaseNote/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "releaseNote.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<TechNoteListItem> techNoteListItems = (List<TechNoteListItem>) modelMap.get("listOfTechNoteItems");
		assertEquals(2, techNoteListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ReleaseNote releaseNote = releaseNoteFactoryForTest.newReleaseNote();
		Integer id = releaseNote.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ReleaseNote releaseNoteSaved = new ReleaseNote();
		releaseNoteSaved.setId(id);
		when(releaseNoteService.update(releaseNote)).thenReturn(releaseNoteSaved); 
		
		// When
		String viewName = releaseNoteController.update(releaseNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/releaseNote/form/"+releaseNote.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(releaseNoteSaved, (ReleaseNote) modelMap.get("releaseNote"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ReleaseNote releaseNote = releaseNoteFactoryForTest.newReleaseNote();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = releaseNoteController.update(releaseNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("releaseNote/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(releaseNote, (ReleaseNote) modelMap.get("releaseNote"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/releaseNote/update", modelMap.get("saveAction"));
		
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

		ReleaseNote releaseNote = releaseNoteFactoryForTest.newReleaseNote();
		
		Exception exception = new RuntimeException("test exception");
		when(releaseNoteService.update(releaseNote)).thenThrow(exception);
		
		// When
		String viewName = releaseNoteController.update(releaseNote, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("releaseNote/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(releaseNote, (ReleaseNote) modelMap.get("releaseNote"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/releaseNote/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "releaseNote.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<TechNoteListItem> techNoteListItems = (List<TechNoteListItem>) modelMap.get("listOfTechNoteItems");
		assertEquals(2, techNoteListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ReleaseNote releaseNote = releaseNoteFactoryForTest.newReleaseNote();
		Integer id = releaseNote.getId();
		
		// When
		String viewName = releaseNoteController.delete(redirectAttributes, id);
		
		// Then
		verify(releaseNoteService).delete(id);
		assertEquals("redirect:/releaseNote", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ReleaseNote releaseNote = releaseNoteFactoryForTest.newReleaseNote();
		Integer id = releaseNote.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(releaseNoteService).delete(id);
		
		// When
		String viewName = releaseNoteController.delete(redirectAttributes, id);
		
		// Then
		verify(releaseNoteService).delete(id);
		assertEquals("redirect:/releaseNote", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "releaseNote.error.delete", exception);
	}
	
	
}
