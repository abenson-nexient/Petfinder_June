package com.nexient.petfinder.web;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexient.petfinder.util.typeconverter.AnimalTypeConverter;
import com.nexient.petfinder.util.PetFinderTypes;
import com.systemsinmotion.petrescue.entity.AgeType;
import com.systemsinmotion.petrescue.entity.AnimalType;
import com.systemsinmotion.petrescue.entity.GenderType;
import com.systemsinmotion.petrescue.entity.SizeType;
import com.systemsinmotion.petrescue.web.PetFinderConsumer;

@RestController
@RequestMapping("/meta")
public class MetaController {

	@Autowired
	private PetFinderConsumer petFinderService;

	@RequestMapping("/breeds")
	public String[] getBreeds(@RequestParam(value="animal", required=false) AnimalType animalType) {
		String[] breeds;
		if (animalType != null) {
			breeds = PetFinderTypes.queryValueExcludeNulls(petFinderService.breedList(PetFinderTypes.queryValueStrict(animalType), "xml"));
		} else {
			breeds = Arrays.stream(AnimalType.values())
					.map(PetFinderTypes::queryValueStrict)
					.parallel()
					.map(typeString -> petFinderService.breedList(typeString, "xml"))
					.map(PetFinderTypes::queryValueExcludeNulls)
					.flatMap(breedArray -> Arrays.stream(breedArray))
					.toArray(size -> new String[size]);
		}
		return breeds;
	}

	@RequestMapping("/animals")
	public String[] getAnimalTypes() {
		return Arrays.stream(AnimalType.values())
				.map(PetFinderTypes::queryValueStrict)
				.toArray(size -> new String[size]);
	}

	@RequestMapping("/ages")
	public String[] getAgeTypes() {
		return Arrays.stream(AgeType.values())
				.map(PetFinderTypes::queryValueStrict)
				.toArray(size -> new String[size]);
	}

	@RequestMapping("/sizes")
	public String[] getSizeTypes() {
		return Arrays.stream(SizeType.values())
				.map(PetFinderTypes::queryValueStrict)
				.toArray(size -> new String[size]);
	}

	@RequestMapping("/genders")
	public String[] getGenderTypes() {
		return Arrays.stream(GenderType.values())
				.map(PetFinderTypes::queryValueStrict)
				.map(String::valueOf)
				.toArray(size -> new String[size]);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(AnimalType.class, new AnimalTypeConverter());
	}
}
