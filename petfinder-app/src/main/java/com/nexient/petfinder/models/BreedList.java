package com.nexient.petfinder.models;

import com.systemsinmotion.petrescue.entity.AnimalType;

public class BreedList {
	private AnimalType animal;
	private String[] breeds;

	public BreedList() {}
	public BreedList(AnimalType animal, String[] breeds) {
		this.animal = animal;
		this.breeds = breeds;
	}

	public AnimalType getAnimal() {
		return animal;
	}

	public void setAnimal(AnimalType animal) {
		this.animal = animal;
	}

	public String[] getBreeds() {
		return breeds;
	}

	public void setBreeds(String[] list) {
		this.breeds = list;
	}
}
