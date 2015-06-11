package com.nexient.petfinder.web.converters;

import java.beans.PropertyEditorSupport;

import com.systemsinmotion.petrescue.entity.AnimalType;

public class AnimalTypeConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(final String text) throws IllegalArgumentException {
    	if (text.equalsIgnoreCase("smallfurry")) {
    		setValue(AnimalType.SMALL_FURRY);
    		return;
    	}
    	
        for (AnimalType type : AnimalType.values()) {
        	if (type.value().equalsIgnoreCase(text)) {
        		setValue(type);
        		return;
        	}
        }
    	
    	try {
    		setValue(Enum.valueOf(AnimalType.class, text.toUpperCase()));
        	return;
        } catch (IllegalArgumentException e) { }
    	
    	throw new IllegalArgumentException(text);
    }
}
