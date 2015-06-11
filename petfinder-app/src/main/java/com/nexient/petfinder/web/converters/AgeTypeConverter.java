package com.nexient.petfinder.web.converters;

import java.beans.PropertyEditorSupport;

import com.systemsinmotion.petrescue.entity.AgeType;

public class AgeTypeConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
        for (AgeType type : AgeType.values()) {
        	if (type.description.equalsIgnoreCase(text)) {
        		setValue(type);
        		return;
        	}
        }
    	
    	try {
    		setValue(Enum.valueOf(AgeType.class, text.toUpperCase()));
        	return;
        } catch (IllegalArgumentException e) { }
    	
    	throw new IllegalArgumentException(text);
    }
}
