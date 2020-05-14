package com.miu.onlinemarket.service;

import java.util.List;

import com.miu.onlinemarket.domain.City;
import com.miu.onlinemarket.domain.Country;
import com.miu.onlinemarket.domain.State;

public interface AddressService {

	public List<Country> loadCountries() throws Exception;

	public List<State> loadStates(long id) throws Exception;

	public List<City> loadCities(long id) throws Exception;

}
