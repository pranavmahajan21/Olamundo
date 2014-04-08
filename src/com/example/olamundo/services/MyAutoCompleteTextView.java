package com.example.olamundo.services;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class MyAutoCompleteTextView extends AutoCompleteTextView {

	public MyAutoCompleteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/** Returns the country name corresponding to the selected item */
	@Override
	protected CharSequence convertSelectionToString(Object selectedItem) {
		Temp temp = (Temp) selectedItem;
		return temp.getName();
	}

}
