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
import com.kumasi.techjournal.domain.BatchJob;
import com.kumasi.techjournal.domain.ReleaseNote;
import com.kumasi.techjournal.test.BatchJobFactoryForTest;
import com.kumasi.techjournal.test.ReleaseNoteFactoryForTest;

//--- Services 
import com.kumasi.techjournal.business.service.BatchJobService;
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
public class BatchJobControllerTest {
	
	@InjectMocks
	private BatchJobController batchJobController;
	@Mock
	private BatchJobService batchJobService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ReleaseNoteService releaseNoteService; // Injected by Spring

	private BatchJobFactoryForTest batchJobFactoryForTest = new BatchJobFactoryForTest();
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
		
		List<BatchJob> list = new ArrayList<BatchJob>();
		when(batchJobService.findAll()).thenReturn(list);
		
		// When
		String viewName = batchJobController.list(model);
		
		// Then
		assertEquals("batchJob/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = batchJobController.formForCreate(model);
		
		// Then
		assertEquals("batchJob/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((BatchJob)modelMap.get("batchJob")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/batchJob/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		BatchJob batchJob = batchJobFactoryForTest.newBatchJob();
		Integer id = batchJob.getId();
		when(batchJobService.findById(id)).thenReturn(batchJob);
		
		// When
		String viewName = batchJobController.formForUpdate(model, id);
		
		// Then
		assertEquals("batchJob/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(batchJob, (BatchJob) modelMap.get("batchJob"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/batchJob/update", modelMap.get("saveAction"));
		
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		BatchJob batchJob = batchJobFactoryForTest.newBatchJob();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		BatchJob batchJobCreated = new BatchJob();
		when(batchJobService.create(batchJob)).thenReturn(batchJobCreated); 
		
		// When
		String viewName = batchJobController.create(batchJob, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/batchJob/form/"+batchJob.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(batchJobCreated, (BatchJob) modelMap.get("batchJob"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		BatchJob batchJob = batchJobFactoryForTest.newBatchJob();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = batchJobController.create(batchJob, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("batchJob/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(batchJob, (BatchJob) modelMap.get("batchJob"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/batchJob/create", modelMap.get("saveAction"));
		
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

		BatchJob batchJob = batchJobFactoryForTest.newBatchJob();
		
		Exception exception = new RuntimeException("test exception");
		when(batchJobService.create(batchJob)).thenThrow(exception);
		
		// When
		String viewName = batchJobController.create(batchJob, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("batchJob/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(batchJob, (BatchJob) modelMap.get("batchJob"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/batchJob/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "batchJob.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		BatchJob batchJob = batchJobFactoryForTest.newBatchJob();
		Integer id = batchJob.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		BatchJob batchJobSaved = new BatchJob();
		batchJobSaved.setId(id);
		when(batchJobService.update(batchJob)).thenReturn(batchJobSaved); 
		
		// When
		String viewName = batchJobController.update(batchJob, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/batchJob/form/"+batchJob.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(batchJobSaved, (BatchJob) modelMap.get("batchJob"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		BatchJob batchJob = batchJobFactoryForTest.newBatchJob();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = batchJobController.update(batchJob, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("batchJob/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(batchJob, (BatchJob) modelMap.get("batchJob"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/batchJob/update", modelMap.get("saveAction"));
		
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

		BatchJob batchJob = batchJobFactoryForTest.newBatchJob();
		
		Exception exception = new RuntimeException("test exception");
		when(batchJobService.update(batchJob)).thenThrow(exception);
		
		// When
		String viewName = batchJobController.update(batchJob, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("batchJob/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(batchJob, (BatchJob) modelMap.get("batchJob"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/batchJob/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "batchJob.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ReleaseNoteListItem> releaseNoteListItems = (List<ReleaseNoteListItem>) modelMap.get("listOfReleaseNoteItems");
		assertEquals(2, releaseNoteListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		BatchJob batchJob = batchJobFactoryForTest.newBatchJob();
		Integer id = batchJob.getId();
		
		// When
		String viewName = batchJobController.delete(redirectAttributes, id);
		
		// Then
		verify(batchJobService).delete(id);
		assertEquals("redirect:/batchJob", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		BatchJob batchJob = batchJobFactoryForTest.newBatchJob();
		Integer id = batchJob.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(batchJobService).delete(id);
		
		// When
		String viewName = batchJobController.delete(redirectAttributes, id);
		
		// Then
		verify(batchJobService).delete(id);
		assertEquals("redirect:/batchJob", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "batchJob.error.delete", exception);
	}
	
	
}
