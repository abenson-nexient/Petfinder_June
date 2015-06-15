package com.nexient.petfinder.web;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.isIn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nexient.petfinder.Application;
import com.nexient.petfinder.web.util.PetFinderTypes;
import com.systemsinmotion.petrescue.entity.AnimalType;
import com.systemsinmotion.petrescue.web.PetFinderConsumer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MetaControllerTest {

	@Autowired
	private PetFinderConsumer petFinderService;

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testGetAllBreeds() throws Exception {
		Set<String> allbreeds = getBreedsDirectly(Arrays.stream(AnimalType.values())
				.map(PetFinderTypes::queryValue)
				.toArray(size -> new String[size]));

		mockMvc.perform(get("/meta/breeds").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$[*]", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*]", everyItem(isIn(allbreeds))));

		;
	}

	@Test
	public void testGetEachBreed() throws Exception {
		String[] animalTypes = Arrays.stream(AnimalType.values())
				.map(PetFinderTypes::queryValue)
				.toArray(size -> new String[size]);
		for (String animalType : animalTypes) {
			Set<String> allbreeds = getBreedsDirectly(new String[] {animalType});

			mockMvc.perform(get("/meta/breeds")
					.param("animal", animalType)
					.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$").isArray())
					.andExpect(jsonPath("$[*]", everyItem(isA(String.class))))
					.andExpect(jsonPath("$[*]", everyItem(isIn(allbreeds))));
		}
	}

	@Test
	public void testGetAnimalTypes() throws Exception {
	}

	private Set<String> getBreedsDirectly(String[] animalTypes) {
		return Arrays.stream(animalTypes)
				.parallel()
				.map(typeString -> petFinderService.breedList(typeString, null))
				.filter(Objects::nonNull)
				.map(PetFinderTypes::displayString)
				.flatMap(Arrays::stream)
				.map(breed -> breed.toLowerCase())
				.collect(Collectors.toCollection(TreeSet<String>::new));
	}

}
