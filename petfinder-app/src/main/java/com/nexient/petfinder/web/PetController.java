package com.nexient.petfinder.web;

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
	public @ResponseBody Pet getRandomPet()
	{
		Pet pet = Pet.fromPetFinderPetRecord(petFinderService.randomPet(null, null, null, null, null, null, "full", null));
		return pet;
	}

	@RequestMapping(value="/search")
	public String searchPets() {
		return "Hello from searchPets.";
	}
	
	@RequestMapping(value="/{sid}/{pid}")
	public @ResponseBody Pet[] getPedBySidPid(@PathVariable String sid, @PathVariable String pid) {
		Pet[] pets = new Pet[2];
		Pet dummy = new Pet();
		pets[0] = dummy;
		pets[1] = dummy;
		return pets;
	}
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from Petfinder!";
    }

}
