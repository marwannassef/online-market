package com.miu.onlinemarket.preconfig;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import com.miu.onlinemarket.domain.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.miu.onlinemarket.controller.UserController;
import com.miu.onlinemarket.repository.ProductRepository;
import com.miu.onlinemarket.repository.RoleRepository;
import com.miu.onlinemarket.repository.UserRepository;
import com.miu.onlinemarket.service.BuyerService;
import com.miu.onlinemarket.service.ReviewService;
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

	private void createAdminUser() throws ParseException, IOException {
		User user = new User();
		user.setFirstName("admin");
		user.setLastName("admin");
		user.setEmail("admin@miu.edu");
		user.setPhoneNumber("6418192921");
		user.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse("03/22/1990"));
		user.setUsername("admin");
		user.setPassword("admin");
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepo.findByName("ROLE_ADMIN"));
		user.setRoles(roles);
		user.setPhoto(userPhoto());
		userService.save(user);
	}

	private void createSeller() throws ParseException, IOException {
		Seller user = new Seller();
		user.setFirstName("seller");
		user.setLastName("seller");
		user.setEmail("seller@miu.edu");
		user.setPhoneNumber("6418192921");
		user.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse("03/22/1990"));
		user.setUsername("seller");
		user.setPassword("seller");
		user.setApproved(true);
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepo.findByName("ROLE_SELLER"));
		user.setRoles(roles);
		user.setPhoto(userPhoto());
		sellerService.save(user);

		Product product4 = new Product();
		product4.setName("Pepsi");
		product4.setSeller(user);
		product4.setDescription("Best Pepsi");
		product4.setQuantity(new Long(2));
		product4.setPrice(new Double(90));
		product4.setPhoto(productPhoto());
		productRepository.save(product4);

		Product product5 = new Product();
		product5.setName("Instant Pot");
		product5.setSeller(user);
		product5.setDescription("great healthy one-pot meals");
		product5.setQuantity(new Long(2));
		product5.setPrice(new Double(120));
		product5.setPhoto(productPhoto());
		productRepository.save(product5);
	}

	private void createBuyer() throws ParseException, IOException {
		Buyer user = new Buyer();
		user.setFirstName("buyer");
		user.setLastName("buyer");
		user.setEmail("buyer@miu.edu");
		user.setPhoneNumber("6418192921");
		user.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse("03/22/1990"));
		user.setUsername("buyer");
		user.setPassword("buyer");
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepo.findByName("ROLE_BUYER"));
		user.setRoles(roles);
//		Order order = new Order(12.0,Status.PAYMENT_CONFIRMED);
//		user.addOder(order);
		LocalDate date = LocalDate.of(2020,9,15);
		PaymentMethod paymentMethod= new PaymentMethod("2093",date,"263","ahmed");
		Address address = new Address("1000 Nth",44506,3936,"52557",231);
		user.setPaymentMethod(paymentMethod);
		user.setAddress(address);
		user.setPhoto(userPhoto());
		buyerService.save(user);
		Review review = new Review();
		review.setReview("nice one");
		review.setBuyer(user);
		reviewService.save(review);
	}
	private void createProduct() throws ParseException, IOException {
		Seller user2 = new Seller();
		user2.setFirstName("bassem");
		user2.setLastName("elsawy");
		user2.setEmail("seller@miu.edu");
		user2.setPhoneNumber("6418192921");
		user2.setDateOfBirth(new SimpleDateFormat("MM/dd/yyyy").parse("03/22/1990"));
		user2.setUsername("bassem");
		user2.setPassword("bassem");
		Set<Role> roles = new HashSet<>();
		user2.setApproved(true);
		roles.add(roleRepo.findByName("ROLE_SELLER"));
		user2.setRoles(roles);
		user2.setPhoto(userPhoto());
		sellerRepository.save(user2);


		Product product = new Product();
		product.setName("Mobile");
		product.setSeller(user2);
		product.setDescription("Best Mobile");
		product.setQuantity(new Long(9));
		product.setPrice(new Double(50));
		product.setPurchasedStatus(true);
		product.setPhoto(productPhoto());
		productRepository.save(product);

		Product product2 = new Product();
		product2.setName("Cups");
		product2.setSeller(user2);
		product2.setDescription("Best Cups");
		product2.setQuantity(new Long(9));
		product2.setPrice(new Double(40));
		product2.setPhoto(productPhoto());
		productRepository.save(product2);

		Product product3 = new Product();
		product3.setName("TV");
		product3.setSeller(user2);
		product3.setDescription("Best TV");
		product3.setQuantity(new Long(9));
		product3.setPrice(new Double(70));
		product3.setPhoto(productPhoto());
		productRepository.save(product3);



	}
	
	private byte[] userPhoto () throws IOException {
		String fileName = "static/img/user.png";
		ClassLoader classLoader = new UserController().getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		BufferedImage originalImage = ImageIO.read(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "png", baos);
		baos.flush();
		baos.close();
		return baos.toByteArray();
	}
	
	private byte[] productPhoto () throws IOException {
		String fileName = "static/img/product.png";
		ClassLoader classLoader = new UserController().getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());
		BufferedImage originalImage = ImageIO.read(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "png", baos);
		baos.flush();
		baos.close();
		return baos.toByteArray();
	}

}
