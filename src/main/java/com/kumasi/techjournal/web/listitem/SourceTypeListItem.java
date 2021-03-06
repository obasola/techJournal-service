/*
 * Created on 1 Jul 2016 ( Time 19:35:42 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.web.listitem;

import com.kumasi.techjournal.domain.SourceType;
import com.kumasi.techjournal.web.common.ListItem;

public class SourceTypeListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public SourceTypeListItem(SourceType sourceType) {
		super();

		this.value = ""
			 + sourceType.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = sourceType.toString();
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
