package com.nexient.petfinder.web.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import org.petfinder.entity.PetAgeType;
import org.petfinder.entity.PetContactType;
import org.petfinder.entity.PetGenderType;
import org.petfinder.entity.PetSizeType;
import org.petfinder.entity.PetStatusType;
import org.petfinder.entity.PetfinderBreedList;
import org.petfinder.entity.PetfinderPetRecord.Media;

import com.systemsinmotion.petrescue.entity.AgeType;
import com.systemsinmotion.petrescue.entity.AnimalType;
import com.systemsinmotion.petrescue.entity.ContactType;
import com.systemsinmotion.petrescue.entity.GenderType;
import com.systemsinmotion.petrescue.entity.SizeType;
import com.systemsinmotion.petrescue.entity.StatusType;

/**
 * Class to help with managing the various PetFinder types, classes, and enums.
 * Query values are String intended to be fed into PetFinderConsumer method calls.
 * I.e. they ought to garantee an output String that corresponds to what the Petfinder API
 * accepts.
 *
 * Display Strings are supposed to be meaningful and consistent Strings for the front-end client
 * to receive in method calls.
 * @author dgill
 *
 */
public class PetFinderTypes {

	private final static String NO_DISPLAY = "?";

	private static AnimalType convert(org.petfinder.entity.AnimalType petAnimalType) {
		if (petAnimalType == null)
			return null;

		switch (petAnimalType) {
		case BARN_YARD:
			return AnimalType.BARN_YARD;
		case BIRD:
			return AnimalType.BIRD;
		case CAT:
			return AnimalType.CAT;
		case DOG:
			return AnimalType.DOG;
		case HORSE:
			return AnimalType.HORSE;
		case PIG:
			return AnimalType.PIG;
		case RABBIT:
			return AnimalType.RABBIT;
		case REPTILE:
			return AnimalType.REPTILE;
		case SMALL_FURRY:
			return AnimalType.SMALL_FURRY;
		default:
			try {
				return AnimalType.fromValue(petAnimalType.value());
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("No animalType exists corresponding to org.petfinder.entity.AnimalType " + petAnimalType.name() +".");
			}
		}
	}

	private static GenderType convert(PetGenderType petGenderType) {
		if (petGenderType == null)
			return null;

		if (petGenderType == PetGenderType.M)
			return GenderType.M;
		else if (petGenderType == PetGenderType.F)
			return GenderType.F;
		else
			throw new IllegalArgumentException("No GenderType exists corresponding to PetGenderType " + petGenderType.name() + ".");
	}

	private static AgeType convert(PetAgeType petAgeType) {
		if (petAgeType == null)
			return null;

		switch (petAgeType) {
		case ADULT:
			return AgeType.ADULT;
		case BABY:
			return AgeType.BABY;
		case SENIOR:
			return AgeType.SENIOR;
		case YOUNG:
			return AgeType.YOUNG;
		default:
			throw new IllegalArgumentException("No AgeType exists corresponding to PetAgeType " + petAgeType.name() + ".");
		}
	}

	private static SizeType convert(PetSizeType petSizeType) {
		if (petSizeType == null)
			return null;

		switch (petSizeType) {
		case S:
			return SizeType.S;
		case M:
			return SizeType.M;
		case L:
			return SizeType.L;
		case XL:
			return SizeType.XL;
		default:
			throw new IllegalArgumentException("No SizeType exists corresponding to PetSizeType " + petSizeType.name() + ".");
		}
	}

	private static StatusType convert(PetStatusType petStatusType) {
		if (petStatusType == null)
			return null;

		switch (petStatusType) {
		case A:
			return StatusType.A;
		case H:
			return StatusType.H;
		case P:
			return StatusType.P;
		case X:
			return StatusType.X;
		default:
			throw new IllegalArgumentException("No StatusType exists corresponding to PetStatusType " + petStatusType.name() + ".");
		}
	}

	private static ContactType convert(PetContactType petContactType) {
		if (petContactType == null)
			return null;

		ContactType toReturn = new ContactType();
		toReturn.setAddress1(petContactType.getAddress1());
		toReturn.setAddress2(petContactType.getAddress2());
		toReturn.setEmail(petContactType.getEmail());
		toReturn.setFax(petContactType.getFax());
		toReturn.setName(petContactType.getName());
		toReturn.setState(petContactType.getState());
		toReturn.setZip(petContactType.getZip());

		return toReturn;
	}


	public static String queryValueStrict(AnimalType animalType) {
		if (animalType == null)
			throw new IllegalArgumentException("animalType must not be null.");
		return queryValue(animalType);
	}

