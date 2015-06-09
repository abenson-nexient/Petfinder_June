package com.nexient.petfinder.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexient.petfinder.models.User;

@RestController
@RequestMapping({"/user", "/user/"})
public class UserController {

	@RequestMapping("/new/{username}/{password}/{id}")
	public void newUser(@PathVariable String username, @PathVariable String password, @PathVariable int id) {
		Session session = null;
		SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
		session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		User obj = new User();
		obj.setUsername(username);
		obj.setPassword(password);
		obj.setUserid(id);

		session.save(obj);
		session.flush();
		tx.commit();
		session.close();
	}

	@RequestMapping({"/{id}", "/{id}/"})
	public User getUserByID(@PathVariable int id) {
		Session session = null;
		SessionFactory sessionFactory =new Configuration().configure().buildSessionFactory();
		session = sessionFactory.openSession();

		User obj;
		obj = (User) session.get(User.class.getName(), id);

		session.close();

		return obj;
	}

	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
}
