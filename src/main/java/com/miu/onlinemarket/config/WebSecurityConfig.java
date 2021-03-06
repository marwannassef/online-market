package com.miu.onlinemarket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.miu.onlinemarket.service.impl.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsServiceImpl userDetailsService;

	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .permitAll()
            	.and()
            .logout()
            	.invalidateHttpSession(true)
            	.permitAll()
            	.and()
	        .rememberMe()
	        	.key("uniqueAndSecret")
	        	.tokenValiditySeconds(1000000)
	    		.and()
        	.csrf()
	    		.disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

	@Override
	public void configure(WebSecurity webSecurity) {
		webSecurity.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").and().ignoring().antMatchers("/console/**/**", "/css/**", "/signup/**", "/js/**", "/img/**", "/webjars/**");
	}

}