package com.nexient.petfinder.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nexient.petfinder.models.Pet;

@RestController
@RequestMapping(value="/pet")
public class PetController {

	@RequestMapping(value="/random")
	public String getRandomPet()
	{
		return "Hello from getRandomPet.";
	}

	@RequestMapping(value="/search")
	public String searchPets() {
		return "Hello from searchPets.";
	}
	
	@RequestMapping(value="/{sid}/{pid}")
	public @ResponseBody Pet getPetInJSON(@PathVariable String sid, @PathVariable String pid) {
		Pet dummy = new Pet();
		dummy.setSid(0);
		dummy.setPid(0);
		return dummy;
	}
	
    @RequestMapping("/")
    public String index() {
        return "Greetings from Petfinder!";
    }

}
