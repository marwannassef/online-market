package com.miu.onlinemarket.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotEmpty
	@Size(min = 5, max = 20)
	private String street;

	@Min(value = 1, message = "{validation.number.min}")
	private long city;

	@Min(value = 1, message = "{validation.number.min}")
	private long state;

	@NotEmpty
	@Size(min = 5, max = 5, message = "{validation.zipCode.size}")
	private String zipCode;

	@Min(value = 1, message = "{validation.number.min}")
	private long country;

	public Address() {
	}

	public Address(String street, long city, long state, String zipCode, long country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public long getCity() {
		return city;
	}

	public void setCity(long city) {
		this.city = city;
	}

	public long getState() {
		return state;
	}

	public void setState(long state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public long getCountry() {
		return country;
	}

	public void setCountry(long country) {
		this.country = country;
	}

}
