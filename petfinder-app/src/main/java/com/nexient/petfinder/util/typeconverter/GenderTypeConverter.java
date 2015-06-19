package com.nexient.petfinder.util.typeconverter;

import java.beans.PropertyEditorSupport;

import com.systemsinmotion.petrescue.entity.GenderType;

public class GenderTypeConverter extends PropertyEditorSupport {
	@Override
	public void setAsText(final String text) throws IllegalArgumentException {
		for (GenderType type : GenderType.values()) {
			if (type.description.equalsIgnoreCase(text)) {
				setValue(type);
				return;
			}
		}

		try {
			setValue(Enum.valueOf(GenderType.class, text.toUpperCase()));
			return;
		} catch (IllegalArgumentException e) { }

		throw new IllegalArgumentException(text);
	}
}
