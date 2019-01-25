package com.stackroute.keepnote.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

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
public class CategoryDAOImpl implements CategoryDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */
	@Autowired
	private SessionFactory sessionFactory;
	
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}

	public Session getSession() {
		
		return sessionFactory.getCurrentSession();
	}
	
	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		return (getSession().save(category)) != null ? true : false ;

	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		Category category;
		try {
			category = getCategoryById(categoryId);
			
			if(category!= null ) {
				getSession().delete(category);
				return true;
			}
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
		}
		return false;

	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		return (getSession().save(category)) != null ? true : false ;

	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		
		Query query =  getSession().createQuery("from Category where categoryId=?1");
		query.setParameter(1, categoryId);
		try {
			return (Category) query.getSingleResult();
		}catch (Exception e) {
			throw new CategoryNotFoundException("Category not found");
		}

	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		Query query =  getSession().createQuery("from Category where categoryCreatedBy=?1");
		query.setParameter(1, userId);
		return (List<Category>) query.getResultList();

	}

}