	public static String queryValueStrict(org.petfinder.entity.AnimalType animalType) {
		return queryValueStrict(convert(animalType));
	}

	public static String queryValue(AnimalType animalType) {
		if (animalType == null)
			return null;

		switch (animalType) {
		case SMALL_FURRY:
			return "smallfurry";
		case BARN_YARD:
			return "barnyard";
		case BIRD:
		case CAT:
		case DOG:
		case PIG:
		case RABBIT:
		case HORSE:
		case REPTILE:
			return animalType.name().toLowerCase();
		default:
			return animalType.value();
		}
	}

	public static String queryValue(org.petfinder.entity.AnimalType animalType) {
		return queryValue(convert(animalType));
	}


	public static Character queryValueStrict(GenderType genderType) {
		if (genderType == null)
			throw new IllegalArgumentException("genderType must not be null.");
		return queryValue(genderType);
	}

	public static Character queryValueStrict(PetGenderType petGenderType) {
		return queryValueStrict(convert(petGenderType));
	}

	public static Character queryValue(GenderType genderType) {
		if (genderType == null)
			return null;

		if (genderType == GenderType.F)
			return Character.valueOf('F');
		else if (genderType == GenderType.M)
			return Character.valueOf('M');
		else if (genderType.description.length() > 0)
			return genderType.description.toUpperCase().charAt(0);
		else
			return genderType.name().charAt(0);
	}

	public static Character queryValue(PetGenderType petGenderType) {
		return queryValue(convert(petGenderType));
	}

	public static String queryValueStrict(AgeType ageType) {
		if (ageType == null)
			throw new IllegalArgumentException("ageType must not be null.");
		return queryValue(ageType);
	}

	public static String queryValueStrict(PetAgeType petAgeType) {
		return queryValueStrict(convert(petAgeType));
	}

	public static String queryValue(AgeType ageType) {
		if (ageType == null)
			return null;

		switch (ageType) {
		case ADULT:
			return "Adult";
		case BABY:
			return "Baby";
		case SENIOR:
			return "Senior";
		case YOUNG:
			return "Young";
		default:
			return ageType.description;
		}
	}

	public static String queryValue(PetAgeType petAgeType) {
		return queryValue(convert(petAgeType));
	}

	public static String queryValueStrict(SizeType sizeType) {
		if (sizeType == null)
			throw new IllegalArgumentException("sizeType must not be null.");
		return queryValue(sizeType);
	}

	public static String queryValueStrict(PetSizeType petSizeType) {
		return queryValueStrict(convert(petSizeType));
	}

	public static String queryValue(SizeType sizeType) {
		if (sizeType == null)
			return null;

		return sizeType.name();
	}

	private static Stream<String> queryValueCore(PetfinderBreedList petfinderBreedList) {
		if (petfinderBreedList == null || petfinderBreedList.getBreed() == null)
			return Stream.empty();

		return petfinderBreedList.getBreed().stream()
				.map(breedString -> breedString != null ? breedString.toLowerCase() : null);
	}

	public static String[] queryValueStrict(PetfinderBreedList petfinderBreedList) {
		if (petfinderBreedList == null)
			throw new IllegalArgumentException("petfinderBreedList must not be null.");

		String[] toReturn = queryValueCore(petfinderBreedList).toArray(size -> new String[size]);
		if (Arrays.stream(toReturn).anyMatch(Objects::isNull))
			throw new IllegalArgumentException("petFinderBreedList contains null values.");

		return toReturn;
	}

	public static String[] queryValueStrictExcludeNulls(PetfinderBreedList petfinderBreedList) {
		if (petfinderBreedList == null)
			throw new IllegalArgumentException("petfinderBreedList must not be null.");

		return queryValueCore(petfinderBreedList)
				.filter(Objects::nonNull)
				.toArray(size -> new String[size]);
	}

	public static String[] queryValue(PetfinderBreedList petfinderBreedList) {
		if (petfinderBreedList == null)
			return new String[0];

		return queryValueCore(petfinderBreedList)
				.toArray(size -> new String[size]);
	}

	public static String[] queryValueExcludeNulls(PetfinderBreedList petfinderBreedList) {
		if (petfinderBreedList == null)
			return new String[0];

		return queryValueCore(petfinderBreedList)
				.filter(Objects::nonNull)
				.toArray(size -> new String[size]);
	}

	public static String queryValue(PetSizeType petSizeType) {
		return queryValue(convert(petSizeType));
	}

