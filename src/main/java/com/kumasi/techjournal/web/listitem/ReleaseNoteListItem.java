/*
 * Created on 1 Jul 2016 ( Time 19:35:41 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.techjournal.web.listitem;

import com.kumasi.techjournal.domain.ReleaseNote;
import com.kumasi.techjournal.web.common.ListItem;

public class ReleaseNoteListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public ReleaseNoteListItem(ReleaseNote releaseNote) {
		super();

		this.value = ""
			 + releaseNote.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = releaseNote.toString();
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
