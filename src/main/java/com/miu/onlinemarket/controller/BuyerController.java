package com.miu.onlinemarket.controller;

import com.miu.onlinemarket.domain.*;
import com.miu.onlinemarket.service.BuyerService;
import com.miu.onlinemarket.service.OrderService;
import com.miu.onlinemarket.service.ProductService;
import com.miu.onlinemarket.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller

public class BuyerController {

    @Autowired
    SellerService sellerService;
    @Autowired
    private BuyerService buyerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

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
    @GetMapping({"/addProduct"})
    public String addProduct(@RequestParam("productID")Long productId, Model model,HttpSession session) {

        Product product = (Product)productService.findById(productId).orElse(new Product());
        product.setQuantity(product.getQuantity()-1);
        productService.save(product);
        // create new item
        Item item = new Item(product,1,"prepared");

        // add item to order
        Order order = new Order();
        try {
             Long orderId = (Long) session.getAttribute("orderId");
             order = orderService.findById(orderId);
             order.getItems().add(item);
             order.setTotalPrice(order.getTotalPrice()+ product.getPrice());
             orderService.save(order);

        } catch (Exception e){
            order.getItems().add(item);
            order.setTotalPrice(order.getTotalPrice()+ product.getPrice());
            orderService.save(order);
            session.setAttribute("orderId",order.getId());
        }
        // add order to user
        Long userId = (Long) session.getAttribute("userId");
        Buyer buyer = buyerService.findBuyerById(userId);
        buyer.getOrders().add(order);
        buyerService.save(buyer);

        // add item to seller for shipping
        Seller seller = product.getSeller();
        seller.getItems().add(item);
        sellerService.save(seller);


        return "redirect:/home";

    }

}
