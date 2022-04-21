package com.poc1.task1.entities;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name="User")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(true)
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="UserId")
	private int userId;
	
	@Column(name="FirstName",nullable = false,insertable = true,updatable = true,length = 50)
	@NotBlank(message = "Must specify user's firstname !!")
	@Size(min=3,max=50,message = "Min. 3 and Max. 50 Characters Allowed !!")
	@Pattern(regexp = "[a-zA-z\\s\\.]+",message = "Invalid First Name !!")
	private String firstname;
	
	@Column(name="LastName",nullable = true,insertable = true,updatable = true,length = 50)
	//@NotBlank(message = "Must specify user's lastname !!")
	@Size(min=0,max=50,message = "Min. 0 and Max. 50 Characters Allowed !!")
	@Pattern(regexp = "[a-zA-z\\s\\.]+",message = "Invalid Last Name !!")
	private String lastname;
	
	@Column(name="DOB",nullable = false,updatable = false)
	@NotNull(message = "Must Specify Users's DOB !!")
	@Past(message = "Invalid DOB !!")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date dob;
	
	@Column(name="Date_of_Joining",nullable = false,updatable = false)
	@NotNull(message = "Must Specify Users's Date of Joining !!")
	@PastOrPresent(message = "Invalid Date of Joining !!")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date doj;
	
	@Column(name="Email_ID",nullable = false)
	@NotEmpty(message = "Must specify user's Email ID !!")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "Invalid Email ID !!")
	private String email;
	
	@OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
	@JoinColumn(name="Address_Id",referencedColumnName = "Address_Id",nullable = false)
	private Address address;
	
	@Column(name="Deleted",nullable = true)
	private boolean deleted=Boolean.FALSE;
	
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



	public User(
			@NotBlank(message = "Must specify user's firstname !!") @Size(min = 3, max = 50, message = "Min. 3 and Max. 50 Characters Allowed !!") @Pattern(regexp = "[a-zA-z\\s\\.]+", message = "Invalid First Name !!") String firstname,
			@Size(min = 0, max = 50, message = "Min. 0 and Max. 50 Characters Allowed !!") @Pattern(regexp = "[a-zA-z\\s\\.]+", message = "Invalid Last Name !!") String lastname,
			@NotNull(message = "Must Specify Users's DOB !!") @Past(message = "Invalid DOB !!") Date dob,
			@NotNull(message = "Must Specify Users's Date of Joining !!") @PastOrPresent(message = "Invalid Date of Joining !!") Date doj,
			@NotEmpty(message = "Must specify user's Email ID !!") @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Invalid Email ID !!") String email,
			boolean deleted) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.doj = doj;
		this.email = email;
		this.deleted = deleted;
	}


}
