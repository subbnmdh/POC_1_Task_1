package com.poc1.task1.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.OrderBy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc1.task1.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> 
{
	
	/*
	 * // custom method to test if a user of the given Id exists or not...
	 * 
	 * @Query("Select CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u")
	 * Boolean isUserExistsById(Integer id);
	 */
		
		//Custom finder method to find user based on Id...
		@Query("select u from User u where u.userId=:id and u.deleted=FALSE")
		public Optional<User> findById(@Param("id") int id);
		
		//Custom finder methods to find user based on different criteria.... 
		@Query("select u from User u where u.firstname=:fname and u.deleted=FALSE")
		public List<User> findByFirstname(@Param("fname") String firstname);
		
		@Query("select u from User u where u.lastname=:lname and u.deleted=FALSE")
		public List<User> findByLastname(@Param("lname") String lastname);
		
		@Query("select u from User u where u.address.pincode=:pincode and u.deleted=FALSE")
		public List<User> findByPincode(@Param("pincode") String pincode);
		
		//custom sorting method to sort user record based on DOB sorting criteria...
		@Query("select u from User u where deleted=:op order by u.dob desc")
		public List<User> findByOrderByDobDesc(@Param("op") boolean op);
		
		//custom sorting method to sort user record based on DOB sorting criteria...
		@Query("select u from User u where deleted=:op order by u.doj")
		public List<User> findByOrderByDojAsc(@Param("op") boolean op);
		
		
		
		//Defining query for findAll() to get all those users records have not been softly deleted...
		@Query("select u from User u where u.deleted=FALSE")
		public List<User> findAll();
		
		//custom finder method to find all the users including those who are softly deleted
		@Query("select u from User u")
		public List<User> findOverAll();
		
		//custom finder method to find all softly deleted user entities
		@Query("select u from User u where u.deleted=TRUE")
		public List<User> findSoftDeleted();
				
}
