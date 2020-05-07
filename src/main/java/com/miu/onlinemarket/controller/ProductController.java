package com.miu.onlinemarket.controller;

import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Seller;

import com.miu.onlinemarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Product product = productService.findById(id).orElse(null);
        boolean status = product.isPurchasedStatus();
        model.addAttribute("status",status);
         model.addAttribute("product",product);
        return "productDetails";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model,HttpSession session) {
        model.addAttribute("product", new Product());
        Seller seller = (Seller)session.getAttribute("seller");
        boolean approved = seller.getApproved();
        model.addAttribute("approved",approved);
        return "addProduct";
    }


    @RequestMapping(value = "/addProductProcess", method = RequestMethod.POST)
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
                              HttpSession session){
        if(bindingResult.hasErrors()) {
            return "redirect:/product/addProduct";
        }

        Seller seller =(Seller) session.getAttribute("seller");
        product.setSeller(seller);
        productService.save(product);

        return "redirect:/home";
    }

    @GetMapping("/removeProduct")
    public String removeProduct(@RequestParam("id") Long id){

        Product product =productService.findById(id).orElse(null);
        productService.delete(product);

        return "redirect:/home";
    }

    @GetMapping("/updateProduct")
    public String updateProduct(@RequestParam("id") Long id,Model model){

        Product product =productService.findById(id).orElse(null);
        model.addAttribute("updateProduct",product);

        return "update-product";
    }

    @RequestMapping(value = "/updateProductProcess", method = RequestMethod.POST)
    public String updateProductProcess(@Valid @ModelAttribute("product") Product product,@RequestParam("id") Long id, BindingResult bindingResult,
                             HttpSession session){

        if(bindingResult.hasErrors()) {
            return "redirect:/product/updateProduct";
        }
        Seller seller =(Seller) session.getAttribute("seller");
        product.setSeller(seller);
        productService.update(product,id);
        return "redirect:/home";
    }

}
