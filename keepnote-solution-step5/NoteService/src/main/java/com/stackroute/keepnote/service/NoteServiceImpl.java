package com.stackroute.keepnote.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.repository.NoteRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */

@Service
public class NoteServiceImpl implements NoteService{

	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	
	@Autowired
	private NoteRepository noteRepository;
	
	
	
	public NoteServiceImpl(NoteRepository noteRepository) {
		this.noteRepository=noteRepository;
	}

	/*
	 * This method should be used to save a new note.
	 */
	public boolean createNote(Note note) {
		
		NoteUser noteUser = new NoteUser();
		if(note== null) {
			return false;
		}
		List<Note> notes = new ArrayList<>();
		notes.add(note);
		noteUser.setNotes(notes);
		noteUser.setUserId(note.getNoteCreatedBy());
		
		NoteUser noteUser2=  noteRepository.insert(noteUser);
		if(noteUser2!=null) {
			return true;
		}
		return false;
	}
	
	/* This method should be used to delete an existing note. */

	
	public boolean deleteNote(String userId, int noteId) {
		
		if(userId!= null) {
			
			Note note = null;
			try {
				note = getNoteByNoteId(userId, noteId);
			} catch (NoteNotFoundExeption e) {
				throw new NullPointerException();
			}
			if(note!= null) {
				NoteUser noteUser = new NoteUser();
				List<Note> notes = new ArrayList<>();
				notes.add(note);
				noteUser.setNotes(notes);
				noteUser.setUserId(userId);
				noteRepository.delete(noteUser);
				return true;
			}
		}
		
		return false;
	}
	
	/* This method should be used to delete all notes with specific userId. */

	
	public boolean deleteAllNotes(String userId) {
		
		if(userId!= null) {
			
			noteRepository.deleteAllByUserId(userId);
			return true;
		}
		
		return false;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note, int id, String userId) throws NoteNotFoundExeption {
		
		Note note1 = null;
		if(userId != null) {
			
			try {
				note1 =  getNoteByNoteId(userId, id);
			} catch (NoteNotFoundExeption e) {
				throw e;
			}
			if(note!= null && note1!= null) {
				NoteUser noteUser = new NoteUser();
				List<Note> notes = new ArrayList<>();
				notes.add(note);
				noteUser.setNotes(notes);
				noteUser.setUserId(userId);
				noteRepository.insert(noteUser);
				return note;
			}
			
		}
		
		return null;
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String userId, int noteId) throws NoteNotFoundExeption {
		
		try {
			if(userId!= null && noteId > 0) {
				
				Optional<NoteUser> optional =  noteRepository.findById(userId);
				if(optional.isPresent() && optional.get().getNotes()!= null) {
					
					Iterator<Note> notes = optional.get().getNotes().iterator();
					while (notes.hasNext()) {
						Note note = (Note) notes.next();
						if(note.getNoteId() ==noteId ) {
							 return  note;
						}
					}
					return null;
				}
			}
			
		} catch (Exception e) {
			throw new NoteNotFoundExeption("Note Not Found!");
		}
		
		throw new NoteNotFoundExeption("Note Not Found!");
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		
		if(userId!= null) {
			
			Optional<NoteUser> optional =  noteRepository.findById(userId);
			
			if(optional.isPresent() && optional.get().getNotes()!= null) {
				
				return optional.get().getNotes();
			}
		}
			
		return null;
	}

}
