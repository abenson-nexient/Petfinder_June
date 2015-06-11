package com.nexient.petfinder.web.util;

import com.systemsinmotion.petrescue.entity.AgeType;
import com.systemsinmotion.petrescue.entity.AnimalType;
import com.systemsinmotion.petrescue.entity.GenderType;
import com.systemsinmotion.petrescue.entity.SizeType;

public class PetFinderTypes {



	public static String queryValueStrict(AnimalType animalType) {
		if (animalType == null)
			throw new IllegalArgumentException("animalType must not be null.");
		return queryValue(animalType);
	}

	public static String queryValue(AnimalType animalType) {
		if (animalType == null)
			return null;

		String queryValue;
		switch (animalType) {
		case SMALL_FURRY:
			queryValue = "smallfurry";
			break;
		case BARN_YARD:
			queryValue = "barnyard";
			break;
		case BIRD:
		case CAT:
		case DOG:
		case PIG:
		case RABBIT:
		case HORSE:
		case REPTILE:
			queryValue = animalType.name().toLowerCase();
			break;
		default:
			queryValue = animalType.value();
			break;
		}
		return queryValue;
	}
	


	public static Character queryValueStrict(GenderType genderType) {
		if (genderType == null)
			throw new IllegalArgumentException("genderType must not be null.");
		return queryValue(genderType);
	}

	public static Character queryValue(GenderType genderType) {
		if (genderType == null)
			return null;
		
		Character queryValue;
		switch (genderType) {
		case F:
			queryValue = Character.valueOf('F');
			break;
		case M:
			queryValue = Character.valueOf('M');
			break;
		default:
			if (genderType.description.length() > 0)
				queryValue = genderType.description.toUpperCase().charAt(0);
			else
				queryValue = genderType.name().charAt(0);
			break;
		}
		return queryValue;
	}
	


	public static String queryValueStrict(AgeType ageType) {
		if (ageType == null)
			throw new IllegalArgumentException("ageType must not be null.");
		return queryValue(ageType);
	}

	public static String queryValue(AgeType ageType) {
		if (ageType == null)
			return null;
		
		String queryValue;
		switch (ageType) {
		case ADULT:
			queryValue = "Adult";
		case BABY:
			queryValue = "Baby";
		case SENIOR:
			queryValue = "Senior";
		case YOUNG:
			queryValue = "Young";
			break;
		default:
			queryValue = ageType.description;
			break;
		}
		return queryValue;
	}
	


	public static String queryValueStrict(SizeType sizeType) {
		if (sizeType == null)
			throw new IllegalArgumentException("sizeType must not be null.");
		return queryValue(sizeType);
	}

	public static String queryValue(SizeType sizeType) {
		if (sizeType == null)
			return null;
		
		String queryValue;
		switch (sizeType) {
		case S:
		case M:
		case L:
		case XL:
		default:
			queryValue = sizeType.name();
			break;
		}
		return queryValue;
	}
}
