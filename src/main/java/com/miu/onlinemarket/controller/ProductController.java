package com.miu.onlinemarket.controller;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.repository.ProductRepository;
import com.miu.onlinemarket.service.ProductService;
import com.miu.onlinemarket.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/detail")
    public String displayProductDetails(@RequestParam("id") long id, Model model){

   model.addAttribute("product",productService.findById(id).orElse(null));
        return "productDetails";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    @RequestMapping(value = "/addProductProcess", method = RequestMethod.POST)
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
            HttpSession session){

        if(bindingResult.hasErrors()) {
            return "addProduct";
        }

        Seller seller =(Seller) session.getAttribute("seller");
        product.setSeller(seller);
        productService.save(product);

        return "redirect:/home";
    }
}