	public static String displayString(AnimalType animalType) {
		if (animalType == null)
			return null;

		switch (animalType) {
		case BARN_YARD:
			return "Barn Yard";
		case SMALL_FURRY:
			return "Small & Furry";
		case BIRD:
		case CAT:
		case DOG:
		case HORSE:
		case PIG:
		case RABBIT:
		case REPTILE:
			return animalType.name().substring(0,1).toUpperCase() + animalType.name().substring(1).toLowerCase();
		default:
			return NO_DISPLAY;
		}
	}

	public static String displayString(org.petfinder.entity.AnimalType petAnimalType) {
		return displayString(convert(petAnimalType));
	}

	public static String displayString(GenderType genderType) {
		if (genderType == null)
			return null;

		switch (genderType) {
		case F:
			return "Female";
		case M:
			return "Male";
		default:
			return genderType.description;
		}
	}

	public static String displayString(PetGenderType petGenderType) {
		return displayString(convert(petGenderType));
	}

	public static String displayString(AgeType ageType) {
		if (ageType == null)
			return null;

		switch (ageType) {
		case ADULT:
			return "Adult";
		case BABY:
			return "Baby";
		case SENIOR:
			return "Senior";
		case YOUNG:
			return "Young";
		default:
			return ageType.description;
		}
	}

	public static String displayString(PetAgeType petAgeType) {
		return displayString(convert(petAgeType));
	}

	public static String displayString(SizeType sizeType) {
		if (sizeType == null)
			return null;

		switch (sizeType) {
		case S:
			return "Small";
		case M:
			return "Medium";
		case L:
			return "Large";
		case XL:
			return "Extra Large";
		default:
			return sizeType.description;
		}
	}

	public static String displayString(PetSizeType petSizeType) {
		return displayString(convert(petSizeType));
	}

	public static String displayString(StatusType statusType) {
		if (statusType == null)
			return null;

		switch (statusType) {
		case A:
			return "Adoptable";
		case H:
			return "Hold";
		case P:
			return "Pending";
		case X:
			return "Adopted";
		default:
			return statusType.description;
		}
	}

	public static String displayString(PetStatusType petStatusType) {
		return displayString(convert(petStatusType));
	}

	public static String displayString(ContactType contact) {
		if (contact == null)
			return null;

		if ((contact.getEmail() != null && !contact.getEmail().trim().isEmpty()))
			return "email: " + contact.getEmail().trim().toLowerCase();
		else if ((contact.getPhone() != null && !contact.getPhone().trim().isEmpty()))
			return "phone: " + contact.getPhone().trim().toLowerCase();
		else if ((contact.getName() != null && !contact.getName().trim().isEmpty())
				&& (contact.getAddress1() != null && !contact.getAddress1().trim().isEmpty())
				&& (contact.getState() != null && !contact.getState().trim().isEmpty())
				&& (contact.getZip() != null && !contact.getZip().trim().isEmpty())) {
			String toReturn = "address: ";
			toReturn += contact.getAddress1().trim().toLowerCase();
			if (contact.getAddress2() != null && !contact.getAddress1().trim().isEmpty()) {
				toReturn += " " + contact.getAddress2().trim().toLowerCase();
			}
			toReturn += ", " + contact.getState().trim().toLowerCase();
			toReturn += " " + contact.getZip().trim().toLowerCase();
			return toReturn;
		}
		else if (contact.getFax() != null && !contact.getFax().trim().isEmpty())
			return contact.getFax();
		else
			return NO_DISPLAY;
	}

	public static String displayString(PetContactType petContactType) {
		return displayString(convert(petContactType));
	}

	public static String[] displayString(PetfinderBreedList petfinderBreedList) {
		if (petfinderBreedList == null)
			return new String[0];

		return petfinderBreedList.getBreed().stream()
				.filter(Objects::nonNull)
				.map(breed -> breed.toLowerCase())
				.toArray((int size) -> new String[size]);
	}

	private static String[] displayStringCore(Media media, String imageSize) {
		if (media == null || media.getPhotos() == null)
			return new String[0];
		if (imageSize == null)
			throw new IllegalArgumentException("imageSize must not be null.");

		return media.getPhotos().getPhoto().stream()
				.filter(Objects::nonNull)
				.filter((ppt) -> ppt.getSize().equals(imageSize))
				.map((ppt) -> ppt.getValue())
				.toArray((int size) -> new String[size]);
	}

	public static String[] displayString(Media media, String imageSize) {
		if (imageSize == null)
			throw new IllegalArgumentException("imageSize must not be null.");
		return displayStringCore(media, imageSize);
	}

	public static String[] displayString(Media media) {
		return displayStringCore(media, "x");
	}

}
