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

import org.petfinder.entity.PetAgeType;
import org.petfinder.entity.PetGenderType;
import org.petfinder.entity.PetSizeType;
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

import com.nexient.petfinder.config.CaseInsensitiveConverter;
import com.nexient.petfinder.models.Pet;
import com.systemsinmotion.petrescue.entity.AnimalType;
import com.systemsinmotion.petrescue.web.PetFinderConsumer;

@RestController
@RequestMapping(value="/pet")
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
			@RequestParam(value="animal", required=false) AnimalType animalType,		// DOG, CAT, SMALL_FURRY, BARN_YARD, BIRD, HORSE, PIG, RABBIT, REPTILE
			@RequestParam(value="breed", required=false) String[] breeds,
			@RequestParam(value="sex", required=false) PetGenderType sex,				// M, F
			@RequestParam(value="age", required=false) PetAgeType[] ages, 				// BABY, YOUNG, ADULT, SENIOR
			@RequestParam(value="size", required=false) PetSizeType[] sizes, 			// S, M, L, XL
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
				List<String> breedList = petFinderService.breedList(animalType.value().toLowerCase(), null).getBreed();
				breedList.replaceAll(breed -> breed.toLowerCase());
				breedsAreValid = breedList.containsAll(breedsAsList);
			} else {
				breedsAreValid = Arrays.stream(AnimalType.values())
						.map(type -> type.value().toLowerCase())
						.parallel()
						.map(typeString -> petFinderService.breedList(typeString, null).getBreed())
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


		ages = ages != null ? ages : new PetAgeType[] { null };
		sizes = sizes != null ? sizes : new PetSizeType[] { null};
		List<PetfinderPetRecordList> petLists = new LinkedList<PetfinderPetRecordList>();


		Character sexValue = sex != null ? sex.value().charAt(0) : null;
		String animalTypeValue = animalType != null ? animalType.value().toLowerCase() : null;
		for (String breed : breeds) {
			for (PetAgeType age : ages) {
				String ageValue = age != null ? age.value() : null;
				for (PetSizeType size : sizes) {
					String sizeValue = size != null ? size.value() : null;
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

	@RequestMapping("/{id}")
	public Pet[] getPedBySidPid(@PathVariable BigInteger id) {
		PetfinderPetRecord ppr = petFinderService.readPet(id, "xml");
		if (ppr == null)
			return new Pet[0];
		else
			return new Pet[] { Pet.fromPetFinderPetRecord(ppr) };
	}

	@RequestMapping("/")
	public String index() {
		return "Greetings from Petfinder!";
	}

	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(PetAgeType.class, new CaseInsensitiveConverter<>(PetAgeType.class));
		binder.registerCustomEditor(PetGenderType.class, new CaseInsensitiveConverter<>(PetGenderType.class));
		binder.registerCustomEditor(PetSizeType.class, new CaseInsensitiveConverter<>(PetSizeType.class));
		binder.registerCustomEditor(AnimalType.class, new CaseInsensitiveConverter<>(AnimalType.class));
	}
}
