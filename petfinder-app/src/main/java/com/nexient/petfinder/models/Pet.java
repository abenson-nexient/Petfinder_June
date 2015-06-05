package com.nexient.petfinder.models;


import java.math.BigInteger;

import org.petfinder.entity.PetfinderPetRecord;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pet {
	private String shelterId;
	private BigInteger id;
	
	public String getShelterId() {
		return shelterId;
	}

	public void setShelterId(String sid) {
		this.shelterId = sid;
	}
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public static Pet fromPetFinderPetRecord(PetfinderPetRecord record) {
		Pet toReturn = new Pet();
		toReturn.id = record.getId();
		toReturn.shelterId = record.getShelterId();
		return toReturn;
	}
}
