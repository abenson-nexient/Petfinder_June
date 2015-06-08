package com.nexient.petfinder.models;


import java.math.BigInteger;
import java.util.Objects;

import org.petfinder.entity.PetPhotoType;
import org.petfinder.entity.PetfinderPetRecord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pet {
	private String[] images, breeds;
	private String name, animal, age, sex, status, size, mix, description, contact ;
	private BigInteger id;

	public static Pet fromPetFinderPetRecord(PetfinderPetRecord record) {
		Pet toReturn = new Pet();
		toReturn.breeds = record.getBreeds().getBreed().stream().toArray((int size) -> new String[size]);
		if (record.getMedia().getPhotos() == null) {
			toReturn.images = new String[0];
		} else {
			toReturn.images = record.getMedia().getPhotos().getPhoto().stream()
					.filter(Objects::nonNull)
					.filter((ppt) -> ppt.getSize().equals("x"))
					.map((ppt) -> ppt.getValue())
					.toArray((int size) -> new String[size]);			
		}
		toReturn.id = record.getId();
		toReturn.name = record.getName();
		toReturn.animal = record.getAnimal() != null ? record.getAnimal().value() : null;
		toReturn.age = record.getAge().getValue();
		toReturn.sex = record.getSex().getDescription();
		toReturn.status = record.getStatus().value();
		toReturn.size = record.getSize().getDescription();
		toReturn.mix = record.getMix();
		toReturn.description = record.getDescription();
		toReturn.contact = record.getContact().getEmail();
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
