package com.nexient.petfinder.dao;

import com.nexient.petfinder.models.User;
 
public interface UserDao {
	User findByUserName(String username);
}