package com.nexient.petfinder.web;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.petfinder.entity.PetAgeType;
import org.petfinder.entity.PetGenderType;
import org.petfinder.entity.PetSizeType;
import org.petfinder.entity.PetfinderPetRecord;
import org.petfinder.entity.PetfinderPetRecordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexient.petfinder.models.Pet;
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
			@RequestParam(value="animal", required=false) String animalType,
			@RequestParam(value="breed", required=false) String[] breeds,
			@RequestParam(value="sex", required=false) PetGenderType sex,
			@RequestParam(value="age", required=false) PetAgeType[] ages,
			@RequestParam(value="size", required=false) PetSizeType[] sizes,
			@RequestParam(value="offset", required=false, defaultValue="0") int offset,
			@RequestParam(value="count", required=false, defaultValue="30") int count) {

		breeds = breeds != null ? breeds : new String[] { null };
		ages = ages != null ? ages : new PetAgeType[] { null };
		sizes = sizes != null ? sizes : new PetSizeType[] { null};
		List<PetfinderPetRecordList> petLists = new LinkedList<PetfinderPetRecordList>();


		// Since the requester only wants count-many items, divide these across the api calls to petfinder.
		int numRequests = breeds.length * ages.length * sizes.length;
		count = count / numRequests;


		Character sexValue = sex != null ? sex.value().charAt(0) : null; 
		for (String breed : breeds) {
			for (PetAgeType age : ages) {
				String ageValue = age != null ? age.value() : null;
				for (PetSizeType size : sizes) {
					String sizeValue = size != null ? size.value() : null;
					petLists.add(petFinderService.findPet(animalType, breed, sizeValue, sexValue, location, ageValue, offset, count, "basic", null));
				}
			}
		}

		return petLists.stream()
				.filter(Objects::nonNull)
				.flatMap(list -> list.getPet().stream())
				.filter(Objects::nonNull)
				.map(Pet::fromPetFinderPetRecord)
				.sorted((pet1, pet2) -> pet1.getName().compareTo(pet2.getName()))
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

}
