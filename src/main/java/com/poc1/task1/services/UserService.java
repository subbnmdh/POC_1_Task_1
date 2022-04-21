package com.poc1.task1.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.poc1.task1.dao.UserRepository;
import com.poc1.task1.entities.Address;
import com.poc1.task1.entities.User;

@Service
public class UserService 
{
	@Autowired
	private UserRepository userRepository;	
	
	public User registerUser(User u)
	{
		return userRepository.save(u);
	}
	
	public User getUserById(int id)
	{
		try
		{
			Optional<User> user = userRepository.findById(id);
			return user.get();
		}
		catch(NoSuchElementException ex)
		{
			System.out.println(ex);
			return null;
		}
	}
	public List<User> getUserByFirstname(String fname)
	{
		try
		{
			List<User> user = userRepository.findByFirstname(fname);
			return user;
		}
		catch(NoSuchElementException ex)
		{
			System.out.println(ex);
			return null;
		}
	}
	
	public List<User> getUserByLastname(String lname)
	{
		try
		{
			List<User> user = userRepository.findByLastname(lname);
			return user;
		}
		catch(NoSuchElementException ex)
		{
			System.out.println(ex);
			return null;
		}
	}
	public List<User> getUserByPincode(String pincode)
	{
		try
		{
			List<User> user = userRepository.findByPincode(pincode);
			return user;
		}
		catch(NoSuchElementException ex)
		{
			System.out.println(ex);
			return null;
		}
	}

	public List<User> getAllUsers()
	{
		return userRepository.findAll();
	}
	public User updateUser(int id,User u)
	{
		User u1=getUserById(id);
		if(u1!=null)
		{
			u1.setFirstname(u.getFirstname());
			u1.setLastname(u.getLastname());
			u1.setEmail(u.getEmail());
			Address ads=u1.getAddress();
			ads.setStreet(u.getAddress().getStreet());
			ads.setCity(u.getAddress().getCity());
			ads.setState(u.getAddress().getState());
			ads.setPincode(u.getAddress().getPincode());
			u1.setAddress(ads);
			return userRepository.save(u1);
		}
		else
			return null;
	}
	public boolean delUserById(int id)
	{
		try
		{
			userRepository.deleteById(id);
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			return false;
		}
	}
	public List<User> getOverAllUsers()
	{
		return userRepository.findOverAll();
	}
	public List<User> getAllSoftDeletedUsers()
	{
		return userRepository.findSoftDeleted();
	}
	public boolean softDelUserById(int id)
	{
		try
		{
			User user = this.getUserById(id);
			user.setDeleted(true);    // Sets the boolean flag as true for soft delete operation
			this.userRepository.save(user);  
			return true;
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			return false;
		}
	}
	
	public List<User> getAllUserSortedByDob(boolean op)
	{
		return userRepository.findByOrderByDobDesc(op);
	}
	
	  public List<User> getAllUserSortedByDoj(boolean op) 
	  { 
		  return userRepository.findByOrderByDojAsc(op); 
	  }
	 
	
}
