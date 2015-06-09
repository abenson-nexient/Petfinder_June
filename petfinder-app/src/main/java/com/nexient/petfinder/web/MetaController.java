package com.nexient.petfinder.web;

import java.util.Arrays;
import java.util.Objects;

import org.petfinder.entity.PetfinderBreedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexient.petfinder.config.CaseInsensitiveConverter;
import com.nexient.petfinder.models.BreedList;
import com.systemsinmotion.petrescue.entity.AnimalType;
import com.systemsinmotion.petrescue.web.PetFinderConsumer;

@RestController
@RequestMapping("/meta")
public class MetaController {

	@Autowired
	private PetFinderConsumer petFinderService;

	@RequestMapping("/breeds")
	public BreedList getBreeds(@RequestParam(value="animal", required=false) AnimalType animal) {
		String animalValue = animal == null ? null : animal.value().toLowerCase();
		String[] breeds;
		if (animalValue != null) {
			PetfinderBreedList pfbl = petFinderService.breedList(animalValue, "xml");
			if (pfbl != null) {
				breeds = pfbl.getBreed().stream().toArray(size -> new String[size]);
			} else {
				breeds = new String[] {};
			}
		} else {
			breeds = Arrays.stream(AnimalType.values())
					.map(type -> type.value().toLowerCase())
					.parallel()
					.map(typeString -> petFinderService.breedList(typeString, null))
					.filter(Objects::nonNull)
					.map(breedList -> breedList.getBreed())
					.flatMap(list -> list.stream())
					.map(breed -> breed.toLowerCase())
					.toArray(size -> new String[size]);
		}

		return new BreedList(animal, breeds);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(AnimalType.class, new CaseInsensitiveConverter<>(AnimalType.class));
	}
}
