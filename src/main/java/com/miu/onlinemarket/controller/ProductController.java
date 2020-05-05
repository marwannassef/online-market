package com.miu.onlinemarket.controller;

import com.miu.onlinemarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/detail")
    public String displayProductDetails(@RequestParam("id") long id, Model model){

   model.addAttribute("product",productRepository.findById(id).orElse(null));
        return "productDetails";
    }
}
