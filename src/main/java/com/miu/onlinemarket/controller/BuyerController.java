package com.miu.onlinemarket.controller;

import com.miu.onlinemarket.domain.*;
import com.miu.onlinemarket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

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

    @GetMapping("/addAddress")
    public String addAddress(@ModelAttribute("address") Address address, Model model) {
        model.addAttribute("address", address);
        return "address";
    }
    @PostMapping({"/addAddress"})
    public String addAddress(@Valid @ModelAttribute("address") Address address, BindingResult bindingResult, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        buyerService.updateAddress(userId ,address);
        System.out.println(address.getCity());
        return "redirect:/home";
    }
    @GetMapping("/addPayment")
    public String addPayment(@ModelAttribute("paymentMethod") PaymentMethod paymentMethod) {
        return "paymentMethod";
    }
    @PostMapping("/addPayment")
    public  String addPayment(@Valid @ModelAttribute("paymentMethod") PaymentMethod paymentMethod, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        buyerService.updatePayment(userId, paymentMethod );

        return "redirect:/home";
    }

    @PreAuthorize("hasRole('ROLE_BUYER')")
    @GetMapping({"/addItem"})
    public String addItem(@RequestParam("id")Long id, Model model,HttpSession session) {


        Product product = (Product)productService.findById(id).orElse(new Product());
        product.setQuantity(product.getQuantity()-1);
        productService.save(product);

        // create new item
        Item item = new Item(product,1,"prepared");
        itemService.save(item);

        // add item to order
        Order order;
        try {
             order = (Order) orderService.findById((Long) session.getAttribute("orderId"))
                    .orElse(new Order());
        } catch (Exception e){
            order = new Order();
        }
        order.getItems().add(item);
        long total = order.getTotalPrice() + product.getPrice();
        order.setTotalPrice(total);
        orderService.save(order);
        session.setAttribute("orderId",order.getId());


        // add order to user
        Buyer buyer = (Buyer) session.getAttribute("buyer");

        try{
            buyer.getOrders().add(order);
        } catch (Exception e){
            buyer.setOrders(new HashSet<>());
            buyer.getOrders().add(order);
        }
        buyerService.save(buyer);


        // add item to seller for shipping
        Seller seller = product.getSeller();

        try {seller.getItems().add(item);
        } catch (Exception e){
            seller.setItems(new ArrayList<>());
        }
        sellerService.save(seller);



        return "redirect:/home";

    }

    @GetMapping("/order")
    public String displayOrder(Model model,HttpSession session){
        Buyer buyer = (Buyer)session.getAttribute("buyer");
        Set<Order> orders = buyer.getOrders();
        model.addAttribute("orders", orders);
        System.out.println("before page");
        return "order";
    }

}
