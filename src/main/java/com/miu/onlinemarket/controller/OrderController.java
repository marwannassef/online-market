package com.miu.onlinemarket.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Checkout;
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
import com.miu.onlinemarket.service.UserService;

@Controller
public class OrderController {

	@Autowired
	SellerService sellerService;
	@Autowired
	private BuyerService buyerService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ItemService itemService;

	@GetMapping({ "/addToCart" })
	public String addToCart(@RequestParam("id") Long id, Model model, HttpSession session, Principal principal,
			RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
		Product product = (Product) productService.findById(id);
		product.setQuantity(product.getQuantity() - 1);
		productService.save(product);
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Optional<Order> order = buyer.getOrders().stream().filter(ord -> ord.getStatus() == Status.PREPARED)
				.findFirst();
		Order orderItem = order.orElse(new Order());
		orderItem.setTotalPrice(orderItem.getTotalPrice() + product.getPrice());
		orderService.save(orderItem);
		Optional<Item> tempItem = order.orElse(new Order()).getItems().stream()
				.filter(itm -> itm.getProduct().getId() == id).findFirst();
		if (!tempItem.isPresent()) {
			Item item = new Item(product, 1, Status.PREPARED, product.getSeller(), order.orElse(new Order()), buyer);
			itemService.save(item);
			session.setAttribute("cartCount", order.orElse(new Order()).getItems().size() + 1);
		} else {
			tempItem.get().setQuantity(tempItem.get().getQuantity() + 1);
			itemService.save(tempItem.get());
		}
		redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/home";
	}

	@PostMapping({ "/updateQuantity" })
	public String updateQuantity(@ModelAttribute Product prod, Model model, HttpSession session, Principal principal,
			RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
		Product product = (Product) productService.findById(prod.getId());
		product.setQuantity(product.getQuantity() - prod.getQuantity());
		productService.save(product);
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Optional<Order> order = buyer.getOrders().stream().filter(ord -> ord.getStatus() == Status.PREPARED)
				.findFirst();
		Order orderItem = order.orElse(new Order());
		orderItem.setTotalPrice(orderItem.getTotalPrice() + (product.getPrice() * prod.getQuantity()));
		orderService.save(orderItem);
		Optional<Item> tempItem = order.orElse(new Order()).getItems().stream()
				.filter(itm -> itm.getProduct().getId() == prod.getId()).findFirst();
		if (!tempItem.isPresent()) {
			Item item = new Item(product, prod.getQuantity(), Status.PREPARED, product.getSeller(), order.orElse(new Order()), buyer);
			itemService.save(item);
			session.setAttribute("cartCount", order.orElse(new Order()).getItems().size() + 1);
		} else {
			tempItem.get().setQuantity(tempItem.get().getQuantity() + prod.getQuantity());
			itemService.save(tempItem.get());
		}
		redirectAttributes.addFlashAttribute("status", "success");
		if (prod.getDescription() != null && prod.getDescription().equalsIgnoreCase("details"))
			return "redirect:/product/detail?id=" + prod.getId();
		return "redirect:/cart";
	}

	@GetMapping("/cart")
	public String displayCart(Model model, Principal principal) throws ResourceNotFoundException {
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Optional<Order> order = buyer.getOrders().stream().filter(ord -> ord.getStatus() == Status.PREPARED)
				.findFirst();
		model.addAttribute("checkoutEnable", true);
		if (buyer.getAddress() == null || buyer.getPaymentMethod() == null) {
			model.addAttribute("checkoutEnable", false);
		}
		model.addAttribute("order", order.orElse(new Order()));
		model.addAttribute("itm", new Product());
		return "cart";
	}

	@GetMapping("/removeCartItem")
	public String removeItem(@RequestParam("id") Long id, HttpSession session) throws ResourceNotFoundException {
		Item item = itemService.findItem(id);
		Product product = (Product) productService.findById(item.getProduct().getId());
		product.setQuantity(product.getQuantity() + item.getQuantity());
		productService.save(product);
		itemService.delete(id);
		session.setAttribute("cartCount", Integer.parseInt(session.getAttribute("cartCount").toString()) - 1);
		return "redirect:/cart";
	}

