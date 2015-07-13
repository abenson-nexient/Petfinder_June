package com.nexient.petfinder.web;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.petfinder.entity.PetfinderPetRecord;
import org.petfinder.entity.PetfinderPetRecordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexient.petfinder.dto.PetDto;
import com.nexient.petfinder.util.PetFinderTypes;
import com.nexient.petfinder.util.typeconverter.AgeTypeConverter;
import com.nexient.petfinder.util.typeconverter.AnimalTypeConverter;
import com.nexient.petfinder.util.typeconverter.GenderTypeConverter;
import com.nexient.petfinder.util.typeconverter.SizeTypeConverter;
import com.systemsinmotion.petrescue.entity.AgeType;
import com.systemsinmotion.petrescue.entity.AnimalType;
import com.systemsinmotion.petrescue.entity.GenderType;
import com.systemsinmotion.petrescue.entity.SizeType;
import com.systemsinmotion.petrescue.web.PetFinderConsumer;

@RestController
//@RequestMapping("/pet")
public class PetController {

	@Autowired
	private PetFinderConsumer petFinderService;

	@RequestMapping(value = "pet/random", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public PetDto[] getRandomPet() {
		return new PetDto[] { PetDto
				.fromPetFinderPetRecord(petFinderService.randomPet(null, null, null, null, null, null, "full", null)) };
	}

	@RequestMapping(value = "pet/search", produces = "application/json")
	public PetDto[] searchPets(@RequestParam("location") String location,
			@RequestParam(value = "animal", required = false) AnimalType animalType,
			@RequestParam(value = "breed", required = false) String[] breedParameters,
			@RequestParam(value = "sex", required = false) GenderType genderType,
			@RequestParam(value = "age", required = false) AgeType[] ageTypes,
			@RequestParam(value = "size", required = false) SizeType[] sizeTypes,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
			@RequestParam(value = "count", required = false, defaultValue = "30") int count) {
		// Validate Location
		if (location == null || location.isEmpty())
			throw new IllegalArgumentException("The 'location' parameter must not be empty.");

		if (breedParameters == null) {
			breedParameters = new String[0];
		}

		List<String> breedParametersList = Arrays.asList(breedParameters);
		breedParametersList.replaceAll(breed -> breed.toLowerCase());

		// Validate Breeds
		if (breedParametersList.size() > 0) {
			Set<String> validBreeds;
			if (animalType != null) {
				validBreeds = new TreeSet<String>(Arrays.asList(PetFinderTypes
						.queryValue(petFinderService.breedList(PetFinderTypes.queryValue(animalType), null))));

			} else {
				validBreeds = Arrays.stream(AnimalType.values()).map(type -> PetFinderTypes.queryValueStrict(type))
						.map(typeString -> petFinderService.breedList(typeString, null)).map(PetFinderTypes::queryValue)
						.flatMap(Arrays::stream).collect(Collectors.toCollection(TreeSet::new));
			}

			List<String> invalidBreeds = breedParametersList.stream().filter(breed -> !validBreeds.contains(breed))
					.collect(Collectors.toList());

			if (invalidBreeds.size() > 0)
				throw new IllegalArgumentException("Invalid breeds given");
		} else {
			breedParameters = new String[] { null };
		}

		ageTypes = ageTypes != null ? ageTypes : new AgeType[] { null };
		sizeTypes = sizeTypes != null ? sizeTypes : new SizeType[] { null };
		List<PetfinderPetRecordList> petLists = new LinkedList<PetfinderPetRecordList>();

		Character genderTypeChar = PetFinderTypes.queryValue(genderType);
		String animalTypeString = PetFinderTypes.queryValue(animalType);
		for (String breed : breedParameters) {
			for (AgeType ageType : ageTypes) {
				String ageTypeString = PetFinderTypes.queryValue(ageType);
				for (SizeType size : sizeTypes) {
					String sizeTypeString = PetFinderTypes.queryValue(size);
					petLists.add(petFinderService.findPet(animalTypeString, breed, sizeTypeString, genderTypeChar,
							location, ageTypeString, offset, count, "basic", null));
				}
			}
		}

		return petLists.stream().filter(Objects::nonNull).flatMap(list -> list.getPet().stream())
				.filter(Objects::nonNull).map(PetDto::fromPetFinderPetRecord)
				.sorted((pet1, pet2) -> pet1.getName().compareTo(pet2.getName())).limit(count)
				.toArray(size -> new PetDto[size]);
	}

	@RequestMapping(value = "pet/{id}", produces = "application/json")
	public PetDto[] getPetById(@PathVariable BigInteger id) {
		PetfinderPetRecord ppr = petFinderService.readPet(id, "xml");
		if (ppr == null)
			return new PetDto[0];
		else
			return new PetDto[] { PetDto.fromPetFinderPetRecord(ppr) };
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
