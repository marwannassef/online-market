package com.miu.onlinemarket.domain;

public class Country {

	private long id;

	private String sortname;

	private String name;

	private long phoneCode;

	public Country() {
	}

	public Country(long id, String sortname, String name, long phoneCode) {
		this.id = id;
		this.sortname = sortname;
		this.name = name;
		this.phoneCode = phoneCode;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(long phoneCode) {
		this.phoneCode = phoneCode;
	}

}
