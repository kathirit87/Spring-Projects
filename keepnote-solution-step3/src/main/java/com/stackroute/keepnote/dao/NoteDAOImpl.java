package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.NoteNotFoundException;
import com.stackroute.keepnote.model.Note;

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
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	private SessionFactory sessionFactory;

		
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}
	
	public Session getSession() {
		
		return sessionFactory.getCurrentSession();
	}
	/*
	 * Create a new note
	 */
	
	public boolean createNote(Note note) {
		return (getSession().save(note)) != null ? true : false ;

	}

	/*
	 * Remove an existing note
	 */
	
	public boolean deleteNote(int noteId) {
		Note note;
		try {
			note = getNoteById(noteId);
			
			if(note!= null ) {
				getSession().delete(note);
				return true;
			}
		} catch (NoteNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Retrieve details of all notes by userId
	 */
	
	public List<Note> getAllNotesByUserId(String userId) {
		Query query =  getSession().createQuery("from Note where createdBy=?1");
		query.setParameter(1, userId);
		return (List<Note>) query.getResultList();

	}

	/*
	 * Retrieve details of a specific note
	 */
	
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Query query =  getSession().createQuery("from Note where noteId=?1");
		query.setParameter(1, noteId);
		try {
			return (Note) query.getSingleResult();
		}catch (Exception e) {
			throw new NoteNotFoundException("Note not found");
		}

	}

	/*
	 * Update an existing note
	 */

	public boolean UpdateNote(Note note) {
		return (getSession().save(note)) != null ? true : false ;

	}

}
