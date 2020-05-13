package com.miu.onlinemarket.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import com.miu.onlinemarket.domain.AddressModel;
import com.miu.onlinemarket.domain.Buyer;
import com.miu.onlinemarket.domain.Item;
import com.miu.onlinemarket.domain.Order;
import com.miu.onlinemarket.domain.OrderEntryModel;
import com.miu.onlinemarket.domain.OrderModel;
import com.miu.onlinemarket.service.InvoiceService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class InvoiceServiceImp implements InvoiceService {

    private static Logger logger = LogManager.getLogger(InvoiceServiceImp.class);

    @Value("${invoice.logo.path}")
    private String logo_path;

    @Value("${invoice.template.path}")
    private String invoice_template;

    @Override
    public File generateInvoiceFor(OrderModel order, Locale locale) throws IOException {

        File pdfFile = File.createTempFile("my-invoice", ".pdf");

        logger.info(String.format("Invoice pdf path : %s", pdfFile.getAbsolutePath()));

        try(FileOutputStream pos = new FileOutputStream(pdfFile))
        {
            // Load invoice JRXML template.
            final JasperReport report = loadTemplate();

            // Fill parameters map.
            final Map<String, Object> parameters = parameters(order, locale);

            // Create an empty datasource.
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));

            // Render the invoice as a PDF file.
            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);

            // return file.
            return pdfFile;
        }
        catch (final Exception e)
        {
            logger.error(String.format("An error occured during PDF creation: %s", e));
            throw new RuntimeException(e);
        }
    }

    // Fill template order params
    private Map<String, Object> parameters(OrderModel order, Locale locale) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("logo", getClass().getResourceAsStream(logo_path));
        parameters.put("order",  order);
        parameters.put("REPORT_LOCALE", locale);
        return parameters;
    }

    // Load invoice JRXML template
    private JasperReport loadTemplate() throws JRException {

        logger.info(String.format("Invoice template path : %s", invoice_template));

        final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }
    
    @Override
    public OrderModel getOrderByCode(Order order) {

        return order(order);

    }

    private OrderModel order(Order order) {
        return new OrderModel(order.getOrderNumber(), address(order), entries(order));
    }

    private AddressModel address(Order order) {
    	Buyer buyer = order.getItems().iterator().next().getBuyer();
        return new AddressModel(buyer.getFirstName(),
        		buyer.getLastName(),
        		buyer.getAddress().getStreet(),
        		buyer.getAddress().getZipCode(),
        		buyer.getAddress().getCity() + "",
        		buyer.getAddress().getState() + ", " + buyer.getAddress().getCountry());
    }

    private List<OrderEntryModel> entries(Order order) {
    	List<OrderEntryModel> orderEntries = new ArrayList<OrderEntryModel>();
    	Iterator<Item> itr = order.getItems().iterator();
    	while (itr.hasNext()) {
    		Item item = itr.next();
    		orderEntries.add(new OrderEntryModel(item.getProduct().getName(),
							    				 (int)item.getQuantity(),
    											 item.getProduct().getPrice()));
    	}
        return orderEntries;
    }

}
