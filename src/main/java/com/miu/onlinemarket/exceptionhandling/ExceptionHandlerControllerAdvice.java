package com.miu.onlinemarket.exceptionhandling;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ModelAndView handleResourceNotFound(final ResourceNotFoundException exception,
			final HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", exception.getMessage());
		mav.addObject("exception", exception);
		mav.addObject("url", request.getRequestURL());
		mav.setViewName("error");
		return mav;

	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody ModelAndView handleException(final Exception exception, final HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("msg", exception.getMessage());
		mav.addObject("exception", exception);
		mav.addObject("url", request.getRequestURL());
		mav.setViewName("error");
		return mav;

	}

}
