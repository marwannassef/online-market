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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Item;
import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.Product;
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
	public String addToCart(@RequestParam("id") Long id, Model model, HttpSession session, Principal principal,
			RedirectAttributes redirectAttributes) throws ResourceNotFoundException {
		Product product = (Product) productService.findById(id);
		product.setQuantity(product.getQuantity() - 1);
		productService.save(product);
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Optional<Order> order = buyer.getOrders().stream().filter(ord -> ord.getStatus() == Status.PREPARED)
				.findFirst();
		Optional<Item> tempItem = order.orElse(new Order()).getItems().stream()
				.filter(itm -> itm.getProduct().getId() == id).findFirst();
		if (!tempItem.isPresent()) {
			Item item = new Item(product, 1, Status.PREPARED, product.getSeller(), order.orElse(new Order()));
			itemService.save(item);
			session.setAttribute("cartCount", order.orElse(new Order()).getItems().size() + 1);
		} else {
			tempItem.get().setQuantity(tempItem.get().getQuantity() + 1);
			itemService.save(tempItem.get());
		}
		redirectAttributes.addFlashAttribute("status", "success");
		return "redirect:/home";
	}

	@GetMapping("/cart")
	public String displayCart(Model model, Principal principal) throws ResourceNotFoundException {
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Optional<Order> order = buyer.getOrders().stream().filter(ord -> ord.getStatus() == Status.PREPARED)
				.findFirst();
		model.addAttribute("order", order.orElse(new Order()));
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
	public String placeOrder(Principal principal) throws ResourceNotFoundException {
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Optional<Order> order = buyer.getOrders().stream().filter(ord -> ord.getStatus() == Status.PREPARED)
				.findFirst();
		order.orElse(new Order()).setStatus(Status.PAYMENT_CONFIRMED);
		Order newOrder = new Order(0, Status.PREPARED, new HashSet<Item>());
		buyerService.updateUserOrder(newOrder, principal.getName());
		orderService.save(order.orElse(new Order()));
		return "redirect:/home";
	}

	@GetMapping("/orders")
	public String displayOrder(Model model, Principal principal) throws ResourceNotFoundException {
		Buyer buyer = buyerService.findByUsername(principal.getName());
		Set<Order> orders = buyer.getOrders();
		model.addAttribute("orders", orders);
		return "orders";
	}

}
