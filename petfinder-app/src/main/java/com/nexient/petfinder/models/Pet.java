package com.nexient.petfinder.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Pet {
	private int sid, pid; //, animal, age, sex, status, size, breed, mix;
	//private String name, description, images;
	
	public Pet () {
		
	}
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	
	/**
	public static Pet fromPetFinderPetRecord(PetfinderPetRecord record) {
		Pet toReturn = new Pet();
		toReturn.pid = Integer.parseInt(record.getShelterPetId());
		toReturn.sid = Integer.parseInt(record.getShelterId());
		return toReturn;
	}
	**/
}
