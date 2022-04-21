package com.poc1.task1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.poc1.task1.dao.UserRepository;
import com.poc1.task1.entities.Address;
import com.poc1.task1.entities.User;
import com.poc1.task1.services.UserService;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class Poc1Task1ApplicationTests {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	private User registerUser=null;
	/*
	 * @Test void contextLoads() { }
	 */
	
	// Test case for new User registration service
	@Test
	@Order(1)
	public void registerUserServiceTest()
	{
		assertNotNull(registerUser, "User Registred Successfully");
	}
	
	// Test case for find User by id
	@Test
	@Order(2)
	public void findUserByIdTest()
	{
			User userById = userService.getUserById(registerUser.getUserId());
			
			assertEquals(userById, registerUser); 
	}
	// Test case for finding user by firstname
	@Test
	@Order(3)
	public void getUserByFirstnameTest()
	{
		String fname="Manish";
		List<User> userByFirstname = userService.getUserByFirstname(fname);
		
		for(User u:userByFirstname)
		{
			assertEquals(u.getFirstname(), fname);
		}
		
	}
	
	// Test case for finding user by lastname
		@Test
		@Order(4)
		public void getUserByLastnameTest()
		{
			String lname="Rai";
			List<User> userByFirstname = userService.getUserByFirstname(lname);
			
			for(User u:userByFirstname)
			{
				assertEquals(u.getFirstname(), lname);
			}
			
		}
	
		// Test case for finding user by pincode
		@Test
		@Order(5)
		public void getUserByPincodeTest()
		{
			String pincode="221002";
			List<User> userByFirstname = userService.getUserByFirstname(pincode);
			
			for(User u:userByFirstname)
			{
				assertEquals(u.getFirstname(), pincode);
			}
		}
	
		// Test case for finding all Active users
		@Test
		@Order(6)
		public void getAllUsersTest()
		{
			List<User> allUsers = userService.getAllUsers();
			assertThat(allUsers.size()).isGreaterThanOrEqualTo(0);
		}	
		
		// Test case for finding all Active as well as Non-active users
		@Test
		@Order(7)
		public void getOverAllUsersTest()
		{
			List<User> allUsers = userService.getOverAllUsers();
			assertThat(allUsers.size()).isGreaterThanOrEqualTo(0);
		}	
		
		// Test case for to update a User by id
		@Test
		@Order(8)
		public void updateUserTest()
		{
			User u=new User("Manish Kumar","Rai",new Date(Date.parse("1992/05/20")),new Date(Date.parse("2018/10/10")),"manish.kumar123@gmail.com",false);
			u.setAddress(new Address("B12/67, Kamachcha Tiraha, Kamchcha","Vanarasi","UP","221003"));
			
			User updateUser = userService.updateUser(registerUser.getUserId(), u);
			
			assertEquals(u.getFirstname(), updateUser.getFirstname());
			assertEquals(u.getAddress().getStreet(), updateUser.getAddress().getStreet());
			assertEquals(u.getAddress().getPincode(), updateUser.getAddress().getPincode());
		}
		
		// Test Case for testing hard delete User by id
		@Test
		@Order(9)
		public void delUserByIdTest()
		{
			assertTrue(userService.delUserById(registerUser.getUserId()));  
		}
		
		//Test Case for testing soft delete User By Id 
		@Test
		@Order(10)
		public void softDelUserByIdTest()
		{
			boolean softDelUserById = userService.softDelUserById(registerUser.getUserId());
			
			assertTrue(softDelUserById);
			
			// Checking the flag field of the soft deleted user It must be set as true
			Integer id=registerUser.getUserId();
			 User findById = userRepository.findById(id).get();	
			boolean flag = findById.isDeleted();
			
			assertTrue(flag); // deleted flag field is true hence user entity is softly deleted 
		}
		
		//Test case to retrieve all soft deleted Users
		@Test
		@Order(11)
		public void getAllSoftDeletedUsersTest()
		{
			List<User> allSoftDeletedUsers = userService.getAllSoftDeletedUsers();
			assertThat(allSoftDeletedUsers.size()).isGreaterThanOrEqualTo(0);
			
			// Checking the flag field of all such user who are softly deleted. For each User entity present in the List it should be set as true   
			for(User u:allSoftDeletedUsers)
			{
				assertTrue(u.isDeleted());
			}
		}
		//Test case to sort User based on their DOB in Descending Order...
		@Test
		@Order(12)
		public void getAllUserSortedByDobTest()
		{
			User u=new User("Ashish","Kumar",new Date(Date.parse("1994/01/20")),new Date(Date.parse("2021/01/07")),"ashish.kumar123@gmail.com",false);
			u.setAddress(new Address("J12/47, Labour Colony, Chawkaghat","Vanarasi","UP","221002"));
			userService.registerUser(u);
			List<User> allUserSortedByDob = userService.getAllUserSortedByDob(false);
			
			assertEquals(new Date(Date.parse("1994/01/20")), allUserSortedByDob.get(0).getDob());
			assertEquals(new Date(Date.parse("1992/05/20")), allUserSortedByDob.get(1).getDob());
			
		}
	
		//Test case to sort User based on their DOJ in Ascending Order...
				@Test
				@Order(13)
				public void getAllUserSortedByDojTest()
				{
					User u=new User("Ashish","Kumar",new Date(Date.parse("1994/01/20")),new Date(Date.parse("2021/01/07")),"ashish.kumar123@gmail.com",false);
					u.setAddress(new Address("J12/47, Labour Colony, Chawkaghat","Vanarasi","UP","221002"));
					userService.registerUser(u);
					List<User> allUserSortedByDob = userService.getAllUserSortedByDoj(false);
					
					assertEquals(new Date(Date.parse("2018/10/10")), allUserSortedByDob.get(0).getDoj());
					assertEquals(new Date(Date.parse("2021/01/07")), allUserSortedByDob.get(1).getDoj());
					
				}
		
	@BeforeEach
	public void setup()
	{
		System.out.println("\n\tSetting up the System...");
		User u=new User("Manish","Rai",new Date(Date.parse("1992/05/20")),new Date(Date.parse("2018/10/10")),"manish.kumar123@gmail.com",false);
		u.setAddress(new Address("L12/30, Samne Ghat, Lanka","Vanarasi","UP","221010"));
		registerUser= userRepository.save(u);
	}
	
	@AfterEach
	public void tearDown()
	{
		System.out.println("\n\tTearing Down...");
		
		userRepository.deleteAll();
	}
}
