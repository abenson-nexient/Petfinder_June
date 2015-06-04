package com.nexient.petfinder;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class PetController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Petfinder!";
    }

}
