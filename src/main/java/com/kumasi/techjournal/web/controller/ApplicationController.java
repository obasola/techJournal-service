/*
 * Created on 4 Jul 2016 ( Time 10:15:05 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//--- Common classes
import com.kumasi.techjournal.web.common.AbstractController;
import com.kumasi.techjournal.web.common.FormMode;
import com.kumasi.techjournal.web.common.Message;
import com.kumasi.techjournal.web.common.MessageType;

//--- Entities
import com.kumasi.techjournal.domain.Application;

//--- Services 
import com.kumasi.techjournal.business.service.ApplicationService;


/**
 * Spring MVC controller for 'Application' management.
 */
@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	//--- Variables names ( to be used in JSP with Expression Language )
	private static final String MAIN_ENTITY_NAME = "application";
	private static final String MAIN_LIST_NAME   = "list";

	//--- JSP pages names ( View name in the MVC model )
	private static final String JSP_FORM   = "application/form";
	private static final String JSP_LIST   = "application/list";

	//--- SAVE ACTION ( in the HTML form )
	private static final String SAVE_ACTION_CREATE   = "/application/create";
	private static final String SAVE_ACTION_UPDATE   = "/application/update";

	//--- Main entity service
	@Resource
    private ApplicationService applicationService; // Injected by Spring
	//--- Other service(s)

	//--------------------------------------------------------------------------------------
	/**
	 * Default constructor
	 */
	public ApplicationController() {
		super(ApplicationController.class, MAIN_ENTITY_NAME );
		log("ApplicationController created.");
	}

	//--------------------------------------------------------------------------------------
	// Spring MVC model management
	//--------------------------------------------------------------------------------------

	/**
	 * Populates the Spring MVC model with the given entity and eventually other useful data
	 * @param model
	 * @param application
	 */
	private void populateModel(Model model, Application application, FormMode formMode) {
		//--- Main entity
		model.addAttribute(MAIN_ENTITY_NAME, application);
		if ( formMode == FormMode.CREATE ) {
			model.addAttribute(MODE, MODE_CREATE); // The form is in "create" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_CREATE); 			
			//--- Other data useful in this screen in "create" mode (all fields)
		}
		else if ( formMode == FormMode.UPDATE ) {
			model.addAttribute(MODE, MODE_UPDATE); // The form is in "update" mode
			model.addAttribute(SAVE_ACTION, SAVE_ACTION_UPDATE); 			
			//--- Other data useful in this screen in "update" mode (only non-pk fields)
		}
	}

	//--------------------------------------------------------------------------------------
	// Request mapping
	//--------------------------------------------------------------------------------------
	/**
	 * Shows a list with all the occurrences of Application found in the database
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping()
	public String list(Model model) {
		log("Action 'list'");
		List<Application> list = applicationService.findAll();
		model.addAttribute(MAIN_LIST_NAME, list);		
		return JSP_LIST;
	}

	/**
	 * Shows a form page in order to create a new Application
	 * @param model Spring MVC model
	 * @return
	 */
	@RequestMapping("/form")
	public String formForCreate(Model model) {
		log("Action 'formForCreate'");
		//--- Populates the model with a new instance
		Application application = new Application();	
		populateModel( model, application, FormMode.CREATE);
		return JSP_FORM;
	}

	/**
	 * Shows a form page in order to update an existing Application
	 * @param model Spring MVC model
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/form/{id}")
	public String formForUpdate(Model model, @PathVariable("id") Integer id ) {
		log("Action 'formForUpdate'");
		//--- Search the entity by its primary key and stores it in the model 
		Application application = applicationService.findById(id);
		populateModel( model, application, FormMode.UPDATE);		
		return JSP_FORM;
	}

	/**
	 * 'CREATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param application  entity to be created
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/create" ) // GET or POST
	public String create(@Valid Application application, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'create'");
		try {
			if (!bindingResult.hasErrors()) {
				Application applicationCreated = applicationService.create(application); 
				model.addAttribute(MAIN_ENTITY_NAME, applicationCreated);

				//---
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				return redirectToForm(httpServletRequest, application.getId() );
			} else {
				populateModel( model, application, FormMode.CREATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			log("Action 'create' : Exception - " + e.getMessage() );
			messageHelper.addException(model, "application.error.create", e);
			populateModel( model, application, FormMode.CREATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'UPDATE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param application  entity to be updated
	 * @param bindingResult Spring MVC binding result
	 * @param model Spring MVC model
	 * @param redirectAttributes Spring MVC redirect attributes
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/update" ) // GET or POST
	public String update(@Valid Application application, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
		log("Action 'update'");
		try {
			if (!bindingResult.hasErrors()) {
				//--- Perform database operations
				Application applicationSaved = applicationService.update(application);
				model.addAttribute(MAIN_ENTITY_NAME, applicationSaved);
				//--- Set the result message
				messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
				log("Action 'update' : update done - redirect");
				return redirectToForm(httpServletRequest, application.getId());
			} else {
				log("Action 'update' : binding errors");
				populateModel( model, application, FormMode.UPDATE);
				return JSP_FORM;
			}
		} catch(Exception e) {
			messageHelper.addException(model, "application.error.update", e);
			log("Action 'update' : Exception - " + e.getMessage() );
			populateModel( model, application, FormMode.UPDATE);
			return JSP_FORM;
		}
	}

	/**
	 * 'DELETE' action processing. <br>
	 * This action is based on the 'Post/Redirect/Get (PRG)' pattern, so it ends by 'http redirect'<br>
	 * @param redirectAttributes
	 * @param id  primary key element
	 * @return
	 */
	@RequestMapping(value = "/delete/{id}") // GET or POST
	public String delete(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
		log("Action 'delete'" );
		try {
			applicationService.delete( id );
			//--- Set the result message
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));	
		} catch(Exception e) {
			messageHelper.addException(redirectAttributes, "application.error.delete", e);
		}
		return redirectToList();
	}

}
