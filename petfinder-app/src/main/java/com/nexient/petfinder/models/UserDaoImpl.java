package com.nexient.petfinder.models;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import com.nexient.petfinder.models.User;
 
@Repository
public class UserDaoImpl implements UserDao {
 
	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {
		Session session = null;
		SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
		session = sessionFactory.openSession();
 
		List<User> users = new ArrayList<User>();
 
		users = sessionFactory.getCurrentSession()
			.createQuery("from User where username=?")
			.setParameter(0, username)
			.list();
 
		session.close();
		
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
		
	}
 
}