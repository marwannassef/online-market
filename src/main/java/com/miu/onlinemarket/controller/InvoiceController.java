package com.miu.onlinemarket.controller;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_PDF;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.OrderModel;
import com.miu.onlinemarket.service.InvoiceService;
import com.miu.onlinemarket.service.OrderService;

@Controller
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private OrderService orderService;

	@GetMapping(value = "/generate", produces = "application/pdf")
	public ResponseEntity<InputStreamResource> invoiceGenerate(@RequestParam("id") Long id) throws Exception {
		final OrderModel order = invoiceService.getOrderByCode(orderService.findById(id).orElse(new Order()));
		final File invoicePdf = invoiceService.generateInvoiceFor(order, Locale.forLanguageTag("en"));
		final HttpHeaders httpHeaders = getHttpHeaders(orderService.findById(id).orElse(new Order()).getOrderNumber(),
				invoicePdf);
		return new ResponseEntity<>(new InputStreamResource(new FileInputStream(invoicePdf)), httpHeaders, OK);
	}

	private HttpHeaders getHttpHeaders(String code, File invoicePdf) {
		HttpHeaders respHeaders = new HttpHeaders();
		respHeaders.setContentType(APPLICATION_PDF);
		respHeaders.setContentLength(invoicePdf.length());
		respHeaders.setContentDispositionFormData("attachment", format("%s.pdf", code));
		return respHeaders;
	}

}
