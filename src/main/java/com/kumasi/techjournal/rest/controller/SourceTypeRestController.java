/*
 * Created on 1 Jul 2016 ( Time 19:35:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.kumasi.techjournal.domain.SourceType;
import com.kumasi.techjournal.business.service.SourceTypeService;
import com.kumasi.techjournal.web.listitem.SourceTypeListItem;

/**
 * Spring MVC controller for 'SourceType' management.
 */
@Controller
public class SourceTypeRestController {

	@Resource
	private SourceTypeService sourceTypeService;
	
	@RequestMapping( value="/items/sourceType",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<SourceTypeListItem> findAllAsListItems() {
		List<SourceType> list = sourceTypeService.findAll();
		List<SourceTypeListItem> items = new LinkedList<SourceTypeListItem>();
		for ( SourceType sourceType : list ) {
			items.add(new SourceTypeListItem( sourceType ) );
		}
		return items;
	}
	
	@RequestMapping( value="/sourceType",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<SourceType> findAll() {
		return sourceTypeService.findAll();
	}

	@RequestMapping( value="/sourceType/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SourceType findOne(@PathVariable("id") Integer id) {
		return sourceTypeService.findById(id);
	}
	
	@RequestMapping( value="/sourceType",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SourceType create(@RequestBody SourceType sourceType) {
		return sourceTypeService.create(sourceType);
	}

	@RequestMapping( value="/sourceType/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SourceType update(@PathVariable("id") Integer id, @RequestBody SourceType sourceType) {
		return sourceTypeService.update(sourceType);
	}

	@RequestMapping( value="/sourceType/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		sourceTypeService.delete(id);
	}
	
}
