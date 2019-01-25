package com.stackroute.keepnote.dao;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class UserDaoImpl implements UserDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	
	private SessionFactory sessionFactory;

	@Autowired(required=true)
	public UserDaoImpl(SessionFactory sessionFactory) {

		this.sessionFactory= sessionFactory;
	}

	public Session getSession() {
		
		return sessionFactory.getCurrentSession();
	}
	/*
	 * Create a new user
	 */

	public boolean registerUser(User user) {

		return (getSession().save(user)) != null ? true : false ;
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {

		return (getSession().save(user)) != null ? true : false ;

	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String userId) {

		Query query =  getSession().createQuery("from User where userId=?1");
		query.setParameter(1, userId);
		try {
			return (User) query.getSingleResult();
		}catch (Exception e) {
			return null;
		}
		
	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		
		try {
			System.out.println("validate inside ::"+userId);
			User user = getUserById(userId);
			System.out.println("validate user ::"+user);
			if(user== null) {
				throw new UserNotFoundException("User Not Found.");
			}
			
			if(user.getUserPassword().equalsIgnoreCase(password)) {
				return true;
			}
			return false;
		}catch (Exception e) {
			throw new UserNotFoundException("User Not Found.");
		}
		

	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(String userId) {
		
		User user = getUserById(userId);
		if(user!= null ) {
			getSession().delete(user);
			return true;
		}
		return false;

	}

}
