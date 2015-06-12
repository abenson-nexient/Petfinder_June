package com.nexient.petfinder.models;


import java.math.BigInteger;

import org.petfinder.entity.PetfinderPetRecord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nexient.petfinder.web.util.PetFinderTypes;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pet {
	private String[] images, breeds;
	private String name, animal, age, sex, status, size, mix, description, contact ;
	private BigInteger id;

	public static Pet fromPetFinderPetRecord(PetfinderPetRecord petRecord) {
		if (petRecord == null)
			throw new IllegalArgumentException("petRecord must not be null.");

		Pet toReturn = new Pet();

		toReturn.id = petRecord.getId();
		toReturn.name = petRecord.getName();
		toReturn.mix = petRecord.getMix();
		toReturn.description = petRecord.getDescription();

		toReturn.breeds = PetFinderTypes.displayString(petRecord.getBreeds());
		toReturn.images = PetFinderTypes.displayString(petRecord.getMedia());

		toReturn.animal = PetFinderTypes.displayString(petRecord.getAnimal());
		toReturn.age = PetFinderTypes.displayString(petRecord.getAge());
		toReturn.sex = PetFinderTypes.displayString(petRecord.getSex());
		toReturn.status = PetFinderTypes.displayString(petRecord.getStatus());
		toReturn.size = PetFinderTypes.displayString(petRecord.getSize());
		toReturn.contact = PetFinderTypes.displayString(petRecord.getContact());

		return toReturn;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String[] getBreeds() {
		return breeds;
	}

	public void setBreeds(String[] breeds) {
		this.breeds = breeds;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnimal() {
		return animal;
	}

	public void setAnimal(String animal) {
		this.animal = animal;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMix() {
		return mix;
	}

	public void setMix(String mix) {
		this.mix = mix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
}
