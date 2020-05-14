package com.miu.onlinemarket.domain;

public class State {

	private long id;

	private String name;

	private long country_id;

	public State() {
	}

	public State(long id, String name, long country_id) {
		super();
		this.id = id;
		this.name = name;
		this.country_id = country_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(long country_id) {
		this.country_id = country_id;
	}

}
