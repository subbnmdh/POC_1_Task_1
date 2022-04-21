package com.poc1.task1.entities;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

@Data
@Entity
@Table(name="Address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable(true)
public class Address 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="Address_Id")
	private int adsId;
	
	@Column(name="Street",nullable = false,updatable = true, length = 100)
	@NotBlank(message = "Specify street name !!")
	@NotEmpty(message = "Street Name is not specified !!")
	private String street;
	
	@Column(name="City",nullable = false,updatable = true, length = 100)
	@NotBlank(message = "Specify city name !!")
	@NotEmpty(message = "City Name is not specified !!")
	@Pattern(regexp = "[a-zA-z\\s\\.]+",message = "Invalid City Name !!")
	private String city;
	
	@Column(name="State",nullable = false,updatable = true, length = 50)
	@NotBlank(message = "Specify state name !!")
	@NotEmpty(message = "State Name is not specified !!")
	@Pattern(regexp = "[a-zA-z\\s\\.]+",message = "Invalid State Name !!")
	private String state;
	
	@Column(name="Pincode",nullable = false,updatable = true, length = 100)
	//@Pattern(regexp="^[1-9]{1}[0-9]{2}\\\\s{0,1}[0-9]{3}$",message = "Invalid pincode !!")
	private String pincode;
	
	
	public Address(
			@NotBlank(message = "Specify street name !!") @NotEmpty(message = "Street Name is not specified !!") String street,
			@NotBlank(message = "Specify city name !!") @NotEmpty(message = "City Name is not specified !!") @Pattern(regexp = "[a-zA-z\\s\\.]+", message = "Invalid City Name !!") String city,
			@NotBlank(message = "Specify state name !!") @NotEmpty(message = "State Name is not specified !!") @Pattern(regexp = "[a-zA-z\\s\\.]+", message = "Invalid State Name !!") String state,
			String pincode) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}
	@Override
	public String toString() {
		return "Address [adsId=" + adsId + ", street=" + street + ", city=" + city + ", state=" + state + ", pincode="
				+ pincode + "]";
	}
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}



}
	
	

