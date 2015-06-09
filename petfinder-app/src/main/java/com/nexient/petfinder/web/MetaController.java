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
import com.systemsinmotion.petrescue.entity.AnimalType;
import com.systemsinmotion.petrescue.web.PetFinderConsumer;

@RestController
@RequestMapping("/meta")
public class MetaController {

	@Autowired
	private PetFinderConsumer petFinderService;

	@RequestMapping("/breeds")
	public String[] getBreeds(@RequestParam(value="animal", required=false) AnimalType animal) {
		String[] breeds;
		if (animal != null) {
			PetfinderBreedList pfbl = petFinderService.breedList(animal.value().toLowerCase(), "xml");
			if (pfbl == null) {
				breeds = new String[] {};
			} else {
				breeds = pfbl.getBreed().stream().toArray(size -> new String[size]);
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
		return breeds;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(AnimalType.class, new CaseInsensitiveConverter<>(AnimalType.class));
	}
}
