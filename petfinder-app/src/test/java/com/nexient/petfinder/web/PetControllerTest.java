package com.nexient.petfinder.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.hamcrest.Matchers.*;

import org.hamcrest.collection.IsArrayContaining;
import org.hamcrest.generator.HamcrestFactoryWriter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PetControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPetRoot() throws Exception {
		mockMvc.perform(get("/pet").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isNotFound());
	}

	@Test
	public void testGetRandomPet() throws Exception {
		String petElement = "$[*]['%s']";
		
		mockMvc.perform(get("/pet/random").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$", hasSize(1))) // Only difference from testSearchpets.
		.andExpect(jsonPath("$[*].name", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].animal", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].age", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].sex", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].status", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].size", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].mix", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].description", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].contact", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].id", everyItem(isA(Integer.class))))
		.andExpect(jsonPath("$[*].breeds").isArray())
		.andExpect(jsonPath("$[*].breeds[*]", everyItem(either(isA(String.class)).or(isEmptyOrNullString()))))
		.andExpect(jsonPath("$[*].images").isArray())
		.andExpect(jsonPath("$[*].images[*]", everyItem(either(isA(String.class)).or(isEmptyOrNullString()))));
	}

	@Test
	public void testSearchPets() throws Exception {
		mockMvc.perform(get("/pet/search?location=MI").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$[*].name", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].animal", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].age", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].sex", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].status", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].size", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].mix", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].description", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].contact", everyItem(isA(String.class))))
		.andExpect(jsonPath("$[*].id", everyItem(isA(Integer.class))))
		.andExpect(jsonPath("$[*].breeds").isArray())
		.andExpect(jsonPath("$[*].breeds[*]", everyItem(either(isA(String.class)).or(isEmptyOrNullString()))))
		.andExpect(jsonPath("$[*].images").isArray())
		.andExpect(jsonPath("$[*].images[*]", everyItem(either(isA(String.class)).or(isEmptyOrNullString()))));
	}

	@Test
	public void testGetPetById() throws Exception {
		int id = 32296622;
		mockMvc.perform(get("/pet/" + id).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(content().json("[{\"images\": [\"http://photos.petfinder.com/photos/pets/32296622/1/?bust=1433004499&width=500&-x.jpg\"],\"breeds\": [\"Domestic Short Hair\"],\"name\": \"Pressley\",\"animal\": \"Cat\",\"age\": \"Baby\",\"sex\": \"Female\",\"status\": \"A\",\"size\": \"Medium\",\"mix\": \"yes\",\"description\": \"Primary Color: Brown Secondary Color: White Weight: 1.85 Age: 0yrs 0mths 10wks\",\"contact\": \"ilovethemutts@yahoo.com\",\"id\": 32296622}]"));
	}

}
