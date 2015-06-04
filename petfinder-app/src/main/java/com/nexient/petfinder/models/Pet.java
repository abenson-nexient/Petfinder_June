package com.nexient.petfinder.models;

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
}
