package com.nexient.petfinder.web;

import java.math.BigInteger;

import org.petfinder.entity.PetfinderPetRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(value="/random")
	public @ResponseBody Pet[] getRandomPet()
	{
		return new Pet[] { Pet.fromPetFinderPetRecord(petFinderService.randomPet(null, null, null, null, null, null, "full", null)) };
	}

	@RequestMapping(value="/search")
	public String searchPets() {
		return "Hello from searchPets.";
	}
	
	@RequestMapping(value="/{id}")
	public @ResponseBody Pet[] getPedBySidPid(@PathVariable BigInteger id) {
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
