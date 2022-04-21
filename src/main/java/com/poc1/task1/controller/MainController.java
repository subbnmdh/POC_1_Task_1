package com.poc1.task1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.poc1.task1.entities.User;
import com.poc1.task1.services.UserService;

@RestController
public class MainController 
{
		@Autowired
		private UserService userService;
		
		// Handler method to register new user
		@PostMapping("/users")
		public ResponseEntity<User> registerNewUserHanlder(@RequestBody User u)
		{
			System.out.println("\n\tNew User Registering Handler Responded");
			System.out.println(u);
			User u1=userService.registerUser(u);
			if(u1!=null)
				return ResponseEntity.status(HttpStatus.CREATED).build();  	// On success Status Code - 201
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 - Failed to create new Entity
		}
	
		// Handler Method to get User Details By UserId
		@GetMapping("/users/{id}")
		public ResponseEntity<User> getUserByIdHandler(@PathVariable("id") int userId)
		{
			System.out.println("\n\tFind User By ID Handler Responded");
			User u=userService.getUserById(userId);
			if(u!=null)
				return ResponseEntity.of(Optional.of(u));                // Status Code 200  Ok  - returns the found Entity
					
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 - Entity Not Found 
		}
		
		// Handler Method to fetch all users excluding soft-deleted ones
		@GetMapping("/users")
		public ResponseEntity<List<User>> getAllUsersHandler()
		{
			System.out.println("\n\tAll User Retrieval Handler Responded");
			List<User> allUsers = userService.getAllUsers();
			if(allUsers.size()<=0)
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 - No User Available
		
			return ResponseEntity.of(Optional.of(allUsers));            // 200 - returns All Users as list
		}
		
		//Handler Method to fetch all users including soft-deleted ones
		@GetMapping("/allusers")
		public ResponseEntity<List<User>> getOverAllUsersHandler()
		{
			System.out.println("\n\tOver All Users Retrieval Handler Responded");
			List<User> allUsers = userService.getOverAllUsers();
			if(allUsers.size()<=0)
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();   // 204 - No User Available
			
			return ResponseEntity.of(Optional.of(allUsers));              // 200 - returns All Users as list
		}
		
		//Handler Method to soft delete the user record based on id
		@DeleteMapping("/deluser/{id}")
		public ResponseEntity<Void> softDeleteHandler(@PathVariable("id") int id)
		{
			System.out.println("\n\tSoft-Delete Handler Responded");
			if(userService.softDelUserById(id))
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 	// 204 -- NO_CONTENT - Successfully Deleted, now the user is not an active one

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 INTERNAL_SERVER_ERROR occurs during delete Operation
		}
		
		//Handler Method to hard delete the user record based on id  
		@DeleteMapping("users/{id}")
		public ResponseEntity<Void> deleteUserHandler(@PathVariable("id") int id)
		{
			System.out.println("\n\tHard-Delete Handler Responded");
			if(userService.delUserById(id))
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 -- NO_CONTENT - Successfully Deleted, now the user no more exists
			else
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 INTERNAL_SERVER_ERROR occurs during delete Operation
		}
		
		// Handler Method to fetch users by firstname 
		@GetMapping("/usersbyfname/{fname}")
		public ResponseEntity<List<User>> userByFNameHandler(@PathVariable("fname") String fname)
		{
			System.out.println("\n\tAll User Retrieval By Firstname Handler Responded");
			List<User> allUsers = userService.getUserByFirstname(fname);
			if(allUsers.size()<=0)
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 204 - No User Available
		
			return ResponseEntity.of(Optional.of(allUsers));            // 200 - returns All Users as list
		}
		
		// Handler Method to fetch users by lastname 
		@GetMapping("/usersbylname/{lname}")
		public ResponseEntity<List<User>> userByLNameHandler(@PathVariable("lname") String lname)
		{
			System.out.println("\n\tAll User Retrieval By Lastname Handler Responded");
			List<User> allUsers = userService.getUserByLastname(lname);
			if(allUsers.size()<=0)
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 - No User Available
		
			return ResponseEntity.of(Optional.of(allUsers));            // 200 - returns All Users as list
		}
		
		// Handler Method to fetch users by pincode
		@GetMapping("/usersbypincode/{pin}")
		public ResponseEntity<List<User>> userByPincodeHandler(@PathVariable("pin") String pincode)
		{
			System.out.println("\n\tAll User Retrieval By Pincode Handler Responded");
			List<User> allUsers = userService.getUserByPincode(pincode);
			if(allUsers.size()<=0)
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 - No User Available
		
			return ResponseEntity.of(Optional.of(allUsers));            	// 200 - returns All Users as list
		}
		
		// Handler Method to fetch soft deleted user entities
		@GetMapping("/alldelusers")
		public ResponseEntity<List<User>> getAllDeletedUsersHandler()
		{
			System.out.println("\n\tAll Soft Deleted User Entities Retrieval Handler Responded");
			List<User> allSoftDeletedUsers = userService.getAllSoftDeletedUsers();
			if(allSoftDeletedUsers.size()<=0)
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  		// 204 - No User Available
			
			return ResponseEntity.of(Optional.of(allSoftDeletedUsers));            // 200 - returns All Users as list
		}
		
		// Handler Method to fetch sorted user entities based on given property
		@GetMapping("/users_sorteddob/{op}")
		public ResponseEntity<List<User>> getAllUserSortByDobHandler(@PathVariable("op") Boolean op)
		{
			System.out.println("\n\tAll User Sorted DOB Retrieval Handler Responded");
			List<User> allUsers = userService.getAllUserSortedByDob(op);
			if(allUsers.size()<=0)
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 - No User Available
		
			return ResponseEntity.of(Optional.of(allUsers));            // 200 - returns All Users as list
		}
		
		// Handler Method to fetch sorted user entities based on given property
	  @GetMapping("/users_sorteddoj/{op}") 
	  public ResponseEntity<List<User>> getAllUserSortByDojHandler(@PathVariable("op") Boolean op) 
	  {
			  System.out.println("\n\tAll User Sorted DOJ Retrieval Handler Responded");
			  List<User> allUsers = userService.getAllUserSortedByDoj(op);
			  if(allUsers.size()<=0) return
					  ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 	// 204 - No User Available
	  
	  return ResponseEntity.of(Optional.of(allUsers)); 			// 200 - returns All Users as list 
	  }
	  
	  // Handler Method to update/Edit user entity by id
	  @PutMapping("/users/{id}")
	  public ResponseEntity<User> updateUserHandler(@RequestBody User u,@PathVariable("id") Integer id)

	  {
		  		User updateUser = userService.updateUser(id, u);
		  		if(updateUser!=null)
		  			return ResponseEntity.ok(updateUser); 	// 200 Successfully Updated , returns Updated Entity
		  		
		  		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();  // 500 - INTERNAL_SERVER_ERROR occurs during Update operation   
	  }
	  
	/*
	 * // Handler Method to delete user entity by id (Hard Delete)
	 * 
	 * @DeleteMapping("/users/{id}") public ResponseEntity<Void>
	 * deleteUserHandler(@PathVariable("id") Integer id) {
	 * System.out.println("\n\tSingle User Hard Delete Handler Responded");
	 * if(userService.delUserById(id)) return
	 * ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 -- NO_CONTENT -
	 * Successfully Deleted, now the Entity no more exist
	 * 
	 * else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	 * // 500 INTERNAL_SERVER_ERROR occurs during delete Operation }
	 */
}
