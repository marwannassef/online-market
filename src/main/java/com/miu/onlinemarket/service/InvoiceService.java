package com.miu.onlinemarket.service;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.OrderModel;

public interface InvoiceService {
	
	public File generateInvoiceFor(OrderModel order, Locale locale) throws IOException;
	
	public OrderModel getOrderByCode(Order order);

}
