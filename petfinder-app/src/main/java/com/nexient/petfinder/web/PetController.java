package com.nexient.petfinder.web;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.petfinder.entity.PetfinderBreedList;
import org.petfinder.entity.PetfinderPetRecord;
import org.petfinder.entity.PetfinderPetRecordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexient.petfinder.models.Pet;
import com.nexient.petfinder.web.converters.AgeTypeConverter;
import com.nexient.petfinder.web.converters.AnimalTypeConverter;
import com.nexient.petfinder.web.converters.GenderTypeConverter;
import com.nexient.petfinder.web.converters.SizeTypeConverter;
import com.nexient.petfinder.web.util.PetFinderTypes;
import com.systemsinmotion.petrescue.entity.AgeType;
import com.systemsinmotion.petrescue.entity.AnimalType;
import com.systemsinmotion.petrescue.entity.GenderType;
import com.systemsinmotion.petrescue.entity.SizeType;
import com.systemsinmotion.petrescue.web.PetFinderConsumer;

@RestController
@RequestMapping("/pet")
public class PetController {

	@Autowired
	private PetFinderConsumer petFinderService;

	@RequestMapping("/random")
	public Pet[] getRandomPet()
	{
		return new Pet[] { Pet.fromPetFinderPetRecord(petFinderService.randomPet(null, null, null, null, null, null, "full", null)) };
	}

	@RequestMapping("/search")
	public Pet[] searchPets(@RequestParam("location") String location,
			@RequestParam(value="animal", required=false) AnimalType animalType,
			@RequestParam(value="breed", required=false) String[] breeds,
			@RequestParam(value="sex", required=false) GenderType genderType,
			@RequestParam(value="age", required=false) AgeType[] ageTypes,
			@RequestParam(value="size", required=false) SizeType[] sizeTypes,
			@RequestParam(value="offset", required=false, defaultValue="0") int offset,
			@RequestParam(value="count", required=false, defaultValue="30") int count) {
		// Validate Location
		if (location == null || location.isEmpty())
			throw new IllegalArgumentException("The 'location' parameter must not be empty.");

		// Validate Breeds
		if (breeds != null && breeds.length > 0) {
			boolean breedsAreValid;
			List<String> breedsAsList = Arrays.asList(breeds);
			breedsAsList.replaceAll(breed -> breed.toLowerCase());
			if (animalType != null) {
				PetfinderBreedList breedListContainer = petFinderService.breedList(PetFinderTypes.queryValue(animalType), null);
				if (breedListContainer == null) {
					breedsAreValid = false;
				} else {
					List<String> breedList = petFinderService.breedList(PetFinderTypes.queryValue(animalType), null).getBreed();
					breedList.replaceAll(breed -> breed.toLowerCase());
					breedsAreValid = breedList.containsAll(breedsAsList);
				}
			} else {
				breedsAreValid = Arrays.stream(AnimalType.values())
						.map(type -> PetFinderTypes.queryValueStrict(type))
						.parallel()
						.map(typeString -> petFinderService.breedList(typeString, null))
						.filter(Objects::nonNull)
						.map(breedList -> breedList.getBreed())
						.flatMap(list -> list.stream())
						.map(breed -> breed.toLowerCase())
						.collect(Collectors.toCollection(TreeSet::new))
						.containsAll(breedsAsList);
			}
			if (!breedsAreValid)
				throw new IllegalArgumentException("Invalid breeds given.");
		} else {
			breeds = new String[] { null };
		}


		ageTypes = ageTypes != null ? ageTypes : new AgeType[] { null };
		sizeTypes = sizeTypes != null ? sizeTypes : new SizeType[] { null };
		List<PetfinderPetRecordList> petLists = new LinkedList<PetfinderPetRecordList>();


		Character genderTypeChar = PetFinderTypes.queryValue(genderType);
		String animalTypeString = PetFinderTypes.queryValue(animalType);
		for (String breed : breeds) {
			for (AgeType ageType : ageTypes) {
				String ageTypeString = PetFinderTypes.queryValue(ageType);
				for (SizeType size : sizeTypes) {
					String sizeTypeString = PetFinderTypes.queryValue(size);
					petLists.add(petFinderService.findPet(animalTypeString, breed, sizeTypeString, genderTypeChar, location, ageTypeString, offset, count, "basic", null));
				}
			}
		}

		return petLists.stream()
				.filter(Objects::nonNull)
				.flatMap(list -> list.getPet().stream())
				.filter(Objects::nonNull)
				.map(Pet::fromPetFinderPetRecord)
				.sorted((pet1, pet2) -> pet1.getName().compareTo(pet2.getName()))
				.limit(count)
				.toArray(size -> new Pet[size]);
	}

	@RequestMapping("/{id}")
	public Pet[] getPetById(@PathVariable BigInteger id) {
		PetfinderPetRecord ppr = petFinderService.readPet(id, "xml");
		if (ppr == null)
			return new Pet[0];
		else
			return new Pet[] { Pet.fromPetFinderPetRecord(ppr) };
	}

	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(AgeType.class, new AgeTypeConverter());
		binder.registerCustomEditor(GenderType.class, new GenderTypeConverter());
		binder.registerCustomEditor(SizeType.class, new SizeTypeConverter());
		binder.registerCustomEditor(AnimalType.class, new AnimalTypeConverter());
	}
}
