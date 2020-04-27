package com.miu.onlinemarket.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.miu.onlinemarket.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${jwt.http.request.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @GetMapping("/login")
	public String greeting(Model model) {
//    	model.addAttribute("authenticationRequest", new JwtTokenRequest());
		return "login";
	}

//    @RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
//    public String createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
//            throws AuthenticationException {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        final String token = jwtTokenUtil.generateToken(userDetails);
//        return "greeting";
//    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
//            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
//            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        } catch (Exception e) {
//            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        }
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping(value = "user/get_all")
//    public List<User> list() {
//        return userService.findAll();
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @GetMapping(value = "user/get/{userId}")
//    public User getUserById(@PathVariable Long userId) {
//        return userService.findById(userId);
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PostMapping(value = "user/add")
//    public User addNewUser(@Valid @RequestBody User user) {
//        return userService.save(user);
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @PutMapping(value = "user/update/{userId}")
//    public User updateUser(@Valid @RequestBody User editedUser, @PathVariable Long userId) {
//        return userService.update(editedUser, userId);
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//    @DeleteMapping(value = "user/delete/{userId}")
//    public void deleteUser(@PathVariable Long userId) {
//        userService.delete(userId);
//    }

}
