package com.nexient.petfinder.models;

public class User {
	private int userID;
	private String username, password;
	
	private User () {
		Session sess = getSessionFactory().openSession();
		//nothing
	}
	
	private User (int id, String name, String pass) {
		this.username = name;
		this.userID = id;
		this.password = pass;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
