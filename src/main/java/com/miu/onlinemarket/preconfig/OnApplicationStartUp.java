package com.miu.onlinemarket.preconfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.miu.onlinemarket.domain.Role;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.repository.RoleRepository;
import com.miu.onlinemarket.repository.UserRepository;
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

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
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

    private void createAdminUser() {
        User user = new User();
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setEmail("admin@miu.edu");
        user.setPhoneNumber("6418192921");
        user.setDateOfBirth(LocalDate.parse("1990-03-22"));
        user.setUsername("admin");
        user.setPassword("admin");
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName("ROLE_ADMIN"));
        user.setRoles(roles);
        userService.save(user);
    }

    private void createSeller() {
        User user = new User();
        user.setFirstName("seller");
        user.setLastName("seller");
        user.setEmail("seller@miu.edu");
        user.setPhoneNumber("6418192921");
        user.setDateOfBirth(LocalDate.parse("1990-03-22"));
        user.setUsername("seller");
        user.setPassword("seller");
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName("ROLE_SELLER"));
        user.setRoles(roles);
        userService.save(user);
    }

    private void createBuyer() {
        User user = new User();
        user.setFirstName("buyer");
        user.setLastName("buyer");
        user.setEmail("buyer@miu.edu");
        user.setPhoneNumber("6418192921");
        user.setDateOfBirth(LocalDate.parse("1990-03-22"));
        user.setUsername("buyer");
        user.setPassword("buyer");
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepo.findByName("ROLE_BUYER"));
        user.setRoles(roles);
        userService.save(user);
    }

    private void createProduct() {
        Product product = new Product();
        product.setName("Mobile");
        productRepository.save(product);
        Product product2 = new Product();
        product2.setName("TV");
        productRepository.save(product2);
    }

}
