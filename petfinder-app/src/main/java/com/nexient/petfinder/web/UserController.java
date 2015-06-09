package com.nexient.petfinder.web;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexient.petfinder.models.User;

@RestController
@RequestMapping(value="/user")
public class UserController {
	
	@RequestMapping("/test")
	public String test() {
		Session session = null;
		SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		User obj = new User();
		obj.setUsername("Testman");
		obj.setPassword("Testpass");
		obj.setUserid(42);

		session.save(obj);
		session.flush();
		tx.commit();
		session.close();
		
		return "Success!(?)";
	}
	
	@RequestMapping("/")
	public String index() {
		 return "Greetings from UserController!";
	}
}
