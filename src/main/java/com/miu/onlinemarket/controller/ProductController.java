package com.miu.onlinemarket.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.miu.onlinemarket.domain.Item;
import com.miu.onlinemarket.domain.Product;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.exceptionhandling.ResourceNotFoundException;
import com.miu.onlinemarket.service.ProductService;
import com.miu.onlinemarket.service.SellerService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private SellerService sellerService;

	@GetMapping("/detail")
	public String displayProductDetails(@RequestParam("id") long id, Model model) throws ResourceNotFoundException {
		Product product = productService.findById(id);
		boolean status = product.isPurchasedStatus();
		model.addAttribute("status", status);
		model.addAttribute("product", product);
		product.getReviews().forEach(review -> {
	    	if (review.getBuyer().getPhoto() != null && review.getBuyer().getPhoto().length != 0)
	    		review.getBuyer().setPhotoBase64(Base64.getEncoder().encodeToString(review.getBuyer().getPhoto()));
		});
		model.addAttribute("reviews", product.getReviews());
		model.addAttribute("itm", new Product());
		return "productDetails";
	}

	@GetMapping("/addProduct")
	public String addProduct(Model model, Principal principal) throws ResourceNotFoundException {
		model.addAttribute("product", new Product());
		Seller seller = sellerService.findSeller(principal.getName());
		boolean approved = seller.getApproved();
		model.addAttribute("approved", approved);
		return "addProduct";
	}

	@RequestMapping(value = "/addProductProcess", method = RequestMethod.POST)
	public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
			Principal principal, Model model) throws ResourceNotFoundException, IOException {
		if (bindingResult.hasErrors()) {
			Seller seller = sellerService.findSeller(principal.getName());
			boolean approved = seller.getApproved();
			model.addAttribute("approved", approved);
			System.out.println(bindingResult);
			return "addProduct";
		}

		MultipartFile image = product.getImage();
		if (image != null && !image.isEmpty()) {
			try {
				product.setPhoto(image.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			String fileName = "static/img/product.png";
			ClassLoader classLoader = new UserController().getClass().getClassLoader();
			File file = new File(classLoader.getResource(fileName).getFile());
			BufferedImage originalImage = ImageIO.read(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "png", baos);
			baos.flush();
			product.setPhoto(baos.toByteArray());
			baos.close();
		}

		Seller seller = sellerService.findSeller(principal.getName());
		product.setSeller(seller);
		productService.save(product);

		return "redirect:/home";
	}

	@GetMapping("/removeProduct")
	public String removeProduct(@RequestParam("id") Long id) throws ResourceNotFoundException {

		Product product = productService.findById(id);
		productService.delete(product);

		return "redirect:/home";
	}

	@GetMapping("/updateProduct")
	public String updateProduct(@RequestParam("id") Long id, Model model) throws ResourceNotFoundException {

		Product product = productService.findById(id);
		model.addAttribute("updateProduct", product);

		return "update-product";
	}
	
	@RequestMapping(value = "/updateProductProcess", method = RequestMethod.POST)
	public String updateProductProcess(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult,
			@RequestParam("id") Long id, Principal principal, Model model) throws ResourceNotFoundException {
		if (bindingResult.hasErrors()) {
			model.addAttribute("updateProduct", product);
			return "update-product";
		}
		MultipartFile image = product.getImage();
		if (image != null && !image.isEmpty()) {
			try {
				product.setPhoto(image.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Seller seller = sellerService.findSeller(principal.getName());
		product.setSeller(seller);
		productService.update(product, id);
		return "redirect:/home";
	}

}
