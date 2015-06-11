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
import com.nexient.petfinder.web.converters.CaseInsensitiveConverter;
import com.nexient.petfinder.web.converters.GenderTypeConverter;
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

	@RequestMapping({"/random", "/random/"})
	public Pet[] getRandomPet()
	{
		return new Pet[] { Pet.fromPetFinderPetRecord(petFinderService.randomPet(null, null, null, null, null, null, "full", null)) };
	}

	@RequestMapping({"/search", "/search/"})
	public Pet[] searchPets(@RequestParam("location") String location,
			@RequestParam(value="animal", required=false) AnimalType animalType,		// DOG, CAT, SMALL_FURRY, BARN_YARD, BIRD, HORSE, PIG, RABBIT, REPTILE
			@RequestParam(value="breed", required=false) String[] breeds,
			@RequestParam(value="sex", required=false) GenderType sex,				// M, F
			@RequestParam(value="age", required=false) AgeType[] ages, 				// BABY, YOUNG, ADULT, SENIOR
			@RequestParam(value="size", required=false) SizeType[] sizes, 			// S, M, L, XL
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
				PetfinderBreedList breedListContainer = petFinderService.breedList(animalType.value().toLowerCase(), null);
				if (breedListContainer == null) {
					breedsAreValid = false;
				} else {
					List<String> breedList = petFinderService.breedList(animalType.value().toLowerCase(), null).getBreed();
					breedList.replaceAll(breed -> breed.toLowerCase());
					breedsAreValid = breedList.containsAll(breedsAsList);
				}
			} else {
				breedsAreValid = Arrays.stream(AnimalType.values())
						.map(type -> type.value().toLowerCase())
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


		ages = ages != null ? ages : new AgeType[] { null };
		sizes = sizes != null ? sizes : new SizeType[] { null};
		List<PetfinderPetRecordList> petLists = new LinkedList<PetfinderPetRecordList>();


		Character sexValue = sex != null ? sex.description.charAt(0) : null;
		String animalTypeValue = animalType != null ? animalType.value().toLowerCase() : null;
		for (String breed : breeds) {
			for (AgeType age : ages) {
				String ageValue = age != null ? age.description : null;
				for (SizeType size : sizes) {
					String sizeValue = size != null ? size.description : null;
					petLists.add(petFinderService.findPet(animalTypeValue, breed, sizeValue, sexValue, location, ageValue, offset, count, "basic", null));
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

	@RequestMapping({"/{id}", "/{id}/"})
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
		binder.registerCustomEditor(SizeType.class, new CaseInsensitiveConverter<>(SizeType.class));
		binder.registerCustomEditor(AnimalType.class, new AnimalTypeConverter());
	}
}
