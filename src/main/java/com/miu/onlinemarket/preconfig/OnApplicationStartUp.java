package com.miu.onlinemarket.preconfig;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.miu.onlinemarket.domain.*;
import com.miu.onlinemarket.service.BuyerService;
import com.miu.onlinemarket.service.ReviewService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.miu.onlinemarket.repository.ProductRepository;
import com.miu.onlinemarket.repository.RoleRepository;
import com.miu.onlinemarket.repository.UserRepository;
import com.miu.onlinemarket.service.SellerService;
import com.miu.onlinemarket.service.UserService;

@Component
public class OnApplicationStartUp {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private SellerService sellerRepository;
	@Autowired
	private BuyerService buyerService;

	@Autowired
	private SellerService sellerService;

	@Autowired
	private ReviewService reviewService;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) throws Exception {
		List<User> users = userRepo.findAll();
		Long count = users.stream().filter(userElm -> userElm.getUsername().equalsIgnoreCase("admin")).count();
		if (count > 0)
			return;
		fillRoleTable();
		createAdminUser();
		createSeller();
		createBuyer();
		loadCountries();
		loadStates();
		loadCities();
		createProduct();

	}

	private void fillRoleTable() {
		List<Role> roles = roleRepo.findAll();
		if (roles == null || roles.isEmpty()) {
			roleRepo.save(new Role("ROLE_ADMIN"));
			roleRepo.save(new Role("ROLE_SELLER"));
			roleRepo.save(new Role("ROLE_BUYER"));
		}
	}

	private void createAdminUser() throws ParseException {
		User user = new User();
		user.setFirstName("admin");
		user.setLastName("admin");
		user.setEmail("admin@miu.edu");
		user.setPhoneNumber("6418192921");
		user.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse("03/22/1990"));
		user.setUsername("admin");
		user.setPassword("admin");
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findByName("ROLE_ADMIN"));
		user.setRoles(roles);
		userService.save(user);
	}

	private void createSeller() throws ParseException {
		Seller user = new Seller();
		user.setFirstName("seller");
		user.setLastName("seller");
		user.setEmail("seller@miu.edu");
		user.setPhoneNumber("6418192921");
		user.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse("03/22/1990"));
		user.setUsername("seller");
		user.setPassword("seller");
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findByName("ROLE_SELLER"));
		user.setRoles(roles);
		sellerService.save(user);

		Product product4 = new Product();
		product4.setName("Pepsi");
		product4.setSeller(user);
		product4.setDescription("Best Pepsi");
		product4.setQuantity(2);
		product4.setPrice(9);
		productRepository.save(product4);
	}

	private void createBuyer() throws ParseException {
		Buyer user = new Buyer();
		user.setFirstName("buyer");
		user.setLastName("buyer");
		user.setEmail("buyer@miu.edu");
		user.setPhoneNumber("6418192921");
		user.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse("03/22/1990"));
		user.setUsername("buyer");
		user.setPassword("buyer");
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findByName("ROLE_BUYER"));
		user.setRoles(roles);
		user.setOrders(null);
		user.setPaymentMethod(null);
		user.setShippingAddress(null);
		buyerService.save(user);
		Review review = new Review();
		review.setReview("nice one");
		review.setBuyer(user);
		reviewService.save(review);
	}
	private void createProduct() throws ParseException {
		Seller user2 = new Seller();
		user2.setFirstName("bassem");
		user2.setLastName("elsawy");
		user2.setEmail("seller@miu.edu");
		user2.setPhoneNumber("6418192921");
		user2.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse("03/22/1990"));
		user2.setUsername("bassem");
		user2.setPassword("bassem");
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepo.findByName("ROLE_SELLER"));
		user2.setRoles(roles);
		sellerRepository.save(user2);


		Product product = new Product();
		product.setName("Mobile");
		product.setSeller(user2);
		product.setDescription("Best Mobile");
		product.setQuantity(10);
		product.setPrice(10);
		productRepository.save(product);

		Product product2 = new Product();
		product2.setName("Cups");
		product2.setSeller(user2);
		product2.setDescription("Best Cups");
		product2.setQuantity(0);
		product2.setPrice(30);
		productRepository.save(product2);

		Product product3 = new Product();
		product3.setName("TV");
		product3.setSeller(user2);
		product3.setDescription("Best TV");
		product3.setQuantity(5);
		product3.setPrice(30);
		productRepository.save(product3);


	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadCountries() throws Exception {
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
//		System.out.print(countries);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadStates() throws Exception {
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
//		System.out.print(states);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadCities() throws Exception {
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
//		System.out.print(cities);
	}

}
