package com.miu.onlinemarket.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Seller;
import com.miu.onlinemarket.domain.User;
import com.miu.onlinemarket.service.BuyerService;
import com.miu.onlinemarket.service.SellerService;
import com.miu.onlinemarket.service.UserService;

@Controller
@SessionAttributes("type")
public class UserController {

	@Autowired
	SellerService sellerService;

	@Autowired
	BuyerService buyerService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/login", "/" })
	public String login(SessionStatus status) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
			return "redirect:/home";
		}
		status.setComplete();
		return "login";
	}

	@RequestMapping(value = "/signup/{type}")
	public String signUp(@PathVariable String type, Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("type", type);
		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) throws IOException {
		String type = model.getAttribute("type").toString();
		if (bindingResult.hasErrors()) {
			return "redirect:/signup/" + type;
		}
		MultipartFile image = user.getImage();
		if (image != null && !image.isEmpty()) {
			try {
				user.setPhoto(image.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
	        String fileName = "static/img/user.png";
	        ClassLoader classLoader = new UserController().getClass().getClassLoader();
	        File file = new File(classLoader.getResource(fileName).getFile());
			BufferedImage originalImage = ImageIO.read(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write( originalImage, "png", baos );
			baos.flush();
			user.setPhoto(baos.toByteArray());
			baos.close();
		}
		if (type.equalsIgnoreCase("seller")) {
			Seller seller = new Seller(user, false, null, null);
			sellerService.save(seller);
		} else {
			Buyer buyer = new Buyer(user, null, null, null);
			buyerService.save(buyer);
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/profile")
	public String profile(Model model, HttpSession session, Principal principal) {
		if(userService.hasRole("ROLE_BUYER")) {
			model.addAttribute("user", buyerService.findByUsername(principal.getName()));
		} else if(userService.hasRole("ROLE_SELLER")) {
			model.addAttribute("user", sellerService.findSeller(principal.getName()));
		} else {
			model.addAttribute("user", userService.findByUsername(principal.getName()));
		}
		return "profile";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String profile(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, Principal principal) throws IOException {
		if (bindingResult.hasErrors()) {
			return "redirect:/profile";
		}
		MultipartFile image = user.getImage();
		if (image != null && !image.isEmpty()) {
			try {
				user.setPhoto(image.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(userService.hasRole("ROLE_BUYER")) {
			buyerService.update(new Buyer(user));
		} else if(userService.hasRole("ROLE_SELLER")) {
			sellerService.update(new Seller(user));
		} else {
			userService.update(user);
		}
		return "redirect:/home";
	}

	@GetMapping("/approveSeller")
	public String approveSeller(@RequestParam("id") Long id, Model model, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("tab", "2");
		Seller seller = sellerService.findSellerById(id).orElse(null);
		seller.setApproved(true);
		sellerService.update(seller);
		return "redirect:/home";
	}

}