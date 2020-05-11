package com.miu.onlinemarket.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Item;
import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.Status;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import com.miu.onlinemarket.service.BuyerService;
import com.miu.onlinemarket.service.ItemService;
import com.miu.onlinemarket.service.OrderService;
import com.miu.onlinemarket.service.ProductService;
import com.miu.onlinemarket.service.SellerService;

@Controller
public class OrderController {

	@Autowired
	SellerService sellerService;
	@Autowired
	private BuyerService buyerService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ItemService itemService;

	@GetMapping({ "/addToCart" })
	public String addItem(@RequestParam("id") Long id, Model model, HttpSession session, Principal principal) throws ResourceNotFoundException {
		Product product = (Product) productService.findById(id);
		product.setQuantity(product.getQuantity() - 1);
		productService.save(product);

		Item item = new Item(product, 1, Status.PREPARED);
		itemService.save(item);

		Order order;
		try {
			order = (Order) orderService.findById((Long) session.getAttribute("orderId")).orElse(new Order());
		} catch (Exception e) {
			order = new Order();
		}
		order.getItems().add(item);
		double total = order.getTotalPrice() + product.getPrice();
		order.setTotalPrice(total);
		orderService.save(order);
		session.setAttribute("orderId", order.getId());

		Buyer buyer = buyerService.findByUsername(principal.getName());
		try {
			buyer.getOrders().add(order);
		} catch (Exception e) {
			buyer.setOrders(new HashSet<>());
			buyer.getOrders().add(order);
		}
		buyerService.save(buyer);

		Seller seller = product.getSeller();
		try {
			seller.getItems().add(item);
		} catch (Exception e) {
			seller.setItems(new HashSet<>());
		}
		sellerService.save(seller);
		return "redirect:/home";
	}

	@GetMapping("/cart")
	public String displayOrder(Model model, HttpSession session) {
		Buyer buyer = (Buyer) session.getAttribute("buyer");
		Optional<Order> order = buyer.getOrders().stream().filter(ord -> ord.getStatus() == Status.PREPARED)
				.findFirst();
		model.addAttribute("order", order);
		return "cart";
	}

	@GetMapping("/removeItem")
	public String removeItem(@RequestParam("id") Long id, HttpSession session) throws ResourceNotFoundException {
		Buyer buyer = (Buyer) session.getAttribute("buyer");
		Order order = (Order) orderService.findById((Long) session.getAttribute("orderId")).orElse(new Order());
		Item item = itemService.findItem(id);
		Seller seller = item.getProduct().getSeller();
		Product product = item.getProduct();

		order.getItems().remove(item);
		seller.getItems().remove(item);
		if (order.getItems().size() == 0) {
			buyer.getOrders().remove(order);
			buyerService.save(buyer);
		}
		double total = order.getTotalPrice() - item.getQuantity() * item.getProduct().getPrice();
		order.setTotalPrice(total);
		product.setQuantity(product.getQuantity() + 1);
		itemService.delete(item);
		orderService.save(order);
		sellerService.save(seller);
		productService.save(product);
		session.setAttribute("buyer", buyerService.findByUsername(buyer.getUsername()));
		session.setAttribute("orderId", id);

		return "redirect:/cart";
	}

	@GetMapping("/orders")
	public String displayOrder(Model model, Principal principal) throws ResourceNotFoundException {
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Set<Order> orders = buyer.getOrders();
		model.addAttribute("orders", orders);
		return "order";
	}

}