	@GetMapping("/placeOrder")
	public String placeOrder(@ModelAttribute Checkout checkout, Principal principal) throws ResourceNotFoundException {
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Optional<Order> order = buyer.getOrders().stream().filter(ord -> ord.getStatus() == Status.PREPARED)
				.findFirst();
		order.orElse(new Order()).setStatus(Status.PAYMENT_CONFIRMED);
		order.orElse(new Order()).setOrderNumber("INVOICE#" + order.orElse(new Order()).getId());;
		Order newOrder = new Order(0, Status.PREPARED, new HashSet<Item>());
		buyerService.updateUserOrder(newOrder, principal.getName());
		if (checkout.isChecked()) {
			double requiredPoints = order.orElse(new Order()).getTotalPrice() * 100;
			double actualPoints = buyer.getPoints() / 100;
			if (buyer.getPoints() == requiredPoints) {
				buyer.setPoints(0);
				buyerService.update(buyer);
				order.orElse(new Order()).setTotalPrice(0);
			} else if (buyer.getPoints() < requiredPoints) {
				buyer.setPoints(0);
				buyerService.update(buyer);
				order.orElse(new Order()).setTotalPrice(order.orElse(new Order()).getTotalPrice() - actualPoints);
			} else {
				buyer.setPoints((int)(buyer.getPoints() - requiredPoints));
				buyerService.update(buyer);
				order.orElse(new Order()).setTotalPrice(0);
			}
		}
		orderService.save(order.orElse(new Order()));
		return "redirect:/home";
	}


	@GetMapping("/orders")
	public String displayOrder(Model model, Principal principal) throws ResourceNotFoundException {
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Set<Order> ord = buyer.getOrders();
		List<Order> orders = new ArrayList<>();
		for (Order order: ord) {
			if(order.getStatus() != Status.PREPARED)
				orders.add(order);
		}
		model.addAttribute("orders", orders);
		return "orders";
	}


	@GetMapping("/items")
	public String displayItems(@RequestParam("id") Long id, Model model, Principal principal) throws ResourceNotFoundException {

		Order order = orderService.findById(id).orElse(new Order());
		model.addAttribute("order", order);

		return "items";
	}

	@GetMapping("/selledItems")
	public String selledItems(Principal principal,Model model) throws ResourceNotFoundException{
		Seller seller = sellerService.findSeller(principal.getName());
		Set<Item> items =  seller.getItems();
		List<Item> items1 = new ArrayList<>();
		for(Item item: items) {
			items1.add(item);
		}
		model.addAttribute("items", items1);
		return "items";
	}
	@GetMapping("/changeStatus")
	public String changeStatus(@RequestParam("id") Long id,@RequestParam("status") Status status, Model model, Principal principal) throws ResourceNotFoundException {


		Item item = itemService.findItem(id);
		item.setStatus(status);
		if(status == Status.CANCELLED)
		{
			Order order = item.getOrder();
			order.setTotalPrice(order.getTotalPrice()-(item.getQuantity()*item.getProduct().getPrice()));
			orderService.save(order);

			Product product = item.getProduct();
			product.setQuantity(product.getQuantity() + item.getQuantity());
			productService.save(product);
		}
		if(status == Status.DELIVERED) {
			Buyer buyer = item.getBuyer();
			int points = (int)(buyer.getPoints() + (item.getQuantity() * item.getProduct().getPrice() * 0.1));
			buyer.setPoints(points);
			buyerService.update(buyer);
		}
		itemService.save(item);
		return "redirect:/selledItems";
	}
	@GetMapping("/itemStatus")
	public String itemStatus(@RequestParam("id") Long id, Model model) throws ResourceNotFoundException {
		Item item = itemService.findItem(id);
		model.addAttribute("item",item);
		return "itemStatus";
	}
	@GetMapping("/removeOrderItem")
	public String removeOrderItem(@RequestParam("id") Long id,Model model, HttpSession httpSession) throws ResourceNotFoundException {
		Item item = itemService.findItem(id);
		Order order1 = item.getOrder();
		order1.getItems().remove(item);
		order1.setTotalPrice(order1.getTotalPrice() -(item.getQuantity() * item.getProduct().getPrice()));

		Product product = (Product) productService.findById(item.getProduct().getId());
		product.setQuantity(product.getQuantity() + item.getQuantity());
		productService.save(product);
		itemService.delete(id);
		orderService.save(order1);
		Order order = orderService.findById(order1.getId()).orElse(new Order());
		httpSession.setAttribute("orderId", order.getId());

		return "redirect:/displayItems";
	}
	@GetMapping("/displayItems")
	public String displayItems(HttpSession httpSession, Model model) {
		Long id = (Long) httpSession.getAttribute("orderId");
		System.out.println(id);
		Order order = orderService.findById(id).orElse(new Order());
		model.addAttribute("order", order);
		return "items";
	}

}
