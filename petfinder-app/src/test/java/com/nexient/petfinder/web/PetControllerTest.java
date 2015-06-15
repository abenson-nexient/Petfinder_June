package com.nexient.petfinder.web;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexient.petfinder.Application;
import com.nexient.petfinder.models.Pet;
import com.systemsinmotion.petrescue.web.PetFinderConsumer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PetControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private PetFinderConsumer petFinderService;

	@Autowired
	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testPetRoot() throws Exception {
		mockMvc.perform(get("/pet").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isNotFound());
	}

	@Test
	public void testGetRandomPet() throws Exception {
		ResultActions result = mockMvc.perform(get("/pet/random").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", hasSize(1))) // Only difference from testSearchpets.
				.andExpect(jsonPath("$[*].id", everyItem(isA(Integer.class))));

		testPetStringFields(result);
		testPetArrayFields(result);
	}

	@Test
	public void testSearchPets() throws Exception {
		ResultActions result = mockMvc.perform(get("/pet/search?location=MI").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
				.andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[*].id", everyItem(isA(Integer.class))));

		testPetStringFields(result);
		testPetArrayFields(result);
	}

	@Test
	public void testGetPetById() throws Exception {
		int ids[] = new int[] { 32296622, 32287134, 31565804, 26611061 };

		for (int id : ids) {
			Pet pet = Pet.fromPetFinderPetRecord(petFinderService.readPet(BigInteger.valueOf(id), null));
			String petStr = mapper.writeValueAsString(new Pet[] { pet });

			mockMvc.perform(get("/pet/" + id).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json;charset=UTF-8"))
			.andExpect(content().json(petStr));
		}

	}

	/**
	 * Insure that all fields of a pet object array that should be arrays of Strings are in fact arrays of Strings.
	 * @param result The result of a get request that should have returned an array of pet objects.
	 * @throws Exception result.andExpect can throw undefined exceptions.
	 */
	private void testPetArrayFields(ResultActions result) throws Exception {
		String arrayField = "$[*]['%s']";
		String arrayFieldItems = "$[*]['%s'][*]";
		String[] arrayFields = new String[] { "images", "breeds" };
		for (String name : arrayFields) {
			result.andExpect(jsonPath(arrayField, name).isArray());
			result.andExpect(jsonPath(arrayFieldItems, name).value(everyItem(isA(String.class))));
		}
	}

	/**
	 * Insure that all fields of a pet object array that should be Strings are in fact Strings.
	 * @param result The result of a get request that should have returned an array of pet objects.
	 * @throws Exception result.andExpect can throw undefined exceptions.
	 */
	private void testPetStringFields(ResultActions result) throws Exception {
		String stringField = "$[*]['%s']";
		String[] fields = new String[] { "name", "animal", "age", "sex", "status", "size", "mix", "description", "contact" };
		for (String name : fields) {
			result.andExpect(jsonPath(stringField, name).value(everyItem(isA(String.class))));
		}
	}

}
