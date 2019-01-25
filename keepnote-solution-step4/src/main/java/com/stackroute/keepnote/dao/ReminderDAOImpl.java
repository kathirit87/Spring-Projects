package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;

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
public class ReminderDAOImpl implements ReminderDAO {
	
	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	private SessionFactory sessionFactory;

	public ReminderDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;

	}
	
	public Session getSession() {
		
		return sessionFactory.getCurrentSession();
	}
	
	/*
	 * Create a new reminder
	 */

	public boolean createReminder(Reminder reminder) {
		
		return (getSession().save(reminder)) != null ? true : false ;

	}
	
	/*
	 * Update an existing reminder
	 */

	public boolean updateReminder(Reminder reminder) {
		return (getSession().save(reminder)) != null ? true : false ;

	}

	/*
	 * Remove an existing reminder
	 */
	
	public boolean deleteReminder(int reminderId) {
		Reminder reminder;
		try {
			reminder = getReminderById(reminderId);
			
			if(reminder!= null ) {
				getSession().delete(reminder);
				return true;
			}
		} catch (ReminderNotFoundException e) {
			e.printStackTrace();
		}
		return false;

	}

	/*
	 * Retrieve details of a specific reminder
	 */
	
	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
		Query query =  getSession().createQuery("from Reminder where reminderId=?1");
		query.setParameter(1, reminderId);
		try {
			return (Reminder) query.getSingleResult();
		}catch (Exception e) {
			throw new ReminderNotFoundException("Reminder not found");
		}

	}

	/*
	 * Retrieve details of all reminders by userId
	 */
	
	public List<Reminder> getAllReminderByUserId(String userId) {
		Query query =  getSession().createQuery("from Reminder where reminderCreatedBy=?1");
		query.setParameter(1, userId);
		return (List<Reminder>) query.getResultList();
		
	}

}
