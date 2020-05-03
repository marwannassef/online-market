package com.miu.onlinemarket.domain;

public class City {

	private long id;

	private String name;

	private long state_id;

	public City() {
	}

	public City(long id, String name, long state_id) {
		super();
		this.id = id;
		this.name = name;
		this.state_id = state_id;
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

	public long getState_id() {
		return state_id;
	}

	public void setState_id(long state_id) {
		this.state_id = state_id;
	}

}
