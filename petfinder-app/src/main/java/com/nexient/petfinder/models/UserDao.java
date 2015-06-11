package com.nexient.petfinder.models;

import com.nexient.petfinder.models.User;;
 
public interface UserDao {
 
	User findByUserName(String username);
 
}