package com.nexient.petfinder.web.converters;

import java.beans.PropertyEditorSupport;

import com.systemsinmotion.petrescue.entity.SizeType;

public class SizeTypeConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        for (SizeType type : SizeType.values()) {
        	if (type.description.equalsIgnoreCase(text)) {
        		setValue(type);
        		return;
        	}
        }
    	
    	try {
    		setValue(Enum.valueOf(SizeType.class, text.toUpperCase()));
        	return;
        } catch (IllegalArgumentException e) { }
    	
    	throw new IllegalArgumentException(text);
    }
}
