/*
 * Created on 1 Jul 2016 ( Time 19:35:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.web.listitem;

import com.kumasi.techjournal.domain.RestCall;
import com.kumasi.techjournal.web.common.ListItem;

public class RestCallListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public RestCallListItem(RestCall restCall) {
		super();

		this.value = ""
			 + restCall.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = restCall.toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

}
