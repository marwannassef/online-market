package com.miu.onlinemarket.service.impl;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import com.miu.onlinemarket.domain.City;
import com.miu.onlinemarket.domain.Country;
import com.miu.onlinemarket.domain.State;
import com.miu.onlinemarket.service.AddressService;

@Service
public class AddressServiceImp implements AddressService {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Country> loadCountries() throws Exception {
		Object obj = new JSONParser().parse(new FileReader("countries.json"));
		JSONObject jo = (JSONObject) obj;
		JSONArray ja = (JSONArray) jo.get("countries");
		Iterator<?> itr2 = ja.iterator();
		List<Country> countries = new ArrayList<>();
		while (itr2.hasNext()) {
			Iterator<Map.Entry> itr1 = ((Map) itr2.next()).entrySet().iterator();
			Country country = new Country();
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				if (pair.getKey().toString().equalsIgnoreCase("id"))
					country.setId(Long.parseLong(pair.getValue().toString()));
				else if (pair.getKey().toString().equalsIgnoreCase("sortname"))
					country.setSortname(pair.getValue().toString());
				else if (pair.getKey().toString().equalsIgnoreCase("name"))
					country.setName(pair.getValue().toString());
				else if (pair.getKey().toString().equalsIgnoreCase("phoneCode"))
					country.setPhoneCode(Long.parseLong(pair.getValue().toString()));
			}
			countries.add(country);
		}
		return countries;
//		System.out.print(countries);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<State> loadStates(long id) throws Exception {
		Object obj = new JSONParser().parse(new FileReader("states.json"));
		JSONObject jo = (JSONObject) obj;
		JSONArray ja = (JSONArray) jo.get("states");
		Iterator<?> itr2 = ja.iterator();
		Map<Long, List<State>> states = new HashMap<>();
		while (itr2.hasNext()) {
			Iterator<Map.Entry> itr1 = ((Map) itr2.next()).entrySet().iterator();
			State state = new State();
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				if (pair.getKey().toString().equalsIgnoreCase("id"))
					state.setId(Long.parseLong(pair.getValue().toString()));
				else if (pair.getKey().toString().equalsIgnoreCase("name"))
					state.setName(pair.getValue().toString());
				else if (pair.getKey().toString().equalsIgnoreCase("country_id"))
					state.setCountry_id(Long.parseLong(pair.getValue().toString()));
			}
			if (states.containsKey(state.getCountry_id())) {
				states.get(state.getCountry_id()).add(state);
			} else {
				List<State> stateList = new ArrayList<>();
				stateList.add(state);
				states.put(state.getCountry_id(), stateList);
			}
		}
		return states.get(id);
//		System.out.print(states);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<City> loadCities(long id) throws Exception {
		Object obj = new JSONParser().parse(new FileReader("cities.json"));
		JSONObject jo = (JSONObject) obj;
		JSONArray ja = (JSONArray) jo.get("cities");
		Iterator<?> itr2 = ja.iterator();
		Map<Long, List<City>> cities = new HashMap<>();
		while (itr2.hasNext()) {
			Iterator<Map.Entry> itr1 = ((Map) itr2.next()).entrySet().iterator();
			City city = new City();
			while (itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				if (pair.getKey().toString().equalsIgnoreCase("id"))
					city.setId(Long.parseLong(pair.getValue().toString()));
				else if (pair.getKey().toString().equalsIgnoreCase("name"))
					city.setName(pair.getValue().toString());
				else if (pair.getKey().toString().equalsIgnoreCase("state_id"))
					city.setState_id(Long.parseLong(pair.getValue().toString()));
			}
			if (cities.containsKey(city.getState_id())) {
				cities.get(city.getState_id()).add(city);
			} else {
				List<City> cityList = new ArrayList<>();
				cityList.add(city);
				cities.put(city.getState_id(), cityList);
			}
		}
		return cities.get(id);
//		System.out.print(cities);
	}
	
}
